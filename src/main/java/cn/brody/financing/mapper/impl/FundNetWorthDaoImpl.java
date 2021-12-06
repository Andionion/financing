package cn.brody.financing.mapper.impl;

import cn.brody.financing.mapper.FundNetWorthDao;
import cn.brody.financing.mapper.FundNetWorthMapper;
import cn.brody.financing.pojo.entity.FundNetWorthEntity;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

/**
 * @author Brody
 * @date 2021/10/26
 **/
@Service
public class FundNetWorthDaoImpl extends ServiceImpl<FundNetWorthMapper, FundNetWorthEntity> implements FundNetWorthDao {


    @Override
    public FundNetWorthEntity getNetWorth(String code, LocalDate confirmDate) {
        return lambdaQuery().eq(FundNetWorthEntity::getCode, code)
                .eq(FundNetWorthEntity::getDate, confirmDate).one();
    }

    @Override
    public List<FundNetWorthEntity> listNetWorth(String code, Collection<LocalDate> confirmDateList) {
        return lambdaQuery().eq(FundNetWorthEntity::getCode, code)
                .in(FundNetWorthEntity::getDate, confirmDateList).list();
    }

    @Override
    public List<FundNetWorthEntity> listNetWorthList(String code) {
        return lambdaQuery().eq(FundNetWorthEntity::getCode, code).list();
    }

    @Override
    public Boolean isExist(LocalDate date) {
        return lambdaQuery().eq(FundNetWorthEntity::getDate, date).count() > 0;
    }

    @Override
    public Boolean removeNetWorth(String code) {
        return remove(lambdaQuery().eq(FundNetWorthEntity::getCode, code));
    }
}
