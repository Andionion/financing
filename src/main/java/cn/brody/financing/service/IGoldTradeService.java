package cn.brody.financing.service;

import cn.brody.financing.pojo.bo.GoldTradeAddBO;
import cn.brody.financing.pojo.vo.GoldStatisticsVO;
import cn.brody.financing.pojo.vo.GoldTradeVO;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * IGoldTradeService
 *
 * @author BrodyChen
 * @since 2025/02/25 14:08
 */
public interface IGoldTradeService {

    /**
     * 新增黄金交易
     *
     * @param bo 黄金交易请求参数
     */
    void addGoldTrade(GoldTradeAddBO bo);

    /**
     * 获取黄金交易列表（分页）
     *
     * @param page 页码
     * @param size 每页大小
     * @return 分页交易列表
     */
    IPage<GoldTradeVO> listGoldTrades(int page, int size);

    /**
     * 统计黄金交易的列表。
     * <p>
     * 因统计数据只有一行，和基金不一样，所以既展示相关统计数据，也展示详细交易数据
     *
     * @return 返回一个包含所有黄金交易信息的BaseList对象。
     */
    GoldStatisticsVO tabulate();
}
