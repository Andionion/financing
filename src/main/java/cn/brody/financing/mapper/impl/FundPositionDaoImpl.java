package cn.brody.financing.mapper.impl;

import cn.brody.financing.mapper.FundPositionDao;
import cn.brody.financing.mapper.FundPositionMapper;
import cn.brody.financing.pojo.entity.FundPositionEntity;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author Brody
 * @date 2021/10/26
 **/
@Service
public class FundPositionDaoImpl extends ServiceImpl<FundPositionMapper, FundPositionEntity> implements FundPositionDao {

    @Override
    public FundPositionEntity getByFundCode(String code) {
        return lambdaQuery().eq(FundPositionEntity::getCode, code).one();
    }
}
