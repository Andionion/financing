package cn.brody.financing;

import cn.brody.financing.constant.UriConstant;
import cn.brody.financing.mapper.FundBasicDao;
import cn.brody.financing.pojo.base.BaseResponse;
import cn.brody.financing.pojo.entity.FundBasicEntity;
import cn.brody.financing.support.financial.request.FundDetailRequest;
import cn.brody.financing.support.financial.response.FundDetailResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Brody
 * @date 2021/10/26
 **/
@SpringBootTest
public class ApplicationTest {
    @Autowired
    private FundBasicDao fundBasicDao;

    @Test
    public void testSave() {
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

        FundBasicEntity fundBasicEntity = new FundBasicEntity();
        fundBasicEntity.setName(fundDetailResponse.getName());
        fundBasicEntity.setBuyRate(fundDetailResponse.getBuyRate());
        fundBasicEntity.setManager(fundDetailResponse.getManager());
        fundBasicEntity.setType(fundDetailResponse.getType());
        fundBasicEntity.setFundScale(fundDetailResponse.getFundScale());
        fundBasicEntity.setCode(fundDetailResponse.getCode());

        fundBasicDao.save(fundBasicEntity);

    }
}
