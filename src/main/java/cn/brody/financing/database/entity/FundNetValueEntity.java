package cn.brody.financing.database.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
    private Integer id;
    /**
     * 基金代码
     */
    @TableField("fund_code")
    private String fundCode;
    /**
     * 净值日期
     */
    @TableField("net_value_date")
    private String netValueDate;
    /**
     * 单位净值
     */
    @TableField("unit_net_value")
    private Double unitNetValue;
    /**
     * 累计净值
     */
    @TableField("accumulated_net_value")
    private Double accumulatedNetValue;
    /**
     * 日增长率
     */
    @TableField("daily_growth_rate")
    private Double dailyGrowthRate;
    /**
     * 申购状态
     */
    @TableField("subscription_status")
    private String subscriptionStatus;
    /**
     * 赎回状态
     */
    @TableField("redemption_status")
    private String redemptionStatus;
}