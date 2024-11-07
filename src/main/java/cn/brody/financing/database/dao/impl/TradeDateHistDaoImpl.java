package cn.brody.financing.database.dao.impl;

import cn.brody.financing.database.dao.TradeDateHistDao;
import cn.brody.financing.database.entity.TradeDateHistEntity;
import cn.brody.financing.database.mapper.TradeDateHistMapper;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * TradeDateHistDaoImpl
 *
 * @author chenyifu6
 * @since 2024/11/07 15:30
 */
@Service
public class TradeDateHistDaoImpl extends ServiceImpl<TradeDateHistMapper, TradeDateHistEntity> implements TradeDateHistDao {

    @Override
    public TradeDateHistEntity getPreviousTradeDate() {
        return lambdaQuery()
                .lt(TradeDateHistEntity::getTradeDate, DateUtil.format(DateUtil.date(), DatePattern.PURE_DATE_PATTERN))
                .orderByDesc(TradeDateHistEntity::getTradeDate).last("limit 1").one();
    }

    @Override
    public TradeDateHistEntity getLastTradeDate() {
        return lambdaQuery()
                .orderByDesc(TradeDateHistEntity::getTradeDate)
                .last("limit 1")
                .one();
    }

    @Override
    public void clear() {
        if (exists(new QueryWrapper<>())) {
            baseMapper.delete(new QueryWrapper<>());
        }
    }
}
