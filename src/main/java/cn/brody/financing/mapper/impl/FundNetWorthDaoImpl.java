package cn.brody.financing.mapper.impl;

import cn.brody.financing.mapper.FundNetWorthDao;
import cn.brody.financing.mapper.FundNetWorthMapper;
import cn.brody.financing.pojo.entity.FundNetWorthEntity;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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
}
