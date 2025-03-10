package cn.brody.financing.database.dao;

import cn.brody.financing.database.entity.HousingProvidentFundEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * HousingProvidentFundDao
 *
 * @author BrodyChen
 * @since 2025/03/10 11:41
 */
public interface HousingProvidentFundDao extends IService<HousingProvidentFundEntity> {
    /**
     * 获取最后一次的记录
     *
     * @return 最后一次记录
     */
    HousingProvidentFundEntity getLastRecord();
}
