package cn.brody.financing.service.impl;

import cn.brody.financing.mapper.FundNetWorthDao;
import cn.brody.financing.pojo.bo.AddTransactionBO;
import cn.brody.financing.pojo.entity.FundNetWorthEntity;
import cn.brody.financing.service.FundOperationService;
import cn.brody.financing.service.TransactionService;
import cn.brody.financing.support.financial.service.FinancialDataService;
import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author brody
 * @date 2021/11/03
 */
@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private FundNetWorthDao fundNetWorthDao;
    @Autowired
    private FinancialDataService financialDataService;
    @Autowired
    private FundOperationService fundOperationService;

    @Override
    public void addTransaction(AddTransactionBO addTransactionBO) {
        // 查找出当天的净值
        FundNetWorthEntity fundNetWorthEntity = fundNetWorthDao.getNetWorth(addTransactionBO.getCode(), addTransactionBO.getConfirmDate());
        if (ObjectUtil.isNull(fundNetWorthEntity)) {
            fundOperationService.addFundNetWorth();
        }

    }
}
