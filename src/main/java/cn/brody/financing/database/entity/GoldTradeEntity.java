package cn.brody.financing.database.entity;

import cn.brody.financing.enums.GoldTypeEnum;
import cn.brody.financing.enums.TradeTypeEnum;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * GoldTradeEntity
 *
 * @author BrodyChen
 * @since 2025/02/23 21:29
 */
@TableName("gold_trade")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoldTradeEntity {


    @TableId
    private Long id;
    /**
     * 交易时间（由业务方填充）
     */
    private LocalDate tradeDate;
    /**
     * 交易金额（精确到分）
     */
    @Column(precision = 12, scale = 2)
    private BigDecimal amount;
    /**
     * 单价（最多四位小数）
     */
    @Column(precision = 10, scale = 4)
    private BigDecimal unitPrice;
    /**
     * 手续费（精确到分）
     */
    @Column(precision = 6, scale = 2)
    private BigDecimal handlingFee;
    /**
     * 重量（单位：g，保留四位小数）
     */
    @Column(precision = 6, scale = 4)
    private BigDecimal weight;
    /**
     * 交易类型（purchase-申购，redeem-赎回）
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TradeTypeEnum tradeType;
    /**
     * 黄金类型（paper-纸面金，physical-实体金）
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GoldTypeEnum goldType;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}
