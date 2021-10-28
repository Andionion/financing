package cn.brody.financing.mapper.impl;

import cn.brody.financing.mapper.FundBasicDao;
import cn.brody.financing.mapper.FundBasicMapper;
import cn.brody.financing.pojo.entity.FundBasicEntity;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author Brody
 * @date 2021/10/26
 **/
@Service
public class FundBasicDaoImpl extends ServiceImpl<FundBasicMapper, FundBasicEntity> implements FundBasicDao {

    @Override
    public FundBasicEntity getByCode(String code) {
        if (StrUtil.isBlank(code)) {
            log.error("基金代码不能为空");
            throw new RuntimeException();
        }
        return lambdaQuery().eq(FundBasicEntity::getCode, code).one();
    }
}
