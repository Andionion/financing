package cn.brody.financing.pojo.vo;

import lombok.Data;

import java.util.List;

/**
 * FundCalculateVO
 *
 * @author chenyifu6
 * @since 2024/11/08 13:44
 */
@Data
public class FundCalculateVO {
    /**
     * 基金代码
     */
    private String fundCode;
    /**
     * 基金名称
     */
    private String fundName;
    /**
     * 总投入
     */
    private Double totalAmount;
    /**
     * 总份额
     */
    private Double totalShare;
    /**
     * 现值
     */
    private Double presentValue;
    /**
     * 收益
     */
    private Double profit;
    /**
     * 收益率
     */
    private String yield;
    /**
     * 投入记录列表
     */
    private List<FundPurchaseInfoVO> purchaseList;
}