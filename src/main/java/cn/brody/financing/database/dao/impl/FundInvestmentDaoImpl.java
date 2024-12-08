package cn.brody.financing.database.dao.impl;

import cn.brody.financing.database.dao.FundInvestmentDao;
import cn.brody.financing.database.entity.FundInvestmentEntity;
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
public class FundInvestmentDaoImpl extends ServiceImpl<FundInvestmentMapper, FundInvestmentEntity> implements FundInvestmentDao {

    @Override
    public List<FundInvestmentEntity> listByInvestmentBelong(String belong) {
        return lambdaQuery()
                .eq(FundInvestmentEntity::getBelong, belong)
                .list();
    }

    @Override
    public List<String> listAllNames() {
        return lambdaQuery().list()
                .stream()
                .map(FundInvestmentEntity::getBelong)
                .distinct().collect(Collectors.toList());
    }
}
