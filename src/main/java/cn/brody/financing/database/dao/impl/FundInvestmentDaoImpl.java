package cn.brody.financing.database.dao.impl;

import cn.brody.financing.database.dao.FundInvestmentDao;
import cn.brody.financing.database.entity.FundTradeEntity;
import cn.brody.financing.database.mapper.FundInvestmentMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * FundInvestmentDaoImpl
 *
 * @author chenyifu6
 * @since 2024/11/08 09:58
 */
@Service
public class FundInvestmentDaoImpl extends ServiceImpl<FundInvestmentMapper, FundTradeEntity> implements FundInvestmentDao {

    @Override
    public List<FundTradeEntity> listByInvestmentBelong(String belong) {
        return lambdaQuery()
                .eq(FundTradeEntity::getBelong, belong)
                .list();
    }

    @Override
    public List<String> listAllNames() {
        return lambdaQuery().list()
                .stream()
                .map(FundTradeEntity::getBelong)
                .distinct().collect(Collectors.toList());
    }
}
