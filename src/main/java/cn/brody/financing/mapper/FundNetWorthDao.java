package cn.brody.financing.mapper;

import cn.brody.financing.pojo.entity.FundNetWorthEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDate;

/**
 * @author Brody
 * @date 2021/10/25
 **/
public interface FundNetWorthDao extends IService<FundNetWorthEntity> {

    /**
     * 获取净值
     * @param code 基金代码
     * @param confirmDate 确认日期
     * @return 净值 entity
     */
    FundNetWorthEntity getNetWorth(String code, LocalDate confirmDate);
}