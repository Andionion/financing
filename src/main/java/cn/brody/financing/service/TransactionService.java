package cn.brody.financing.service;

import cn.brody.financing.pojo.bo.AddTransactionBO;

/**
 * 交易记录 service
 * @author brody
 * @date 2021/11/03
 */
public interface TransactionService {

    /**
     * 添加交易记录
     * @param addTransactionBO 添加交易记录 BO
     */
    void addTransaction(AddTransactionBO addTransactionBO);
}
