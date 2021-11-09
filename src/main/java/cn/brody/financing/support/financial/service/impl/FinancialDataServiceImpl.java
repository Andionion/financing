package cn.brody.financing.support.financial.service.impl;

import cn.brody.financing.constant.UriConstant;
import cn.brody.financing.pojo.base.BaseResponse;
import cn.brody.financing.support.financial.request.FundDetailRequest;
import cn.brody.financing.support.financial.response.FundDetailResponse;
import cn.brody.financing.support.financial.service.FinancialDataService;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Brody
 * @date 2021/10/21
 **/
@Slf4j
@Service
public class FinancialDataServiceImpl implements FinancialDataService {

    @Override
    public FundDetailResponse getFundDetail(String code) {
        return getFundDetail(new FundDetailRequest(code));
    }

    @Override
    public FundDetailResponse getFundDetail(String code, LocalDate date) {
        return getFundDetail(new FundDetailRequest(code, date, date));
    }

    @Override
    public FundDetailResponse getFundDetail(String code, LocalDate startDate, LocalDate endDate) {
        return getFundDetail(new FundDetailRequest(code, startDate, endDate));
    }

    private FundDetailResponse getFundDetail(FundDetailRequest fundDetailRequest) {
        Map<String, Object> hashMap = new HashMap<>(3);
        hashMap.put("code", fundDetailRequest.getCode());
        Optional.ofNullable(fundDetailRequest.getStartDate()).ifPresent(startDate -> hashMap.put("startDate", startDate));
        Optional.ofNullable(fundDetailRequest.getEndDate()).ifPresent(endDate -> hashMap.put("endDate", endDate));
        String response = HttpUtil.get(UriConstant.LIST_FUND_DETAIL_URL, hashMap);
        BaseResponse<?> baseResponse = JSON.parseObject(response, BaseResponse.class);
        return JSON.parseObject(baseResponse.getData().toString(), FundDetailResponse.class);
    }
}
