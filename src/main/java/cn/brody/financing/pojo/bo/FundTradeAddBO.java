package cn.brody.financing.pojo.bo;

import cn.brody.financing.enums.TradeTypeEnum;
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
public class FundTradeAddBO {
    /**
     * 基金代码
     */
    private String fundCode;
    /**
     * 交易所属方
     */
    private String belong;
    /**
     * 交易时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate tradeDate;
    /**
     * 交易类型
     */
    private TradeTypeEnum tradeType;
    /**
     * 金额
     */
    private Double amount;
    /**
     * 费率
     */
    private Double feeRate;
    /**
     * 份额
     */
    private Double share;
}
