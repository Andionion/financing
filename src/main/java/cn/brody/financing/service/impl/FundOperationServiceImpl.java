package cn.brody.financing.service.impl;

import cn.brody.financing.mapper.FundBasicDao;
import cn.brody.financing.pojo.bo.AddFundBO;
import cn.brody.financing.pojo.entity.FundBasicEntity;
import cn.brody.financing.service.FundOperationService;
import cn.brody.financing.support.financial.request.FundDetailRequest;
import cn.brody.financing.support.financial.response.FundDetailResponse;
import cn.brody.financing.support.financial.service.FinancialDataService;
import cn.hutool.core.util.ObjectUtil;
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
        FundBasicEntity fundBasicEntity = fundBasicDao.getByCode(addFundBO.getCode());
        if (ObjectUtil.isNull(fundBasicEntity)) {
            fundBasicEntity = new FundBasicEntity();
            fundBasicEntity.setName(fundDetail.getName());
            fundBasicEntity.setType(fundDetail.getType());
            fundBasicEntity.setCode(fundDetail.getCode());
        }
        fundBasicEntity.setBuyRate(fundDetail.getBuyRate());
        fundBasicEntity.setOperatingRate(addFundBO.getOperatingRate());
        fundBasicEntity.setManager(fundDetail.getManager());
        fundBasicEntity.setFundScale(fundDetail.getFundScale());
        if (fundBasicDao.saveOrUpdate(fundBasicEntity)) {
            log.debug("添加基金成功：{}", fundBasicEntity);
        }
    }
}
