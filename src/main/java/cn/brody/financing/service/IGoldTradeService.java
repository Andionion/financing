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

    BaseList<GoldTradeVO> calculate();
}
