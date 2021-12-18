package cn.brody.financing.mapper.impl;

import cn.brody.financing.mapper.FundTradeRecordDao;
import cn.brody.financing.mapper.FundTradeRecordMapper;
import cn.brody.financing.pojo.entity.FundTradeRecordEntity;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Brody
 * @date 2021/10/26
 **/
@Service
public class FundTradeRecordDaoImpl extends ServiceImpl<FundTradeRecordMapper, FundTradeRecordEntity> implements FundTradeRecordDao {

    @Override
    public Boolean countDateExist(String code, LocalDate date) {
        return lambdaQuery().eq(FundTradeRecordEntity::getCode, code).eq(FundTradeRecordEntity::getConfirmDate, date).count() > 0;
    }

    @Override
    public List<LocalDate> listAlreadyExistRecord(String code, Collection<LocalDate> localDates) {
        List<FundTradeRecordEntity> list = lambdaQuery()
                .eq(FundTradeRecordEntity::getCode, code)
                .in(FundTradeRecordEntity::getConfirmDate, localDates).list();
        return list.stream().map(FundTradeRecordEntity::getConfirmDate).collect(Collectors.toList());
    }

    @Override
    public List<FundTradeRecordEntity> listByFundCode(String code) {
        return lambdaQuery().eq(FundTradeRecordEntity::getCode, code).list();
    }
}
