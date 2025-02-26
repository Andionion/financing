package cn.brody.financing.pojo.vo;

import cn.brody.financing.database.entity.GoldTradeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

/**
 * GoldTradeVO
 *
 * @author BrodyChen
 * @since 2025/02/25 14:08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoldTradeVO {
    /**
     * 交易时间（由业务方填充）
     */
    private String tradeTime;
    /**
     * 交易金额（精确到分）
     */
    private Double amount;
    /**
     * 单价（最多四位小数）
     */
    private Double unitPrice;
    /**
     * 重量（单位：g，保留四位小数）
     */
    private Double weight;
    /**
     * 交易类型（purchase-申购，redeem-赎回）
     */
    private String tradeType;
    /**
     * 黄金类型（paper-纸面金，physical-实体金）
     */
    private String goldType;

    public GoldTradeVO(GoldTradeEntity goldTradeEntity) {
        this.tradeTime = goldTradeEntity.getTradeTime().format(DateTimeFormatter.ISO_LOCAL_DATE);
        this.amount = goldTradeEntity.getAmount().doubleValue();
        this.unitPrice = goldTradeEntity.getUnitPrice().doubleValue();
        this.weight = goldTradeEntity.getWeight().doubleValue();
        this.tradeType = goldTradeEntity.getTradeType().name().toLowerCase();
        this.goldType = goldTradeEntity.getGoldType().name().toLowerCase();
    }
}
