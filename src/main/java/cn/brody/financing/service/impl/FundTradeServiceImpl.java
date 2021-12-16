package cn.brody.financing.service.impl;

import cn.brody.financing.mapper.FundBasicDao;
import cn.brody.financing.mapper.FundNetWorthDao;
import cn.brody.financing.mapper.FundTradeRecordDao;
import cn.brody.financing.pojo.bo.AddFundBO;
import cn.brody.financing.pojo.bo.AddTradeBO;
import cn.brody.financing.pojo.entity.FundBasicEntity;
import cn.brody.financing.pojo.entity.FundNetWorthEntity;
import cn.brody.financing.pojo.entity.FundTradeRecordEntity;
import cn.brody.financing.service.FundOperationService;
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

import static cn.brody.financing.constant.TradeOperationConstant.REDEMPTION;
import static cn.brody.financing.constant.TradeOperationConstant.REQUISITION;

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
    private FundOperationService fundOperationService;

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
        ExcelReader reader = ExcelUtil.getReader(Objects.requireNonNull(getClass().getResource("/")).getPath() + "fundTrade1.xlsx");
        List<Map<String, Object>> maps = reader.readAll();
        maps.forEach(map -> {
            String code = map.get("code").toString();
            map.remove("code");
            Map<LocalDate, Double> tempMap = new LinkedHashMap<>();
            map.forEach((key, value) -> {
                if (ObjectUtil.isNotNull(value) && StrUtil.isNotBlank(value.toString())) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    LocalDate parse = LocalDate.parse(key, formatter);
                    tempMap.put(parse, Double.parseDouble(value.toString()));
                }
            });
            log.info("开始添加交易记录，基金代码：{},map:{}", code, tempMap);
            addTradeRecord(code, tempMap);
        });
    }

    /**
     * 添加交易记录
     *
     * @param code
     * @param tradeMap
     */
    private void addTradeRecord(String code, Map<LocalDate, Double> tradeMap) {
        Set<LocalDate> localDates = tradeMap.keySet();
        // 添加基金记录
        if (!fundBasicDao.isFundExist(code)) {
            log.info("基金不存在，开始添加基金，基金代码：{}", code);
            fundOperationService.addFund(new AddFundBO(code));
        }
        FundBasicEntity fundBasicEntity = fundBasicDao.getByCode(code);
        List<FundNetWorthEntity> fundNetWorthEntities = fundNetWorthDao.listNetWorth(code, localDates);
        List<LocalDate> alreadyExistRecordList = fundTradeRecordDao.listAlreadyExistRecord(code, localDates);
        List<FundTradeRecordEntity> fundTradeRecordEntityList = new ArrayList<>();
        fundNetWorthEntities.forEach(fundNetWorthEntity -> {
            Double amount = tradeMap.get(fundNetWorthEntity.getDate());
            boolean amountNotNull = ObjectUtil.isNotNull(amount) && BigDecimal.valueOf(amount).compareTo(BigDecimal.ZERO) != 0;
            if (amountNotNull && !alreadyExistRecordList.contains(fundNetWorthEntity.getDate())) {
                // 申购的时候按照金额计算份额，赎回的时候直接就是份额
                Integer type = amount < 0 ? REQUISITION : REDEMPTION;
                double confirmShare;
                if (REQUISITION.equals(type)) {
                    double confirmAmount = amount * (100 - fundBasicEntity.getBuyRate()) / 100;
                    confirmShare = -1.0 * BigDecimal.valueOf(confirmAmount / fundNetWorthEntity.getNetWorth())
                            .setScale(2, RoundingMode.HALF_DOWN)
                            .doubleValue();
                } else {
                    confirmShare = -amount;
                }
                fundTradeRecordEntityList.add(new FundTradeRecordEntity(code, amount, type, confirmShare, fundNetWorthEntity.getDate()));
            }
        });
        log.info("开始存储交易记录列表，列表：{}", fundTradeRecordEntityList);
        fundTradeRecordDao.saveBatch(fundTradeRecordEntityList);
    }

}
