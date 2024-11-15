package cn.brody.financing.service.impl;

import cn.brody.financing.database.dao.FundInvestmentDao;
import cn.brody.financing.database.dao.FundNetValueDao;
import cn.brody.financing.database.dao.TradeDateHistDao;
import cn.brody.financing.database.entity.FundInvestmentEntity;
import cn.brody.financing.database.entity.FundNetValueEntity;
import cn.brody.financing.database.entity.TradeDateHistEntity;
import cn.brody.financing.pojo.base.BaseList;
import cn.brody.financing.pojo.bo.BondFundPurchaseBO;
import cn.brody.financing.pojo.vo.FundCalculateVO;
import cn.brody.financing.pojo.vo.FundPurchaseInfoVO;
import cn.brody.financing.service.IFundInvestmentService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.decampo.xirr.Transaction;
import org.decampo.xirr.Xirr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    @Autowired
    private TradeDateHistDao tradeDateHistDao;

    @Override
    public void purchaseBondFund(BondFundPurchaseBO bo) {
        // 获取基金信息
        List<FundInvestmentEntity> fundInvestmentEntities = bo.getList()
                .stream()
                .map(fundPurchaseInfoBO -> {
                    FundNetValueEntity fundNetValue = fundNetValueDao.getFundNetValue(bo.getFundCode(), fundPurchaseInfoBO.getPurchaseDate());
                    if (null == fundPurchaseInfoBO.getShare()) {
                        // 份额为空，需要根据费率计算
                        double feeRate = fundPurchaseInfoBO.getFeeRate() / 100;
                        double share = -1 * fundPurchaseInfoBO.getAmount() * (1 - feeRate) / fundNetValue.getUnitNetValue();
                        fundPurchaseInfoBO.setShare(new BigDecimal(share).setScale(2, RoundingMode.HALF_UP).doubleValue());
                    }
                    return new FundInvestmentEntity(bo.getFundCode(), fundNetValue.getFundName(), fundPurchaseInfoBO);
                })
                .collect(Collectors.toList());
        log.info("开始更新基金投资信息：{}", JSON.toJSONString(fundInvestmentEntities));
        fundInvestmentDao.saveBatch(fundInvestmentEntities);
    }

    @Override
    public BaseList<FundCalculateVO> calculateBondFund() {
        List<FundCalculateVO> list = new ArrayList<>();
        // 获取所有基金交易记录
        List<FundInvestmentEntity> allFundInvestmentList = fundInvestmentDao.list();
        // 按照基金代码分组
        Map<String, List<FundInvestmentEntity>> fundGroup = allFundInvestmentList.stream().collect(Collectors.groupingBy(FundInvestmentEntity::getFundCode));
        // 获取所有基金的最新净值
        TradeDateHistEntity previousTradeDate = tradeDateHistDao.getPreviousTradeDate();
        List<FundNetValueEntity> fundNetValueEntities = fundNetValueDao.listFundNetValue(fundGroup.keySet(), previousTradeDate.getTradeDate());
        Map<String, FundNetValueEntity> fundNetValueMap = fundNetValueEntities.stream().collect(Collectors.toMap(FundNetValueEntity::getFundCode, netValue -> netValue));
        // 遍历每个基金的交易记录，计算总金额和总份额
        fundGroup.forEach((fundCode, fundInvestmentList) -> {
            FundCalculateVO fundCalculateVO = new FundCalculateVO();
            fundCalculateVO.setFundCode(fundCode);
            fundCalculateVO.setFundName(fundInvestmentList.get(0).getFundName());
            // 当前净值
            FundNetValueEntity fundNetValueEntity = fundNetValueMap.get(fundCode);
            fundCalculateVO.setUnitNetValue(fundNetValueEntity.getUnitNetValue());
            // 总投入
            double totalAmount = fundInvestmentList.stream().mapToDouble(FundInvestmentEntity::getAmount).sum();
            fundCalculateVO.setTotalAmount(new BigDecimal(totalAmount).setScale(2, RoundingMode.HALF_UP).doubleValue());
            // 总份额
            double totalShare = fundInvestmentList.stream().mapToDouble(FundInvestmentEntity::getShare).sum();
            fundCalculateVO.setTotalShare(new BigDecimal(totalShare).setScale(2, RoundingMode.HALF_UP).doubleValue());
            // 当前净值
            double presentValue = fundNetValueEntity.getUnitNetValue() * totalShare;
            fundCalculateVO.setPresentValue(new BigDecimal(presentValue).setScale(2, RoundingMode.HALF_UP).doubleValue());
            // 收益
            fundCalculateVO.setProfit(new BigDecimal(presentValue + totalAmount).setScale(2, RoundingMode.HALF_UP).doubleValue());
            // 投入记录列表
            List<FundPurchaseInfoVO> fundPurchaseList = fundInvestmentList.stream()
                    .map(investment -> BeanUtil.copyProperties(investment, FundPurchaseInfoVO.class))
                    .collect(Collectors.toList());
            fundCalculateVO.setPurchaseList(fundPurchaseList);
            // 计算xirr
            List<Transaction> transactions = fundPurchaseList.stream()
                    .map(purchase -> new Transaction(purchase.getAmount(), DateUtil.parse(purchase.getPurchaseDate(), DatePattern.PURE_DATE_PATTERN).toString(DatePattern.NORM_DATE_PATTERN)))
                    .collect(Collectors.toList());
            transactions.add(new Transaction(presentValue, DateUtil.format(DateUtil.date(), DatePattern.NORM_DATE_PATTERN)));
            double xirr = -1 * fundCalculateVO.getProfit() / totalAmount;
            try {
                xirr = new Xirr(transactions).xirr();
            } catch (Exception e) {
                log.info("计算xirr失败，直接使用持有收益率");
            }
            NumberFormat numberFormat = NumberFormat.getPercentInstance();
            numberFormat.setMinimumFractionDigits(2);
            fundCalculateVO.setYield(numberFormat.format(xirr));
            list.add(fundCalculateVO);
        });
        BaseList<FundCalculateVO> result = new BaseList<>();
        result.setList(list);
        result.setTotal(list.size());
        return result;
    }
}
