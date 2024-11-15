package cn.brody.financing.database.dao.impl;

import cn.brody.financing.database.dao.FundNetValueDao;
import cn.brody.financing.database.entity.FundNetValueEntity;
import cn.brody.financing.database.mapper.FundNetValueMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * FundNetValueDaoImpl
 *
 * @author chenyifu6
 * @since 2024/11/05 17:03
 */
@Service
public class FundNetValueDaoImpl extends ServiceImpl<FundNetValueMapper, FundNetValueEntity> implements FundNetValueDao {

    @Override
    public Boolean fundNetValueExists(String fundCode) {
        return lambdaQuery()
                .eq(FundNetValueEntity::getFundCode, fundCode)
                .exists();
    }

    @Override
    public Boolean fundNetValueExists(String fundCode, String tradeDate) {
        return lambdaQuery()
                .eq(FundNetValueEntity::getFundCode, fundCode)
                .eq(FundNetValueEntity::getNetValueDate, tradeDate)
                .exists();
    }

    @Override
    public List<FundNetValueEntity> listFundNetValue(Collection<String> fundCodes, String tradeDate) {
        if (null == tradeDate) {
            tradeDate = lambdaQuery()
                    .orderByDesc(FundNetValueEntity::getNetValueDate)
                    .last("limit 1")
                    .one().getNetValueDate();
        }
        return lambdaQuery()
                .in(FundNetValueEntity::getFundCode, fundCodes)
                .eq(FundNetValueEntity::getNetValueDate, tradeDate)
                .orderByDesc(FundNetValueEntity::getNetValueDate)
                .list();
    }


    @Override
    public FundNetValueEntity getFundNetValue(String fundCode, String tradeDate) {
        return lambdaQuery()
                .eq(FundNetValueEntity::getFundCode, fundCode)
                .eq(null != tradeDate, FundNetValueEntity::getNetValueDate, tradeDate)
                .orderByDesc(FundNetValueEntity::getNetValueDate)
                .last("limit 1")
                .one();
    }

    @Override
    public List<String> findAllSavedFundCode() {
        return baseMapper.selectDistinctFundCodes();
    }
}
