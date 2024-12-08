package cn.brody.financing.database.dao;

import cn.brody.financing.database.entity.FundInvestmentEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * FundInvestmentDao
 *
 * @author chenyifu6
 * @since 2024/11/08 09:58
 */
public interface FundInvestmentDao extends IService<FundInvestmentEntity> {

    /**
     * @param belong
     * @return
     */
    List<FundInvestmentEntity> listByInvestmentBelong(String belong);

    /**
     * 获取所有交易方
     *
     * @return
     */
    List<String> listAllNames();
}
