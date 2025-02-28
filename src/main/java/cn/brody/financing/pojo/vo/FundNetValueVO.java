package cn.brody.financing.pojo.vo;

import cn.brody.financing.pojo.aktool.AktoolFundNetValueVO;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
    private LocalDate netValueDate;
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
        this.netValueDate = LocalDateTimeUtil.parse(fundNetValueVO.getNetValueDate()
                , DateTimeFormatter.ofPattern(DatePattern.UTC_SIMPLE_MS_PATTERN)).toLocalDate();
        this.unitNetValue = fundNetValueVO.getUnitNetValue();
        this.accumulatedNetValue = fundNetValueVO.getAccumulatedNetValue();
        this.dailyGrowthRate = fundNetValueVO.getDailyGrowthRate();
        this.subscriptionStatus = fundNetValueVO.getSubscriptionStatus();
        this.redemptionStatus = fundNetValueVO.getRedemptionStatus();
    }

}
