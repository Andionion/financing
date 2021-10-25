package cn.brody.financing.service;

import cn.brody.financing.pojo.bo.AddFundBO;

/**
 * 基金添加，删除等操作的service
 *
 * @author chenyifu6
 * @date 2021/10/26
 */
public interface FundOperationService {

    /**
     * 添加基金
     *
     * @param addFundBO 添加基金的请求类
     */
    void addFund(AddFundBO addFundBO);
}
