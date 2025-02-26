package cn.brody.financing.service.impl;

import cn.brody.financing.database.dao.FundNetValueDao;
import cn.brody.financing.database.dao.FundTradeDao;
import cn.brody.financing.database.dao.TradeDateHistDao;
import cn.brody.financing.database.entity.FundNetValueEntity;
import cn.brody.financing.database.entity.FundTradeEntity;
import cn.brody.financing.database.entity.TradeDateHistEntity;
import cn.brody.financing.enums.TradeTypeEnum;
import cn.brody.financing.pojo.bo.FundTradeAddBO;
import cn.brody.financing.pojo.vo.FundStatisticsVO;
import cn.brody.financing.pojo.vo.FundTradeVO;
import cn.brody.financing.service.IFundNetValueService;
import cn.brody.financing.service.IFundTradeService;
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
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * FundTradeServiceImpl
 *
 * @author chenyifu6
 * @since 2024/11/08 09:43
 */
@Slf4j
@Service
public class FundTradeServiceImpl implements IFundTradeService {

    @Autowired
    private FundTradeDao fundTradeDao;
    @Autowired
    private FundNetValueDao fundNetValueDao;
    @Autowired
    private TradeDateHistDao tradeDateHistDao;
    @Autowired
    private IFundNetValueService fundNetValueService;

    @Override
    public void trade(FundTradeAddBO bo) {
        // 获取基金信息
        List<FundTradeEntity> fundInvestmentEntities = bo.getList()
                .stream()
                .map(fundTradeInfoBO -> {
                    FundNetValueEntity fundNetValue = fundNetValueDao.getFundNetValue(bo.getFundCode(), fundTradeInfoBO.getTradeDate());
                    if (null == fundTradeInfoBO.getShare()) {
                        // 份额为空，需要根据费率计算
                        double feeRate = fundTradeInfoBO.getFeeRate() / 100;
                        double share = fundTradeInfoBO.getAmount() * (1 - feeRate) / fundNetValue.getUnitNetValue();
                        fundTradeInfoBO.setShare(new BigDecimal(share).setScale(2, RoundingMode.HALF_UP).doubleValue());
                    }
                    return new FundTradeEntity(bo.getFundCode(), bo.getBelong(), fundNetValue.getFundName(), fundTradeInfoBO);
                })
                .collect(Collectors.toList());
        log.info("开始更新基金投资信息：{}", JSON.toJSONString(fundInvestmentEntities));
        fundTradeDao.saveBatch(fundInvestmentEntities);
    }

    @Override
    public List<FundStatisticsVO> tabulate(String belong) {
        List<FundTradeEntity> fundInvestmentEntities = fundTradeDao.listByBelong(belong);
        return getFundCalculateVOBaseList(fundInvestmentEntities);
    }


