package cn.brody.financing.database.dao.impl;

import cn.brody.financing.database.dao.FundInvestmentDao;
import cn.brody.financing.database.entity.FundInvestmentEntity;
import cn.brody.financing.database.mapper.FundInvestmentMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * FundInvestmentDaoImpl
 *
 * @author chenyifu6
 * @since 2024/11/08 09:58
 */
@Service
public class FundInvestmentDaoImpl extends ServiceImpl<FundInvestmentMapper, FundInvestmentEntity> implements FundInvestmentDao {
}
