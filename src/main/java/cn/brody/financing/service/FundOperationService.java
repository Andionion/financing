package cn.brody.financing.service;

import cn.brody.financing.pojo.bo.AddFundBO;
import cn.brody.financing.pojo.bo.DelFundBO;
import cn.brody.financing.support.financial.response.FundDetailResponse;

import java.time.LocalDate;

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

    /**
     * 删除基金
     *
     * @param delFundBO
     */
    void delFund(DelFundBO delFundBO);


    /**
     * 添加指定日期基金净值记录
     *
     * @param date
     */
    void addFundNetWorth(LocalDate date);

    /**
     * 添加基金净值记录
     *
     * @param fundDetailResponse
     */
    void addFundNetWorth(FundDetailResponse fundDetailResponse);

}
