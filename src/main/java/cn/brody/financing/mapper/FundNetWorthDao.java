package cn.brody.financing.mapper;

import cn.brody.financing.pojo.entity.FundNetWorthEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

/**
 * @author Brody
 * @date 2021/10/25
 **/
public interface FundNetWorthDao extends IService<FundNetWorthEntity> {

    /**
     * 获取净值
     *
     * @param code        基金代码
     * @param confirmDate 确认日期
     * @return 净值 entity
     */
    FundNetWorthEntity getNetWorth(String code, LocalDate confirmDate);

    /**
     * 获取在确认日期范围内的基金净值
     *
     * @param code            基金代码
     * @param confirmDateList 确认日期 list
     * @return
     */
    List<FundNetWorthEntity> listNetWorth(String code, Collection<LocalDate> confirmDateList);

    /**
     * 通过基金代码获取净值记录
     *
     * @param code
     * @return
     */
    List<FundNetWorthEntity> listNetWorthList(String code);

    /**
     * 判断当前日期的净值知否存在
     *
     * @param date 当前日期
     * @return true-存在，false-不存在
     */
    Boolean isExist(LocalDate date);

    /**
     * 通过基金代码删除净值记录
     *
     * @param code
     * @return
     */
    Boolean removeNetWorth(String code);

    /**
     * 获取最新净值
     *
     * @param code
     * @return
     */
    FundNetWorthEntity getLatestNetWorth(String code);
}