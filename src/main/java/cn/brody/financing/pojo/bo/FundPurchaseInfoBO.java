package cn.brody.financing.pojo.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 基金购买的具体信息
 *
 * @author chenyifu6
 * @since 2024/11/08 09:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FundPurchaseInfoBO {

    /**
     * 记录购买操作发生的日期
     */
    private String purchaseDate;
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
