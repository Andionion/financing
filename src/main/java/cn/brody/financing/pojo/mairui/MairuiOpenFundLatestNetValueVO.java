package cn.brody.financing.pojo.mairui;

import lombok.Data;

/**
 * MairuiFundOverviewVO
 *
 * @author 94743
 * @since 2024/12/14 16:19
 */
@Data
public class MairuiOpenFundLatestNetValueVO {
    /**
     * 基金代码，如：000001（华夏成长混合）
     */
    private String dm;
    /**
     * 基金名称，如：华夏成长混合
     */
    private String mc;
    /**
     * 单位净值
     */
    private Double dwjz;
    /**
     * 累计净值
     */
    private Double ljjz;
    /**
     * 前一日净值
     */
    private Double qyrjz;
    /**
     * 涨跌额
     */
    private Double zde;
    /**
     * 增长率（%）
     */
    private Double zzl;
    /**
     * 申购状态
     */
    private String sgzt;
    /**
     * 净值日期
     */
    private String jzrq;
    /**
     * 基金管理人
     */
    private String glr;
    /**
     * 基金类型
     */
    private String jjlx;
}
