package cn.brody.financing.service.impl;

import cn.brody.financing.mapper.FundNetWorthDao;
import cn.brody.financing.mapper.FundTradeRecordDao;
import cn.brody.financing.pojo.bo.AddTradeBO;
import cn.brody.financing.pojo.entity.FundNetWorthEntity;
import cn.brody.financing.pojo.entity.FundTradeRecordEntity;
import cn.brody.financing.service.FundTradeService;
import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

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

    @Override
    public void addTradeRecord(AddTradeBO addTradeBO) {
        FundTradeRecordEntity fundTradeRecordEntity = new FundTradeRecordEntity(addTradeBO);
        // 尝试存储买入份额
        FundNetWorthEntity netWorthEntity = fundNetWorthDao.getNetWorth(addTradeBO.getCode(), addTradeBO.getConfirmDate());
        if (ObjectUtil.isNotNull(netWorthEntity)) {
            fundTradeRecordEntity.setConfirmShare(BigDecimal.valueOf(addTradeBO.getAmount() / netWorthEntity.getNetWorth()).setScale(2, RoundingMode.HALF_DOWN).doubleValue());
            // todo 还需要考虑一个问题，就是当前日期有分红怎么算
            // todo 基金详情中，总份额增加
        }
        fundTradeRecordDao.save(fundTradeRecordEntity);
    }
}
