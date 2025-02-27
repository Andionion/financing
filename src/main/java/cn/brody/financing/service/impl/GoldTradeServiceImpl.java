package cn.brody.financing.service.impl;

import cn.brody.financing.database.dao.FundNetValueDao;
import cn.brody.financing.database.dao.GoldTradeDao;
import cn.brody.financing.database.dao.TradeDateHistDao;
import cn.brody.financing.database.entity.FundNetValueEntity;
import cn.brody.financing.database.entity.GoldTradeEntity;
import cn.brody.financing.database.entity.TradeDateHistEntity;
import cn.brody.financing.enums.GoldTypeEnum;
import cn.brody.financing.enums.TradeTypeEnum;
import cn.brody.financing.pojo.bo.GoldTradeAddBO;
import cn.brody.financing.pojo.vo.GoldStatisticsVO;
import cn.brody.financing.pojo.vo.GoldTradeVO;
import cn.brody.financing.service.IGoldTradeService;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.decampo.xirr.Transaction;
import org.decampo.xirr.Xirr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.Comparator;
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
    public void addGoldTrade(GoldTradeAddBO bo) {
        log.info("开始添加黄金交易记录，请求参数：{}", JSONUtil.toJsonStr(bo));
        GoldTradeEntity goldTradeEntity = new GoldTradeEntity();
        goldTradeEntity.setTradeTime(bo.getTradeTime());
        goldTradeEntity.setAmount(BigDecimal.valueOf(bo.getAmount()));
        goldTradeEntity.setUnitPrice(BigDecimal.valueOf(bo.getUnitPrice()));
        goldTradeEntity.setTradeType(TradeTypeEnum.forValue(bo.getTradeType()));
        goldTradeEntity.setGoldType(GoldTypeEnum.forValue(bo.getGoldType()));
        // 如果是买入，则计算方式是从金额出发，手续费 = 金额*费率，重量 = （金额-手续费）/单价
        if (TradeTypeEnum.PURCHASE.equals(goldTradeEntity.getTradeType())) {
            double handlingFee = bo.getAmount() * bo.getRate() / 100;
            goldTradeEntity.setHandlingFee(convertDoubleToBigDecimal(handlingFee, 2));
            double weight = (bo.getAmount() - handlingFee) / bo.getUnitPrice();
            goldTradeEntity.setWeight(convertDoubleToBigDecimal(weight, 4));
        } else {
            // 如果是卖出，那么计算方式应该是从重量出发，手续费 = 重量*单价*费率 金额 = 重量*单价-手续费
            double handlingFee = bo.getUnitPrice() * bo.getWeight() * bo.getRate();
            goldTradeEntity.setHandlingFee(convertDoubleToBigDecimal(handlingFee, 2));
            double amount = bo.getWeight() * bo.getUnitPrice() - handlingFee;
            goldTradeEntity.setAmount(convertDoubleToBigDecimal(amount, 2));
        }
        log.info("构建黄金交易记录实体类成功{}", JSONUtil.toJsonStr(goldTradeEntity));
        goldTradeDao.save(goldTradeEntity);
    }

    private BigDecimal convertDoubleToBigDecimal(double value, int scale) {
        // 通过字符串构造BigDecimal，避免精度损失
        BigDecimal bd = new BigDecimal(Double.toString(value));
        // 设置小数位数并截断多余部分
        return bd.setScale(scale, RoundingMode.DOWN);
    }

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
        goldStatisticsVO.setPresentValue(BigDecimal.valueOf(goldStatisticsVO.getTotalWeight() * goldStatisticsVO.getCurrentUnitPrice()).setScale(2, RoundingMode.HALF_UP).doubleValue());
        // 净投入 = -1*Sum（赎回）+ Sum（购买）
        double netInvestment = goldTradeEntities.stream()
                .mapToDouble(goldTradeEntity -> TradeTypeEnum.PURCHASE.equals(goldTradeEntity.getTradeType()) ? goldTradeEntity.getAmount().doubleValue() : goldTradeEntity.getAmount().negate().doubleValue())
                .sum();
        goldStatisticsVO.setNetInvestment(BigDecimal.valueOf(netInvestment).setScale(2, RoundingMode.HALF_UP).doubleValue());
        // 收益 = 现值 - 净投入
        goldStatisticsVO.setProfit(BigDecimal.valueOf(goldStatisticsVO.getPresentValue() - netInvestment).setScale(2, RoundingMode.HALF_UP).doubleValue());
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
        NumberFormat numberFormat = NumberFormat.getPercentInstance();
        numberFormat.setMinimumFractionDigits(2);
        goldStatisticsVO.setYield(numberFormat.format(xirr));
        // 交易记录
        goldStatisticsVO.setTradeDetailList(goldTradeEntities.stream()
                .sorted(Comparator.comparing(GoldTradeEntity::getTradeTime).reversed())
                .map(GoldTradeVO::new)
                .collect(Collectors.toList()));
        return goldStatisticsVO;
    }
}
