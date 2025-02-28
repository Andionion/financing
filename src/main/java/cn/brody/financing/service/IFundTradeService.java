package cn.brody.financing.service;

import cn.brody.financing.pojo.bo.FundTradeAddBO;
import cn.brody.financing.pojo.vo.FundStatisticsVO;
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
    void add(FundTradeAddBO bo);

    /**
     * 对指定类别的交易进行汇总。
     *
     * @param belong 需要汇总的交易类别。
     * @return 返回一个包含所有符合指定类别交易的列表，每个元素是一个FundTradeVO对象，包含了该交易的详细信息。
     */
    List<FundStatisticsVO> tabulate(String belong);


    /**
     * 获取基金交易信息。
     *
     * @param fundCode 需要查询的基金代码。
     * @param belong   基金所属人。
     * @return 返回一个包含所有符合条件的基金交易信息的列表，每个元素是一个FundTradeVO对象。
     */
    List<FundTradeVO> listFundTrade(String fundCode, String belong);

    /**
     * 查询所有交易方。
     *
     * @return 返回一个包含所有名称的字符串列表。
     */
    List<String> listAllBelongs();
}
