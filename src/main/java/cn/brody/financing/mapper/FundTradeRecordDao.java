package cn.brody.financing.mapper;

import cn.brody.financing.pojo.entity.FundTradeRecordEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

/**
 * @author Brody
 * @date 2021/10/25
 **/
public interface FundTradeRecordDao extends IService<FundTradeRecordEntity> {
    /**
     * 计算某个基金是否在某个日期有过交易记录
     *
     * @param code
     * @param date
     * @return
     */
    Boolean countDateExist(String code, LocalDate date);

    /**
     * 获取所有的日期
     *
     * @param code
     * @param localDates
     * @return
     */
    List<LocalDate> listAlreadyExistRecord(String code, Collection<LocalDate> localDates);

    /**
     * 通过基金代码获取该基金所有的记录
     *
     * @param code
     * @return
     */
    List<FundTradeRecordEntity> listByFundCode(String code);

    /**
     * 查找某个基金在某个日期前的所有交易记录
     *
     * @param code
     * @param date
     * @return
     */
    List<FundTradeRecordEntity> listBeforeDate(String code, LocalDate date);

    /**
     * 判断基金在特定日期下分红记录是否存在
     *
     * @param code
     * @param date
     * @return
     */
    boolean countDividendExist(String code, LocalDate date);
}