package cn.brody.financing.service.impl;

import cn.brody.financing.mapper.FundBasicDao;
import cn.brody.financing.mapper.FundNetWorthDao;
import cn.brody.financing.pojo.bo.AddOrUpdateFundBO;
import cn.brody.financing.pojo.bo.DelFundBO;
import cn.brody.financing.pojo.entity.FundBasicEntity;
import cn.brody.financing.service.FundBasicService;
import cn.brody.financing.service.FundNetWorthService;
import cn.brody.financing.support.financial.response.FundDetailResponse;
import cn.brody.financing.support.financial.service.FinancialDataService;
import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

/**
 * @author chenyifu6
 * @date 2021/10/26
 */
@Slf4j
@Service
public class FundBasicServiceImpl implements FundBasicService {
    @Autowired
    private FinancialDataService financialDataService;
    @Autowired
    private FundBasicDao fundBasicDao;
    @Autowired
    private FundNetWorthDao fundNetWorthDao;
    @Autowired
    private FundNetWorthService fundNetWorthService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrUpdateFund(AddOrUpdateFundBO addOrUpdateFundBO) {
        log.debug("开始添加或修改基金，基金代码={}", addOrUpdateFundBO.getCode());
        FundDetailResponse fundDetailResponse = financialDataService.
                getFundDetail(addOrUpdateFundBO.getCode(), LocalDate.of(2020, 8, 31), null);
        FundBasicEntity fundBasicEntity = fundBasicDao.getByCode(addOrUpdateFundBO.getCode());
        if (ObjectUtil.isNull(fundBasicEntity)) {
            fundBasicEntity = new FundBasicEntity();
            fundBasicEntity.setName(fundDetailResponse.getName());
            fundBasicEntity.setType(fundDetailResponse.getType());
            fundBasicEntity.setCode(fundDetailResponse.getCode());
        }
        fundBasicEntity.setBuyRate(fundDetailResponse.getBuyRate());
        fundBasicEntity.setManager(fundDetailResponse.getManager());
        fundBasicEntity.setFundScale(fundDetailResponse.getFundScale());
        if (fundBasicDao.saveOrUpdate(fundBasicEntity)) {
            fundNetWorthService.addFundNetWorth(fundDetailResponse);
            log.debug("添加或修改基金成功，基金代码={}", fundBasicEntity);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delFund(DelFundBO delFundBO) {
        log.debug("开始删除基金，基金代码={}", delFundBO.getCode());
        FundBasicEntity fundBasicEntity = fundBasicDao.getByCode(delFundBO.getCode());
        if (ObjectUtil.isNull(fundBasicEntity)) {
            log.error("基金不存在，删除失败，{}", delFundBO.getCode());
            throw new NullPointerException("基金不存在，删除失败");
        }
        if (fundBasicDao.removeById(fundBasicEntity)) {
            log.info("删除基金成功");
        }
        fundNetWorthService.delFundNetWorth(delFundBO.getCode());
        log.debug("结束删除基金，基金代码={}", delFundBO.getCode());
    }
}
