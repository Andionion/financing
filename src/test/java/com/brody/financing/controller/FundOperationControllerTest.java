package com.brody.financing.controller;

import cn.brody.financing.support.financial.response.FundDetailResponse;
import cn.brody.financing.support.financial.service.FinancialDataService;
import cn.hutool.core.util.RandomUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;

/**
 * @author brody
 * @date 2021/11/17
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class FundOperationControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private FinancialDataService financialDataService;

    private MockMvc mockMvc;


    @Before
    public void before() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Transactional
    public void addFund() {
        FundDetailResponse fundDetail = new FundDetailResponse();
        fundDetail.setName(RandomUtil.randomString(6));
        fundDetail.setType(RandomUtil.randomString(10));
        fundDetail.setCode(RandomUtil.randomNumbers(6));
        fundDetail.setBuyRate(RandomUtil.randomDouble(0, 1));
        fundDetail.setManager(RandomUtil.randomString(3));
        fundDetail.setFundScale(RandomUtil.randomString(5));
        Mockito.when(financialDataService.getFundDetail(any())).thenReturn(fundDetail);

    }
}
