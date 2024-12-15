package cn.brody.financing.database.entity;

import cn.brody.financing.pojo.vo.FundNetValueVO;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * FundNetValueEntity
 *
 * @author chenyifu6
 * @since 2024/11/05 16:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("fund_net_value")
public class FundNetValueEntity {

    /**
     * 主键，自增
     */
    @TableId(type = IdType.AUTO)
    private Long id;
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
     * 累计净值
     */
    private Double accumulatedNetValue;
    /**
     * 日增长率
     */
    private Double dailyGrowthRate;
    /**
     * 申购状态
     */
    private String subscriptionStatus;
    /**
     * 赎回状态
     */
    private String redemptionStatus;

    public FundNetValueEntity(FundNetValueVO fundNetValueVO) {
        this.fundCode = fundNetValueVO.getFundCode();
        this.fundName = fundNetValueVO.getFundName();
        this.netValueDate = fundNetValueVO.getNetValueDate();
        this.unitNetValue = fundNetValueVO.getUnitNetValue();
        this.accumulatedNetValue = fundNetValueVO.getAccumulatedNetValue();
        this.dailyGrowthRate = fundNetValueVO.getDailyGrowthRate();
        this.subscriptionStatus = fundNetValueVO.getSubscriptionStatus();
        this.redemptionStatus = fundNetValueVO.getRedemptionStatus();
    }
}