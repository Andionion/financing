package cn.brody.financing.pojo.vo;

import cn.brody.financing.pojo.aktool.AktoolFundNetValueVO;
import cn.brody.financing.pojo.mairui.MairuiOpenFundLatestNetValueVO;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * FundNetValueVO 净值响应
 *
 * @author chenyifu6
 * @since 2024/11/05 14:39
 */
@Data
@NoArgsConstructor
public class FundNetValueVO {
    /**
     * 基金代码
     */
    private String fundCode;
    /**
     * 基金名称
     */
    private String fundName;
    /**
     * 净值日期
     */
    private String netValueDate;
    /**
     * 单位净值
     */
    private Double unitNetValue;
    /**
     * 日增长率
     */
    private Double dailyGrowthRate;
    /**
     * 累计净值
     */
    private Double accumulatedNetValue;
    /**
     * 申购状态
     */
    private String subscriptionStatus;
    /**
     * 赎回状态
     */
    private String redemptionStatus;

    public FundNetValueVO(AktoolFundNetValueVO fundNetValueVO, String fundCode, String fundName) {
        this.fundCode = fundCode;
        this.fundName = fundName;
        this.netValueDate = DatePattern.PURE_DATE_FORMAT
                .format(DateUtil.parse(fundNetValueVO.getNetValueDate(), DatePattern.UTC_SIMPLE_MS_PATTERN));
        this.unitNetValue = fundNetValueVO.getUnitNetValue();
        this.accumulatedNetValue = fundNetValueVO.getAccumulatedNetValue();
        this.dailyGrowthRate = fundNetValueVO.getDailyGrowthRate();
        this.subscriptionStatus = fundNetValueVO.getSubscriptionStatus();
        this.redemptionStatus = fundNetValueVO.getRedemptionStatus();
    }

    public FundNetValueVO(MairuiOpenFundLatestNetValueVO fundNetValueLatestVO) {
        this.fundCode = fundNetValueLatestVO.getDm();
        this.fundName = fundNetValueLatestVO.getMc();
        this.netValueDate = DatePattern.PURE_DATE_FORMAT
                .format(DateUtil.parse(fundNetValueLatestVO.getJzrq(), DatePattern.NORM_DATE_PATTERN));
        this.unitNetValue = fundNetValueLatestVO.getDwjz();
        this.accumulatedNetValue = fundNetValueLatestVO.getLjjz();
        this.dailyGrowthRate = fundNetValueLatestVO.getZzl();
        this.subscriptionStatus = fundNetValueLatestVO.getSgzt();
    }
}
