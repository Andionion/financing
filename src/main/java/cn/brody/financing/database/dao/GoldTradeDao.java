package cn.brody.financing.database.dao;

import cn.brody.financing.database.entity.GoldTradeEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * GoldTradeDao
 *
 * @author BrodyChen
 * @since 2025/02/23 21:39
 */
public interface GoldTradeDao extends IService<GoldTradeEntity> {

    /**
     * 按交易日期升序列出所有金交易实体。
     *
     * @return 返回一个金交易实体的列表，这些实体按照交易日期从早到晚排序。
     */
    List<GoldTradeEntity> listByTradeDateAsc();
}
