package cn.brody.financing.pojo.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * FundTradeAddBO
 *
 * @author chenyifu6
 * @since 2024/11/08 09:14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoldTradeAddBO {

    /**
     * 交易时间（由业务方填充）
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate tradeDate;
    /**
     * 交易金额（精确到分）
     */
    private Double amount;
    /**
     * 交易克数
     */
    private Double weight;
    /**
     * 单价（最多四位小数）
     */
    private Double unitPrice;
    /**
     * 费率
     */
    private Double rate;
    /**
     * 交易类型（purchase-申购，redeem-赎回）
     */
    private String tradeType;
    /**
     * 黄金类型（paper-纸面金，physical-实体金）
     */
    private String goldType;
}
