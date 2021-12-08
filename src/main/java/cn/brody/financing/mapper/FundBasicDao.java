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

    /**
     * 判断基金是否存在
     *
     * @param code 基金代码
     * @return 存在则返回 true，否则返回 false
     */
    boolean isFundExist(String code);
}