package cn.brody.financing.database.dao;

import cn.brody.financing.database.entity.FundTradeEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * FundTradeDao
 *
 * @author chenyifu6
 * @since 2024/11/08 09:58
 */
public interface FundTradeDao extends IService<FundTradeEntity> {

    /**
     * @param belong
     * @return
     */
    List<FundTradeEntity> listByTradeBelong(String belong);

    /**
     * 获取所有交易方
     *
     * @return
     */
    List<String> listAllNames();
}
