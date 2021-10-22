package cn.brody.financing.service;

import cn.brody.financing.pojo.request.FundDetailRequest;
import cn.brody.financing.pojo.response.FundDetailResponse;

/**
 * 基金服务类
 *
 * @author Brody
 * @date 2021/10/21
 **/
public interface FundService {

    /**
     * 获取基金详情
     *
     * @param fundDetailRequest
     * @return
     */
    FundDetailResponse getFundDetail(FundDetailRequest fundDetailRequest);
}
