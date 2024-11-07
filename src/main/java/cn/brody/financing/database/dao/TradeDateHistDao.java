package cn.brody.financing.database.dao;

import cn.brody.financing.database.entity.TradeDateHistEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * TradeDateHistDao
 *
 * @author chenyifu6
 * @since 2024/11/07 17:02
 */
public interface TradeDateHistDao extends IService<TradeDateHistEntity> {


    /**
     * 获取上一个交易日期。
     *
     * @return 返回上一个交易日期的实体对象。
     */
    TradeDateHistEntity getPreviousTradeDate();

    /**
     * 获取最后一个交易日
     *
     * @return 最后交易日
     */
    TradeDateHistEntity getLastTradeDate();

    /**
     * 清空所有数据。
     */
    void clear();
}
