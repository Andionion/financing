package cn.brody.financing;

import cn.brody.financing.constant.UriConstant;
import cn.brody.financing.pojo.base.BaseResponse;
import cn.brody.financing.support.financial.request.FundDetailRequest;
import cn.brody.financing.support.financial.response.FundDetailResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Brody
 * @date 2021/10/21
 **/
public class HttpUtilTest {

    public static void main(String[] args) {
        FundDetailRequest fundDetailRequest = new FundDetailRequest();
        fundDetailRequest.setCode("161005");
        fundDetailRequest.setStartDate(LocalDate.of(2021, 10, 20));
        fundDetailRequest.setEndDate(LocalDate.of(2021, 10, 21));

        Map<String, Object> hashMap = new HashMap<>(3);
        hashMap.put("code", fundDetailRequest.getCode());
        hashMap.put("startDate", fundDetailRequest.getStartDate());
        hashMap.put("endDate", fundDetailRequest.getEndDate());

        String s = HttpUtil.get(UriConstant.LIST_FUND_DETAIL_URL, hashMap);
        BaseResponse<?> baseResponse = JSON.parseObject(s, BaseResponse.class);
        FundDetailResponse fundDetailResponse = JSON.parseObject(baseResponse.getData().toString(), FundDetailResponse.class);
        System.out.println(JSONUtil.toJsonPrettyStr(fundDetailResponse));
    }

}
