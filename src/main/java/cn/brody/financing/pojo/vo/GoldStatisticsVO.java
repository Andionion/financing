package cn.brody.financing.pojo.vo;

import lombok.Data;

import java.util.List;

/**
 * FundStatisticsVO
 *
 * @author chenyifu6
 * @since 2024/11/08 13:44
 */
@Data
public class GoldStatisticsVO {
    /**
     * 总克数
     */
    private Double totalWeight;
    /**
     * 纸面金重量
     */
    private Double paperGoldWeight;
    /**
     * 实体金重量
     */
    private Double physicalGoldWeight;
    /**
     * 当前单价
     */
    private Double currentUnitPrice;
    /**
     * 现值
     */
    private Double presentValue;
    /**
     * 净投入
     */
    private Double netInvestment;
    /**
     * 收益
     */
    private Double profit;
    /**
     * 收益率
     */
    private String yield;
    /**
     * 详细交易记录列表
     */
    private List<GoldTradeVO> tradeDetailList;
}
