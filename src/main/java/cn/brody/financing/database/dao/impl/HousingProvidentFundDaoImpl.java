package cn.brody.financing.database.dao.impl;

import cn.brody.financing.database.dao.HousingProvidentFundDao;
import cn.brody.financing.database.entity.HousingProvidentFundEntity;
import cn.brody.financing.database.mapper.HousingProvidentFundMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * HousingProvidentFundDaoImpl
 *
 * @author BrodyChen
 * @since 2025/03/10 11:41
 */
@Service
public class HousingProvidentFundDaoImpl extends ServiceImpl<HousingProvidentFundMapper, HousingProvidentFundEntity> implements HousingProvidentFundDao {

    @Override
    public HousingProvidentFundEntity getLastRecord() {
        return lambdaQuery()
                .orderByDesc(HousingProvidentFundEntity::getOperationDate)
                .last("limit 1").one();
    }
}
