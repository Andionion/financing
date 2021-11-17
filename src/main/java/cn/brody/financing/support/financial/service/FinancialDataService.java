package cn.brody.financing.support.financial.service;

import cn.brody.financing.support.financial.response.FundDetailResponse;

import java.time.LocalDate;

/**
 * 外部金融接口
 *
 * @author Brody
 * @date 2021/10/21
 **/
public interface FinancialDataService {

    /**
     * 获取带有所有历史净值的基金详情
     *
     * @param code 基金代码
     * @return 包含所有历史净值的基金详情
     */
    FundDetailResponse getFundDetail(String code);

    /**
     * 获取带有指定日期净值的基金详情
     *
     * @param code 基金代码
     * @param date 指定日期
     * @return 带有指定日期净值的基金详情
     */
    FundDetailResponse getFundDetail(String code, LocalDate date);

    /**
     * 获取带有指定日期之间的基金详情
     *
     * @param code      基金代码
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 指定日期之间历史净值的基金详情
     */
    FundDetailResponse getFundDetail(String code, LocalDate startDate, LocalDate endDate);
}
