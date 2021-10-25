package cn.brody.financing.support.financial.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Brody
 * @date 2021/10/21
 **/
@Data
public class FundDetailResponse implements Serializable {
    private static final long serialVersionUID = 8457140321657644917L;

    /**
     * 起购额度
     */
    private String buyMin;
    /**
     * 当前买入费率，单位：百分比
     */
    private Double buyRate;
    /**
     * 原始买入费率，单位：百分比
     */
    private Double buySourceRate;
    /**
     * 基金代码
     */
    private String code;
    /**
     * 单位净值日涨幅，单位为百分比
     */
    private Double dayGrowth;
    /**
     * 当前基金单位净值估算日涨幅，单位为百分比
     */
    private Double expectGrowth;
    /**
     * 当前基金单位净值估算
     */
    private Double expectWorth;
    /**
     * 净值估算更新日期，，日期格式为 yy-MM-dd HH:mm.2019-06-27 15:00 代表当天下午 3 点
     */
    private String expectWorthDate;
    /**
     * 基金规模，日期为最后一次规模变动的日期
     */
    private String fundScale;
    /**
     * 单位净值月涨幅，单位为百分比
     */
    private Double lastMonthGrowth;
    /**
     * 单位净值六月涨幅，单位为百分比
     */
    private Double lastSixMonthsGrowth;
    /**
     * 单位净值三月涨幅，单位为百分比
     */
    private Double lastThreeMonthsGrowth;
    /**
     * 单位净值周涨幅，单位为百分比
     */
    private Double lastWeekGrowth;
    /**
     * 单位净值年涨幅，单位为百分比
     */
    private Double lastYearGrowth;
    /**
     * 基金经理
     */
    private String manager;
    /**
     * 基金名称
     */
    private String name;
    /**
     * 当前基金单位净值
     */
    private Double netWorth;
    /**
     * 历史单位净值信息 ["2001-12-18" , 1 , 0 , ""] 依次表示：日期；单位净值；净值涨幅；每份分红.
     */
    private List<List<String>> netWorthData;
    /**
     * 净值估算更新日期，，日期格式为 yy-MM-dd HH:mm.2019-06-27 15:00 代表当天下午 3 点
     */
    private String netWorthDate;
    /**
     * 历史累计净值信息 ["2001-12-18" , 1 ] 依次表示：日期；累计净值
     */
    private List<List<String>> totalNetWorthData;
    /**
     * 当前基金累计净值
     */
    private Double totalWorth;
    /**
     * 基金类型
     */
    private String type;

}
