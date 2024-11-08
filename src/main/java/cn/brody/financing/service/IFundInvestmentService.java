package cn.brody.financing.service;

import cn.brody.financing.pojo.bo.BondFundPurchaseBO;

/**
 * IFundInvestmentService
 *
 * @author chenyifu6
 * @since 2024/11/08 09:43
 */
public interface IFundInvestmentService {


    /**
     * 购买债券基金。
     *
     * @param bo 包含购买债券基金所需信息的BondFundPurchaseBO对象。
     */
    void purchaseBondFund(BondFundPurchaseBO bo);
}
