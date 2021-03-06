package cn.brody.financing.service.impl;

import cn.brody.financing.mapper.FundBasicDao;
import cn.brody.financing.mapper.FundNetWorthDao;
import cn.brody.financing.mapper.FundTradeRecordDao;
import cn.brody.financing.pojo.bo.AddOrUpdateFundBO;
import cn.brody.financing.pojo.bo.AddTradeBO;
import cn.brody.financing.pojo.bo.AddTradeListBO;
import cn.brody.financing.pojo.dto.ImportTradeDTO;
import cn.brody.financing.pojo.entity.FundBasicEntity;
import cn.brody.financing.pojo.entity.FundNetWorthEntity;
import cn.brody.financing.pojo.entity.FundTradeRecordEntity;
import cn.brody.financing.service.FundBasicService;
import cn.brody.financing.service.FundTradeService;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static cn.brody.financing.constant.TradeOperationConstant.*;

/**
 * @author brody
 * @date 2021/11/03
 */
@Slf4j
@Service
public class FundTradeServiceImpl implements FundTradeService {

    @Autowired
    private FundTradeRecordDao fundTradeRecordDao;
    @Autowired
    private FundNetWorthDao fundNetWorthDao;
    @Autowired
    private FundBasicDao fundBasicDao;
    @Autowired
    private FundBasicService fundBasicService;

    @Override
    public void addTradeRecord(AddTradeBO addTradeBO) {
        AddTradeListBO addTradeListBO = new AddTradeListBO(List.of(addTradeBO));
        addTradeListBO.setCode(addTradeBO.getCode());
        addTradeRecord(addTradeListBO);
    }


