package cn.brody.financing.service;

import cn.brody.financing.pojo.bo.AddFundNetWorthBO;
import cn.brody.financing.support.financial.response.FundDetailResponse;

/**
 * @author brody
 * @date 2021/12/18
 */
public interface FundNetWorthService {

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
