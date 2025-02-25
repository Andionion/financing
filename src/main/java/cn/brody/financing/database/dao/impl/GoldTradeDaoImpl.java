package cn.brody.financing.database.dao.impl;

import cn.brody.financing.database.dao.GoldTradeDao;
import cn.brody.financing.database.entity.GoldTradeEntity;
import cn.brody.financing.database.mapper.GoldTradeMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * GoldTradeDaoImpl
 *
 * @author BrodyChen
 * @since 2025/02/23 21:39
 */
@Service
public class GoldTradeDaoImpl extends ServiceImpl<GoldTradeMapper, GoldTradeEntity> implements GoldTradeDao {

    @Override
    public List<GoldTradeEntity> listByTradeDateAsc() {
        return lambdaQuery().orderByAsc(GoldTradeEntity::getTradeTime).list();
    }
}