    @Override
    public void importTradeRecord(MultipartFile file) {
        log.info("开始导入 excel");
        ExcelReader reader = ExcelUtil.getReader(Objects.requireNonNull(getClass().getResource("/")).getPath() + "fundTrade.xlsx", 1);
        List<Map<String, Object>> readerMaps = reader.readAll();
        Map<String, List<ImportTradeDTO>> maps = initTradeMap(reader, readerMaps);
        readerMaps.forEach(map -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDate date = LocalDate.parse(map.get("date").toString(), formatter);
            map.remove("date");
            map.forEach((key, value) -> {
                if (ObjectUtil.isNotNull(value) && StrUtil.isNotBlank(value.toString())) {
                    List<ImportTradeDTO> localDateDoubleList = maps.get(key);
                    localDateDoubleList.add(new ImportTradeDTO(date, Double.parseDouble(value.toString())));
                }
            });
        });
        maps.forEach((key, value) -> log.info("code：{}，trade：{}", key, value.stream().sorted()));
        maps.forEach(this::addTradeRecord);
    }

    /**
     * 获取交易记录的初始 map
     *
     * @param reader
     * @param readerMaps
     * @return
     */
    private Map<String, List<ImportTradeDTO>> initTradeMap(ExcelReader reader, List<Map<String, Object>> readerMaps) {
        Map<String, List<ImportTradeDTO>> maps = new HashMap<>(10);
        List<List<Object>> read = reader.read();
        List<Object> header = read.get(0);
        header.remove(0);
        header.forEach(code -> maps.put(code.toString(), new ArrayList<>()));
        return maps;
    }

    /**
     * 添加交易记录
     *
     * @param code
     * @param importTradeDTOList
     */
    private void addTradeRecord(String code, List<ImportTradeDTO> importTradeDTOList) {
        AddTradeListBO addTradeListBO = new AddTradeListBO();
        addTradeListBO.setCode(code);
        importTradeDTOList.forEach(importTradeDTO -> {
            AddTradeBO addTradeBO = new AddTradeBO();
            addTradeBO.setAmount(importTradeDTO.getAmount());
            addTradeBO.setDate(importTradeDTO.getLocalDate());
            addTradeBO.setType(importTradeDTO.getAmount() < 0 ? REQUISITION : REDEMPTION);
            addTradeListBO.getAddTradeBOList().add(addTradeBO);
        });
        addTradeRecord(addTradeListBO);
    }

    /**
     * 添加基金交易记录
     *
     * @param addTradeListBO
     */
    private void addTradeRecord(AddTradeListBO addTradeListBO) {
        String code = addTradeListBO.getCode();
        List<AddTradeBO> addTradeList = addTradeListBO.getAddTradeBOList();
        List<LocalDate> localDates = addTradeList.stream().map(AddTradeBO::getDate).collect(Collectors.toList());
        // 添加基金记录
        fundBasicService.addOrUpdateFund(new AddOrUpdateFundBO(code));
        FundBasicEntity fundBasicEntity = fundBasicDao.getByCode(code);
        // 获取基金的所有净值
        List<FundNetWorthEntity> fundNetWorthEntities = fundNetWorthDao.listNetWorth(code, localDates);
        Map<LocalDate, Double> fundNetWorthMap = fundNetWorthEntities.stream()
                .collect(Collectors.toMap(FundNetWorthEntity::getDate, FundNetWorthEntity::getNetWorth));
        // 开始计算
        List<FundTradeRecordEntity> fundTradeRecordEntityList = new ArrayList<>();
        addTradeList.forEach(addTradeBO -> {
            Double amount = addTradeBO.getAmount();
            boolean amountNotNull = ObjectUtil.isNotNull(amount) && BigDecimal.valueOf(amount).compareTo(BigDecimal.ZERO) != 0;
            if (amountNotNull) {
                Integer type = addTradeBO.getType();
                double confirmShare;
                if (REQUISITION.equals(type)) {
                    Double netWorth = fundNetWorthMap.get(addTradeBO.getDate());
                    confirmShare = -1.0 * BigDecimal.valueOf(amount * (100 - fundBasicEntity.getBuyRate()) / 100 / netWorth)
                            .setScale(4, RoundingMode.HALF_UP).doubleValue();
                } else {
                    confirmShare = -amount;
                }
                fundTradeRecordEntityList.add(new FundTradeRecordEntity(code, amount, type, confirmShare, addTradeBO.getDate()));
            }
        });
        log.info("开始存储交易记录列表，列表：{}", fundTradeRecordEntityList);
        fundTradeRecordDao.saveBatch(fundTradeRecordEntityList);
        // todo 基金详情中，总份额增加
    }

    @Override
    public void calculateDividend() {
        List<FundBasicEntity> fundBasicEntityList = fundBasicDao.list();
        List<String> fundCodeList = fundBasicEntityList.stream().map(FundBasicEntity::getCode).collect(Collectors.toList());
        fundCodeList.forEach(code -> {
            // 查找所有的分红日
            List<FundNetWorthEntity> fundNetWorthEntities = fundNetWorthDao.listNetWorthList(code);
            Map<LocalDate, Double> dividendsMap = fundNetWorthEntities.stream()
                    .filter(netWorth -> BigDecimal.valueOf(netWorth.getDividends()).compareTo(BigDecimal.ZERO) > 0)
                    .collect(Collectors.toMap(FundNetWorthEntity::getDate, FundNetWorthEntity::getDividends));
            if (dividendsMap.isEmpty()) {
                return;
            }
            TreeMap<LocalDate, Double> sortedDividendMap = MapUtil.sort(dividendsMap);
            log.info("查找到所有的分红：{}", sortedDividendMap);
            sortedDividendMap.forEach((date, dividends) -> {
                // 如果此分红已经被记录，那么跳过
                if (fundTradeRecordDao.countDividendExist(code, date)) {
                    log.info("分红已存在，跳过，code:{},date:{}", code, date);
                    return;
                }
                // 查找此日期之前的所有份额
                List<FundTradeRecordEntity> fundTradeRecordEntityList = fundTradeRecordDao.listBeforeDate(code, date);
                if (fundTradeRecordEntityList.isEmpty()) {
                    return;
                }
                Optional<Double> share = fundTradeRecordEntityList.stream().map(FundTradeRecordEntity::getConfirmShare).reduce(Double::sum);
                // 分红可得金额
                double amount = share.get() * dividends;
                // 查找分红日净值
                FundNetWorthEntity netWorth = fundNetWorthDao.getNetWorth(code, date);
                // 分红份额
                double dividendShare = BigDecimal.valueOf(amount / netWorth.getNetWorth()).setScale(2, RoundingMode.HALF_UP).doubleValue();
                log.info("计算出分红份额，基金：{},份额：{},日期：{}", code, dividendShare, date);
                // 存储份额
                fundTradeRecordDao.save(new FundTradeRecordEntity(code, BigDecimal.valueOf(amount).setScale(2, RoundingMode.HALF_UP).doubleValue(), DIVIDEND, dividendShare, date));
            });

        });
    }

    @Override
    public void timingInvestment() {
        // 买入日期，
    }
}
