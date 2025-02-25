package cn.brody.financing.service;

import cn.brody.financing.pojo.base.BaseList;
import cn.brody.financing.pojo.bo.FundTradeAddBO;
import cn.brody.financing.pojo.vo.FundTradeVO;

import java.util.List;

/**
 * IFundTradeService
 *
 * @author chenyifu6
 * @since 2024/11/08 09:43
 */
public interface IFundTradeService {


    /**
     * 购买债券基金。
     *
     * @param bo 包含购买债券基金所需信息的BondFundPurchaseBO对象。
     */
    void trade(FundTradeAddBO bo);


    /**
     * 计算基金交易数据。
     *
     * @return 返回一个包含基金交易数据的BaseList对象，其中每个元素都是FundTradeVO类型。
     */
    BaseList<FundTradeVO> calculate();


    /**
     * 查询交易所属方的统计数据
     *
     * @param belong 需要查询的基金所属类别。
     * @return 返回一个包含基金交易信息的列表，每个元素是一个FundTradeVO对象。
     */
    BaseList<FundTradeVO> calculate(String belong);


    /**
     * 查询所有交易方。
     *
     * @return 返回一个包含所有名称的字符串列表。
     */
    List<String> listAllNames();
}
