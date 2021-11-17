package cn.brody.financing.service;

import cn.brody.financing.pojo.bo.AddFundBO;
import cn.brody.financing.pojo.bo.AddFundNetWorthBO;
import cn.brody.financing.pojo.bo.DelFundBO;
import cn.brody.financing.support.financial.response.FundDetailResponse;

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
     * 添加基金净值记录
     *
     * @param addFundNetWorthBO
     */
    void addFundNetWorth(AddFundNetWorthBO addFundNetWorthBO);

    /**
     * 添加基金净值记录
     *
     * @param fundDetailResponse
     */
    void addFundNetWorth(FundDetailResponse fundDetailResponse);

    /**
     * 删除基金净值记录
     *
     * @param code 基金代码
     */
    void delFundNetWorth(String code);

}
