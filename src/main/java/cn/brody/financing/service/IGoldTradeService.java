package cn.brody.financing.service;

import cn.brody.financing.pojo.base.BaseList;
import cn.brody.financing.pojo.vo.GoldTradeVO;

/**
 * IGoldTradeService
 *
 * @author BrodyChen
 * @since 2025/02/25 14:08
 */
public interface IGoldTradeService {

    /**
     * 统计黄金交易的列表。
     *
     * @return 返回一个包含所有黄金交易信息的BaseList对象。
     */
    BaseList<GoldTradeVO> calculate();
}
