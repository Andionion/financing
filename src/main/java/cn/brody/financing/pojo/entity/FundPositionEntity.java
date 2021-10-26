package cn.brody.financing.pojo.entity;

import cn.brody.financing.pojo.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * 持仓表
 *
 * @author brodyChen
 * @date 2021/10/25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "fund_position")
@EqualsAndHashCode(callSuper = true)
public class FundPositionEntity extends BaseEntity implements Serializable {

    /**
     * 现值
     */
    @TableField(value = "present_value")
    private Double presentValue;

    /**
     * 已赎回
     */
    @TableField(value = "redeemed")
    private Double redeemed;

    /**
     * 总投入
     */
    @TableField(value = "investment")
    private Double investment;

    /**
     * 总收益
     */
    @TableField(value = "revenue")
    private Double revenue;

    /**
     * 持有收益
     */
    @TableField(value = "holding_income")
    private Double holdingIncome;

    /**
     * 年化收益率
     */
    @TableField(value = "annualized_rate_of_return")
    private Double annualizedRateOfReturn;

    private static final long serialVersionUID = 1L;

    public static final String COL_PRESENT_VALUE = "present_value";

    public static final String COL_REDEEMED = "redeemed";

    public static final String COL_INVESTMENT = "investment";

    public static final String COL_REVENUE = "revenue";

    public static final String COL_HOLDING_INCOME = "holding_income";

    public static final String COL_ANNUALIZED_RATE_OF_RETURN = "annualized_rate_of_return";
}