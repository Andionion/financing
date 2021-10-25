package cn.brody.financing.support.financial.service;

import cn.brody.financing.support.financial.request.FundDetailRequest;
import cn.brody.financing.support.financial.response.FundDetailResponse;

/**
 * 外部金融接口
 *
 * @author Brody
 * @date 2021/10/21
 **/
public interface FinancialDataService {

    /**
     * 获取基金详情
     *
     * @param fundDetailRequest
     * @return
     */
    FundDetailResponse getFundDetail(FundDetailRequest fundDetailRequest);
}
