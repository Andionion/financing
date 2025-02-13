package cn.brody.financing.database.dao;

import cn.brody.financing.database.entity.FundTradeEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * FundInvestmentDao
 *
 * @author chenyifu6
 * @since 2024/11/08 09:58
 */
public interface FundInvestmentDao extends IService<FundTradeEntity> {

    /**
     * @param belong
     * @return
     */
    List<FundTradeEntity> listByInvestmentBelong(String belong);

    /**
     * 获取所有交易方
     *
     * @return
     */
    List<String> listAllNames();
}
