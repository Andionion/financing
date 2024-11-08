package cn.brody.financing.service.impl;

import cn.brody.financing.database.dao.FundInvestmentDao;
import cn.brody.financing.database.dao.FundNetValueDao;
import cn.brody.financing.database.entity.FundInvestmentEntity;
import cn.brody.financing.database.entity.FundNetValueEntity;
import cn.brody.financing.pojo.bo.BondFundPurchaseBO;
import cn.brody.financing.service.IFundInvestmentService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * FundInvestmentServiceImpl
 *
 * @author chenyifu6
 * @since 2024/11/08 09:43
 */
@Slf4j
@Service
public class FundInvestmentServiceImpl implements IFundInvestmentService {

    @Autowired
    private FundInvestmentDao fundInvestmentDao;
    @Autowired
    private FundNetValueDao fundNetValueDao;

    @Override
    public void purchaseBondFund(BondFundPurchaseBO bo) {
        // 获取基金信息
        FundNetValueEntity fundLatestNetValue = fundNetValueDao.getFundLatestNetValue(bo.getFundCode());
        List<FundInvestmentEntity> fundInvestmentEntities = bo.getList().stream()
                .map(fundPurchaseInfoBO -> new FundInvestmentEntity(bo.getFundCode(), fundLatestNetValue.getFundName(), fundPurchaseInfoBO))
                .collect(Collectors.toList());
        log.info("开始更新基金投资信息：{}", JSON.toJSONString(fundInvestmentEntities));
        fundInvestmentDao.saveBatch(fundInvestmentEntities);
    }
}
