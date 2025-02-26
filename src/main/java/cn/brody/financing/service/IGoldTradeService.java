package cn.brody.financing.service;

import cn.brody.financing.pojo.vo.GoldStatisticsVO;

/**
 * IGoldTradeService
 *
 * @author BrodyChen
 * @since 2025/02/25 14:08
 */
public interface IGoldTradeService {

    /**
     * 统计黄金交易的列表。
     * <p>
     * 因统计数据只有一行，和基金不一样，所以既展示相关统计数据，也展示详细交易数据
     *
     * @return 返回一个包含所有黄金交易信息的BaseList对象。
     */
    GoldStatisticsVO tabulate();
}
