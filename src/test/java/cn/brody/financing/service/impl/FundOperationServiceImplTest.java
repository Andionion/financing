package cn.brody.financing.service.impl;

import cn.brody.financing.mapper.FundBasicDao;
import cn.brody.financing.mapper.FundNetWorthDao;
import cn.brody.financing.pojo.bo.AddFundBO;
import cn.brody.financing.support.financial.response.FundDetailResponse;
import cn.brody.financing.support.financial.service.FinancialDataService;
import cn.brody.financing.util.ResourceHelper;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @author brody
 * @date 2021/11/25
 */
class FundOperationServiceImplTest {

    @Mock
    private FinancialDataService financialDataService;
    @Mock
    private FundBasicDao fundBasicDao;
    @Mock
    private FundNetWorthDao fundNetWorthDao;

    @InjectMocks
    private FundOperationServiceImpl fundOperationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addFund() {
        String resourceAsString = ResourceHelper.getResourceAsString(getClass(), "/fundDetail.json");
        FundDetailResponse fundDetailResponse = JSONObject.parseObject(resourceAsString, FundDetailResponse.class);
        when(financialDataService.getFundDetail(any())).thenReturn(fundDetailResponse);
        when(fundBasicDao.saveOrUpdate(any())).thenReturn(true);
        when(fundNetWorthDao.saveOrUpdateBatch(any())).thenReturn(true);
        AddFundBO addFundBO = new AddFundBO();
        addFundBO.setCode("161005");
        fundOperationService.addFund(addFundBO);
        verify(fundBasicDao, times(1)).saveOrUpdate(any());
        verify(financialDataService, times(1)).getFundDetail(any());
        verify(fundNetWorthDao, times(1)).saveOrUpdateBatch(any());
    }

    @Test
    void delFund() {
    }

    @Test
    void addFundNetWorth() {
    }

    @Test
    void testAddFundNetWorth() {
    }

    @Test
    void delFundNetWorth() {
    }
}