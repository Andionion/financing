package cn.brody.financing.database.dao.impl;

import cn.brody.financing.database.dao.FundTradeDao;
import cn.brody.financing.database.entity.FundTradeEntity;
import cn.brody.financing.database.mapper.FundTradeMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * FundTradeDaoImpl
 *
 * @author chenyifu6
 * @since 2024/11/08 09:58
 */
@Service
public class FundTradeDaoImpl extends ServiceImpl<FundTradeMapper, FundTradeEntity> implements FundTradeDao {

    @Override
    public List<FundTradeEntity> listByBelong(String belong) {
        return lambdaQuery()
                .eq(FundTradeEntity::getBelong, belong)
                .list();
    }

    @Override
    public List<FundTradeEntity> listByFundCodeAndBelong(String fundCode, String belong) {
        return lambdaQuery()
                .eq(FundTradeEntity::getFundCode, fundCode)
                .eq(FundTradeEntity::getBelong, belong)
                .list();
    }

    @Override
    public List<String> listAllBelongs() {
        return lambdaQuery().list()
                .stream()
                .map(FundTradeEntity::getBelong)
                .distinct().collect(Collectors.toList());
    }
}