    @Override
    public List<FundTradeVO> listFundTrade(String fundCode, String belong) {
        List<FundTradeEntity> fundTradeEntities = fundTradeDao.listByFundCodeAndBelong(fundCode, belong);
        // 投入记录列表
        return fundTradeEntities.stream()
                .map(investment -> BeanUtil.copyProperties(investment, FundTradeVO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> listAllBelongs() {
        return fundTradeDao.listAllBelongs();
    }

    /**
     * 获取基金交易的计算基础列表。
     *
     * @param allFundTradeList 所有基金的交易记录列表。
     * @return 返回一个包含基金交易计算结果的列表，每个元素是一个FundTradeVO对象，包含了基金代码、基金名称、当前净值、总投入金额、总份额、当前价值、收益和投资详情等数据。
     */
    private List<FundStatisticsVO> getFundCalculateVOBaseList(List<FundTradeEntity> allFundTradeList) {
        List<FundStatisticsVO> list = new ArrayList<>();
        // 全部投资按照基金代码分组
        Map<String, List<FundTradeEntity>> fundGroup = allFundTradeList.stream().collect(Collectors.groupingBy(FundTradeEntity::getFundCode));
        // 获取所有基金的最新净值
        TradeDateHistEntity previousTradeDate = tradeDateHistDao.getPreviousTradeDate();
        List<FundNetValueEntity> fundNetValueEntities = fundNetValueDao.listFundNetValue(fundGroup.keySet(), previousTradeDate.getTradeDate());
        // 如果最新日期的基金净值列表长度和所有基金的投资记录不相等，说明少，需要重新更新一次
        if (fundNetValueEntities.size() != fundGroup.size()) {
            List<String> existNetValueFundCodes = fundNetValueEntities.stream().map(FundNetValueEntity::getFundCode).collect(Collectors.toList());
            List<String> notExistNetValueFundCodes = fundGroup.keySet().stream().filter(fundCode -> !existNetValueFundCodes.contains(fundCode)).collect(Collectors.toList());
            log.info("最新日期的基金净值为空，重新更新一次基金净值，日期：{}，基金列表：{}", previousTradeDate.getTradeDate(), notExistNetValueFundCodes);
            fundNetValueService.updateTimedFundNetValue(notExistNetValueFundCodes);
            fundNetValueEntities = fundNetValueDao.listFundNetValue(fundGroup.keySet(), previousTradeDate.getTradeDate());
        }
        Map<String, FundNetValueEntity> fundNetValueMap = fundNetValueEntities.stream()
                .collect(Collectors.toMap(FundNetValueEntity::getFundCode, netValue -> netValue));
        // 遍历每个基金的交易记录，计算总金额和总份额
        fundGroup.forEach((fundCode, fundTradeList) -> {
            FundStatisticsVO fundStatisticsVO = new FundStatisticsVO();
            fundStatisticsVO.setFundCode(fundCode);
            fundStatisticsVO.setFundName(fundTradeList.get(0).getFundName());
            // 当前净值
            FundNetValueEntity fundNetValueEntity = fundNetValueMap.get(fundCode);
            fundStatisticsVO.setUnitNetValue(fundNetValueEntity.getUnitNetValue());
            // 总投入，给人看的，申购为正，赎回为负
            double totalAmount = fundTradeList.stream()
                    .mapToDouble(fundTradeEntity -> (TradeTypeEnum.forValue(fundTradeEntity.getTradeType()) == TradeTypeEnum.PURCHASE ? 1 : -1) * fundTradeEntity.getAmount())
                    .sum();
            fundStatisticsVO.setTotalAmount(new BigDecimal(totalAmount).setScale(2, RoundingMode.HALF_UP).doubleValue());
            // 总份额，申购为正，赎回为负数
            double totalShare = fundTradeList.stream()
                    .mapToDouble(fundTradeEntity -> (TradeTypeEnum.forValue(fundTradeEntity.getTradeType()) == TradeTypeEnum.PURCHASE ? 1 : -1) * fundTradeEntity.getShare())
                    .sum();
            fundStatisticsVO.setTotalShare(new BigDecimal(totalShare).setScale(2, RoundingMode.HALF_UP).doubleValue());
            // 当前净值
            double presentValue = fundNetValueEntity.getUnitNetValue() * totalShare;
            fundStatisticsVO.setPresentValue(new BigDecimal(presentValue).setScale(2, RoundingMode.HALF_UP).doubleValue());
            // 收益
            fundStatisticsVO.setProfit(new BigDecimal(presentValue - totalAmount).setScale(2, RoundingMode.HALF_UP).doubleValue());
            // 投入记录列表
            List<FundTradeVO> fundPurchaseList = fundTradeList.stream()
                    .map(investment -> BeanUtil.copyProperties(investment, FundTradeVO.class))
                    .collect(Collectors.toList());
            // 计算xirr
            List<Transaction> transactions = fundPurchaseList.stream()
                    // 计算xirr，申购为负，赎回为正
                    .map(purchase -> new Transaction((TradeTypeEnum.forValue(purchase.getTradeType()) == TradeTypeEnum.PURCHASE ? -1 : 1) * purchase.getAmount()
                            , DateUtil.parse(purchase.getTradeDate(), DatePattern.PURE_DATE_PATTERN).toString(DatePattern.NORM_DATE_PATTERN)))
                    .collect(Collectors.toList());
            transactions.add(new Transaction(presentValue, DateUtil.format(DateUtil.date(), DatePattern.NORM_DATE_PATTERN)));
            double xirr = -1 * fundStatisticsVO.getProfit() / totalAmount;
            try {
                xirr = new Xirr(transactions).xirr();
            } catch (Exception e) {
                log.info("计算xirr失败，直接使用持有收益率");
            }
            NumberFormat numberFormat = NumberFormat.getPercentInstance();
            numberFormat.setMinimumFractionDigits(2);
            fundStatisticsVO.setYield(numberFormat.format(xirr));
            list.add(fundStatisticsVO);
        });
        // 根据现值降序排列
        list.sort(Comparator.comparing(FundStatisticsVO::getPresentValue).reversed());
        return list;
    }
}
