package cn.brody.financing.service.impl;

import cn.brody.financing.mapper.FundBasicDao;
import cn.brody.financing.pojo.bo.AddFundBO;
import cn.brody.financing.pojo.entity.FundBasicEntity;
import cn.brody.financing.service.FundOperationService;
import cn.brody.financing.support.financial.request.FundDetailRequest;
import cn.brody.financing.support.financial.response.FundDetailResponse;
import cn.brody.financing.support.financial.service.FinancialDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author chenyifu6
 * @date 2021/10/26
 */
@Slf4j
@Service
public class FundOperationServiceImpl implements FundOperationService {
    @Autowired
    private FinancialDataService financialDataService;
    @Autowired
    private FundBasicDao fundBasicDao;

    @Override
    public void addFund(AddFundBO addFundBO) {
        log.debug("开始添加基金：{}", addFundBO.getCode());
        FundDetailResponse fundDetail = financialDataService.getFundDetail(new FundDetailRequest(addFundBO.getCode()));
        FundBasicEntity fundBasicEntity = new FundBasicEntity();
        fundBasicEntity.setName(fundDetail.getName());
        fundBasicEntity.setBuyRate(fundDetail.getBuyRate());
        fundBasicEntity.setOperatingRate(addFundBO.getOperatingRate());
        fundBasicEntity.setManager(fundDetail.getManager());
        fundBasicEntity.setType(fundDetail.getType());
        fundBasicEntity.setFundScale(fundDetail.getFundScale());
        fundBasicEntity.setCode(fundDetail.getCode());
        if (fundBasicDao.saveOrUpdate(fundBasicEntity)) {
            log.debug("添加基金成功：{}", fundBasicEntity);
        }
    }
}
