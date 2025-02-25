package cn.brody.financing.service.impl;

import cn.brody.financing.database.dao.GoldTradeDao;
import cn.brody.financing.database.entity.GoldTradeEntity;
import cn.brody.financing.pojo.base.BaseList;
import cn.brody.financing.pojo.vo.GoldTradeVO;
import cn.brody.financing.service.IGoldTradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * GoldTradeServiceImpl
 *
 * @author chenyifu6
 * @since 2025/02/25 14:22
 */
@Service
public class GoldTradeServiceImpl implements IGoldTradeService {

    @Autowired
    private GoldTradeDao goldTradeDao;

    @Override
    public BaseList<GoldTradeVO> calculate() {
        List<GoldTradeEntity> goldTradeEntities = goldTradeDao.listByTradeDateAsc();
        
        return null;
    }
}
