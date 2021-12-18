package cn.brody.financing.service.impl;

import cn.brody.financing.mapper.FundBasicDao;
import cn.brody.financing.mapper.FundNetWorthDao;
import cn.brody.financing.mapper.FundTradeRecordDao;
import cn.brody.financing.pojo.bo.AddOrUpdateFundBO;
import cn.brody.financing.pojo.bo.AddTradeBO;
import cn.brody.financing.pojo.dto.ImportTradeDTO;
import cn.brody.financing.pojo.entity.FundBasicEntity;
import cn.brody.financing.pojo.entity.FundNetWorthEntity;
import cn.brody.financing.pojo.entity.FundTradeRecordEntity;
import cn.brody.financing.service.FundBasicService;
import cn.brody.financing.service.FundTradeService;
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
        FundTradeRecordEntity fundTradeRecordEntity = new FundTradeRecordEntity(addTradeBO);
        // 尝试存储买入份额
        FundNetWorthEntity netWorthEntity = fundNetWorthDao.
                getNetWorth(addTradeBO.getCode(), addTradeBO.getConfirmDate());
        if (ObjectUtil.isNotNull(netWorthEntity)) {
            double confirmShare = BigDecimal.valueOf(addTradeBO.getAmount() / netWorthEntity.getNetWorth())
                    .setScale(2, RoundingMode.HALF_DOWN)
                    .doubleValue();
            fundTradeRecordEntity.setConfirmShare(confirmShare);
            // todo 还需要考虑一个问题，就是当前日期有分红怎么算
            // todo 基金详情中，总份额增加
        }
        fundTradeRecordDao.save(fundTradeRecordEntity);
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
        List<LocalDate> localDates = importTradeDTOList.stream().map(ImportTradeDTO::getLocalDate).collect(Collectors.toList());
        // 添加基金记录
        fundBasicService.addOrUpdateFund(new AddOrUpdateFundBO(code));
        FundBasicEntity fundBasicEntity = fundBasicDao.getByCode(code);
        // 获取基金的所有净值
        List<FundNetWorthEntity> fundNetWorthEntities = fundNetWorthDao.listNetWorth(code, localDates);
        Map<LocalDate, Double> fundNetWorthMap = fundNetWorthEntities.stream().collect(Collectors.toMap(FundNetWorthEntity::getDate, FundNetWorthEntity::getNetWorth));
        // 开始获取
        List<FundTradeRecordEntity> fundTradeRecordEntityList = new ArrayList<>();
        importTradeDTOList.forEach(importTradeDTO -> {
            Double amount = importTradeDTO.getAmount();
            boolean amountNotNull = ObjectUtil.isNotNull(amount) && BigDecimal.valueOf(amount).compareTo(BigDecimal.ZERO) != 0;
            if (amountNotNull) {
                // 申购的时候按照金额计算份额，赎回的时候直接就是份额
                Integer type = amount < 0 ? REQUISITION : REDEMPTION;
                double confirmShare;
                if (REQUISITION.equals(type)) {
                    Double netWorth = fundNetWorthMap.get(importTradeDTO.getLocalDate());
                    confirmShare = -1.0 * BigDecimal.valueOf(amount * (100 - fundBasicEntity.getBuyRate()) / 100 / netWorth)
                            .setScale(4, RoundingMode.HALF_UP).doubleValue();
                } else {
                    confirmShare = -amount;
                }
                fundTradeRecordEntityList.add(new FundTradeRecordEntity(code, amount, type, confirmShare, importTradeDTO.getLocalDate()));

            }
        });
        // todo 此处先行加上富国天惠的分红，后续想办法解决分红计算问题
        if ("161005".equals(code)) {
            fundTradeRecordEntityList.add(new FundTradeRecordEntity("161005", 0.0, DIVIDEND, 33.66, LocalDate.of(2021, 3, 29)));
        }
        log.info("开始存储交易记录列表，列表：{}", fundTradeRecordEntityList);
        fundTradeRecordDao.saveBatch(fundTradeRecordEntityList);
    }

}
