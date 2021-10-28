package cn.brody.financing.mapper;

import cn.brody.financing.pojo.entity.FundBasicEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Brody
 * @date 2021/10/25
 **/
public interface FundBasicDao extends IService<FundBasicEntity> {
    /**
     * 通过基金代码查找基金实体类
     *
     * @param code
     * @return
     */
    FundBasicEntity getByCode(String code);
}