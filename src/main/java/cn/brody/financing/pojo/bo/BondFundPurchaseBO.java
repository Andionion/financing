package cn.brody.financing.pojo.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * BondFundPurchaseBO
 *
 * @author chenyifu6
 * @since 2024/11/08 09:14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class BondFundPurchaseBO extends FundCodeBO {

    /**
     * 基金购买信息列表
     */
    List<FundPurchaseInfoBO> list;
}