package cn.brody.financing.service.impl;

import cn.brody.financing.database.dao.FundNetValueDao;
import cn.brody.financing.database.dao.GoldTradeDao;
import cn.brody.financing.database.dao.TradeDateHistDao;
import cn.brody.financing.database.entity.FundNetValueEntity;
import cn.brody.financing.database.entity.GoldTradeEntity;
import cn.brody.financing.database.entity.TradeDateHistEntity;
import cn.brody.financing.enums.GoldTypeEnum;
import cn.brody.financing.enums.TradeTypeEnum;
import cn.brody.financing.pojo.vo.GoldStatisticsVO;
import cn.brody.financing.pojo.vo.GoldTradeVO;
import cn.brody.financing.service.IGoldTradeService;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.decampo.xirr.Transaction;
import org.decampo.xirr.Xirr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

/**
 * GoldTradeServiceImpl
 *
 * @author chenyifu6
 * @since 2025/02/25 14:22
 */
@Slf4j
@Service
public class GoldTradeServiceImpl implements IGoldTradeService {

    @Autowired
    private GoldTradeDao goldTradeDao;
    @Autowired
    private FundNetValueDao fundNetValueDao;
    @Autowired
    private TradeDateHistDao tradeDateHistDao;

    @Override
    public GoldStatisticsVO tabulate() {
        // 响应参数
        GoldStatisticsVO goldStatisticsVO = new GoldStatisticsVO();
        // 获取所有交易记录
        List<GoldTradeEntity> goldTradeEntities = goldTradeDao.listByTradeDateAsc();
        // 按照黄金类型分组，分别得到纸面金重量/实体金重量 = 对应黄金类型交易重量之和
        Map<GoldTypeEnum, Double> goldWeightMap = goldTradeEntities.stream()
                .collect(Collectors.groupingBy(
                        // 按照黄金类型分组
                        GoldTradeEntity::getGoldType,
                        // 购买为正，赎回为负
                        Collectors.summingDouble(goldtradeEntity -> TradeTypeEnum.PURCHASE.equals(goldtradeEntity.getTradeType()) ? goldtradeEntity.getWeight().doubleValue() : goldtradeEntity.getWeight().negate().doubleValue())
                ));
        // 纸面金重量和
        goldStatisticsVO.setPaperGoldWeight(goldWeightMap.get(GoldTypeEnum.PAPER));
        // 实体金重量和
        goldStatisticsVO.setPhysicalGoldWeight(goldWeightMap.get(GoldTypeEnum.PHYSICAL));
        // 总克数 = 所有交易克数之和
        goldStatisticsVO.setTotalWeight(goldWeightMap.values().stream().mapToDouble(Double::doubleValue).sum());
        // 当前价 = 工银瑞信黄金ETF联接E基金（020341）最新净值 * 425
        TradeDateHistEntity previousTradeDate = tradeDateHistDao.getPreviousTradeDate();
        FundNetValueEntity fundNetValue = fundNetValueDao.getFundNetValue("020341", previousTradeDate.getTradeDate());
        log.info("工银瑞信黄金ETF联接E基金（020341）最新净值为：{}", JSONUtil.toJsonStr(fundNetValue));
        goldStatisticsVO.setCurrentUnitPrice(fundNetValue.getUnitNetValue() * 425);
        // 现值 = 总克数 * 当前价
        goldStatisticsVO.setPresentValue(goldStatisticsVO.getTotalWeight() * goldStatisticsVO.getCurrentUnitPrice());
        // 净投入 = -1*Sum（赎回）+ Sum（购买）
        double netInvestment = goldTradeEntities.stream()
                .mapToDouble(goldTradeEntity -> TradeTypeEnum.PURCHASE.equals(goldTradeEntity.getTradeType()) ? goldTradeEntity.getWeight().doubleValue() : goldTradeEntity.getWeight().negate().doubleValue())
                .sum();
        goldStatisticsVO.setNetInvestment(netInvestment);
        // 收益 = 现值 - 净投入
        goldStatisticsVO.setProfit(goldStatisticsVO.getPresentValue() - netInvestment);
        // 年化利率 = xirr(所有交易日期，金额（购买为负数，赎回为正数）,现值）
        List<Transaction> transactions = goldTradeEntities.stream()
                // 计算xirr，申购为负，赎回为正
                .map(goldTrade -> new Transaction((goldTrade.getTradeType() == TradeTypeEnum.PURCHASE ? -1 : 1) * goldTrade.getAmount().doubleValue()
                        , goldTrade.getTradeTime().format(ISO_LOCAL_DATE)))
                .collect(Collectors.toList());
        transactions.add(new Transaction(goldStatisticsVO.getPresentValue(), LocalDateTime.now().format(ISO_LOCAL_DATE)));
        double xirr = -1 * goldStatisticsVO.getProfit() / goldStatisticsVO.getPresentValue();
        try {
            xirr = new Xirr(transactions).xirr();
        } catch (Exception e) {
            log.info("计算xirr失败，直接使用持有收益率");
        }
        goldStatisticsVO.setAnnualizedRate(xirr);
        // 交易记录
        goldStatisticsVO.setTradeDetailList(goldTradeEntities.stream().map(GoldTradeVO::new).collect(Collectors.toList()));
        return goldStatisticsVO;
    }
}
