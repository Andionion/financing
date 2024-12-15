package cn.brody.financing.pojo.mairui;

import lombok.Data;

/**
 * MairuiFundOverviewVO
 *
 * @author 94743
 * @since 2024/12/14 16:19
 */
@Data
public class MairuiFundOverviewVO {

    /**
     * 基金代码
     */
    private String dm;
    /**
     * 基金全称
     */
    private String qc;
    /**
     * 基金简称
     */
    private String jc;
    /**
     * 基金类型
     */
    private String lx;
    /**
     * 发行日期
     */
    private String pub;
    /**
     * 成立日期 / 规模
     */
    private String gm;
    /**
     * 资产规模
     */
    private String zcgm;
    /**
     * 份额规模
     */
    private String fegm;
    /**
     * 基金管理人
     */
    private String glr;
    /**
     * 基金托管人
     */
    private String tgr;
    /**
     * 基金经理人
     */
    private String jlr;
    /**
     * 成立以来分红
     */
    private String ljfh;
    /**
     * 管理费率
     */
    private String glfv;
    /**
     * 托管费率
     */
    private String tgfl;
    /**
     * 销售服务费率
     */
    private String xsfl;
    /**
     * 最高认购费率
     */
    private String rgfl;
    /**
     * 最高申购费率
     */
    private String sgfl;
    /**
     * 最高赎回费率
     */
    private String shfl;
    /**
     * 业绩比较基准
     */
    private String bjjz;
    /**
     * 跟踪标的
     */
    private String gzbd;
    /**
     * 投资目标
     */
    private String tzmb;
    /**
     * 投资理念
     */
    private String tzln;
    /**
     * 投资范围
     */
    private String tzfw;
    /**
     * 投资策略
     */
    private String tzcl;
    /**
     * 分红政策
     */
    private String fhzc;
    /**
     * 风险收益特征
     */
    private String fxsytz;
}
