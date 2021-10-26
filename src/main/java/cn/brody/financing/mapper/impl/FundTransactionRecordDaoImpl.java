package cn.brody.financing.mapper.impl;

import cn.brody.financing.mapper.FundTransactionRecordDao;
import cn.brody.financing.mapper.FundTransactionRecordMapper;
import cn.brody.financing.pojo.entity.FundTransactionRecordEntity;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author Brody
 * @date 2021/10/26
 **/
@Service
public class FundTransactionRecordDaoImpl extends ServiceImpl<FundTransactionRecordMapper, FundTransactionRecordEntity> implements FundTransactionRecordDao {
}
