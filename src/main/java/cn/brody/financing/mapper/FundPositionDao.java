package cn.brody.financing.mapper;

import cn.brody.financing.pojo.entity.FundPositionEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Brody
 * @date 2021/10/25
 **/
public interface FundPositionDao extends IService<FundPositionEntity> {
    /**
     * 从基金代码中获取持仓
     *
     * @param code
     * @return
     */
    FundPositionEntity getByFundCode(String code);
}