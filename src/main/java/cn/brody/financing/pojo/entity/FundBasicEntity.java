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
 * 基金基本表
 *
 * @author brodyChen
 * @date 2021/10/25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "fund_basic")
@EqualsAndHashCode(callSuper = true)
public class FundBasicEntity extends BaseEntity implements Serializable {
    
    /**
     * 基金名称
     */
    @TableField(value = "`name`")
    private String name;

    /**
     * 购买费率
     */
    @TableField(value = "buy_rate")
    private Double buyRate;

    /**
     * 运作费率
     */
    @TableField(value = "operating_rate")
    private Double operatingRate;

    /**
     * 基金经理
     */
    @TableField(value = "manager")
    private String manager;

    /**
     * 基金类型
     */
    @TableField(value = "`type`")
    private String type;

    /**
     * 基金规模
     */
    @TableField(value = "fund_scale")
    private String fundScale;

    private static final long serialVersionUID = 1L;

    public static final String COL_NAME = "name";

    public static final String COL_BUY_RATE = "buy_rate";

    public static final String COL_OPERATING_RATE = "operating_rate";

    public static final String COL_MANAGER = "manager";

    public static final String COL_TYPE = "type";

    public static final String COL_FUND_SCALE = "fund_scale";
}