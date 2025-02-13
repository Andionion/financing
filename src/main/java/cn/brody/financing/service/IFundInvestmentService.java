package cn.brody.financing.service;

import cn.brody.financing.pojo.base.BaseList;
import cn.brody.financing.pojo.bo.FundTradeBO;
import cn.brody.financing.pojo.vo.FundTradeVO;

import java.util.List;

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
    void trade(FundTradeBO bo);

    BaseList<FundTradeVO> calculate();

    /**
     * 查询交易所属方的校验
     *
     * @param belong
     * @return
     */
    BaseList<FundTradeVO> calculate(String belong);

    /**
     * 查询所有交易方
     *
     * @return
     */
    List<String> listAllNames();
}
