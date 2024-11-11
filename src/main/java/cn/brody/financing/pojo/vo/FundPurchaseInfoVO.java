package cn.brody.financing.pojo.vo;

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
public class FundPurchaseInfoVO {

    /**
     * 记录购买操作发生的日期
     */
    private String purchaseDate;
    /**
     * 金额
     */
    private Double amount;
    /**
     * 份额
     */
    private Double share;
}
