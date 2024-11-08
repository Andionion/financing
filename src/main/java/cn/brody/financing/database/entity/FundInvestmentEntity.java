package cn.brody.financing.database.entity;

import cn.brody.financing.pojo.bo.FundPurchaseInfoBO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * FundInvestmentEntity
 *
 * @author chenyifu6
 * @since 2024/11/08 09:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("fund_investment")
public class FundInvestmentEntity {

    /**
     * 主键，自增
     */
    private Long id;
    /**
     * 基金代码
     */
    private String fundCode;
    /**
     * 基金名称
     */
    private String fundName;
    /**
     * 投资日期
     */
    private String purchaseDate;
    /**
     * 购买金额
     */
    private Double amount;
    /**
     * 购买份额
     */
    private Double share;

    public FundInvestmentEntity(String fundCode, String fundName, FundPurchaseInfoBO fundPurchaseInfoBO) {
        this.fundCode = fundCode;
        this.purchaseDate = fundPurchaseInfoBO.getPurchaseDate();
        this.amount = fundPurchaseInfoBO.getAmount();
        this.share = fundPurchaseInfoBO.getShare();
    }
}