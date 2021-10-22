package cn.brody.financing.service.impl;

import cn.brody.financing.constant.UriConstant;
import cn.brody.financing.pojo.base.BaseResponse;
import cn.brody.financing.pojo.request.FundDetailRequest;
import cn.brody.financing.pojo.response.FundDetailResponse;
import cn.brody.financing.service.FundService;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brody
 * @date 2021/10/21
 **/
@Slf4j
@Service
public class FundServiceImpl implements FundService {

    @Override
    public FundDetailResponse getFundDetail(FundDetailRequest fundDetailRequest) {
        Map<String, Object> hashMap = new HashMap<>(3);
        hashMap.put("code", fundDetailRequest.getCode());
        hashMap.put("startDate", fundDetailRequest.getStartDate());
        hashMap.put("endDate", fundDetailRequest.getEndDate());
        String response = HttpUtil.get(UriConstant.LIST_FUND_DETAIL_URL, hashMap);
        BaseResponse<?> baseResponse = JSON.parseObject(response, BaseResponse.class);
        return JSON.parseObject(baseResponse.getData().toString(), FundDetailResponse.class);
    }
}
