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
import java.time.LocalDateTime;

/**
 * GoldTradeEntity
 *
 * @author BrodyChen
 * @since 2025/02/23 21:29
 */
@TableName("gold_trade") // 表名保持为 gold_trade
@Data // 自动生成 Getter/Setter/toString/equalsHashCode
@NoArgsConstructor // 无参构造器
@AllArgsConstructor // 全参构造器
public class GoldTradeEntity {


    @TableId
    private Long id;
    /**
     * 交易金额（精确到分）
     */
    @Column(name = "amount", precision = 12, scale = 2)
    private BigDecimal amount;
    /**
     * 单价（最多四位小数）
     */
    @Column(name = "unit_price", precision = 10, scale = 4)
    private BigDecimal unitPrice;
    /**
     * 手续费（精确到分）
     */
    @Column(name = "handling_fee", precision = 6, scale = 2)
    private BigDecimal handlingFee;
    /**
     * 交易类型（purchase-申购，redeem-赎回）
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "trade_type", nullable = false)
    private TradeTypeEnum tradeType;
    /**
     * 黄金类型（paper-纸面金，physical-实体金）
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "gold_type", nullable = false)
    private GoldTypeEnum goldType;
    /**
     * 交易时间（由业务方填充）
     */
    @Column(name = "trade_time", nullable = false)
    private LocalDateTime tradeTime;
}
