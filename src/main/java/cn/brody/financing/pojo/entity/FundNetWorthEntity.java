package cn.brody.financing.pojo.entity;

import cn.brody.financing.pojo.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;


/**
 * 基金当前净值表
 *
 * @author brodyChen
 * @date 2021/10/25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "fund_net_worth")
@EqualsAndHashCode(callSuper = true)
public class FundNetWorthEntity extends BaseEntity implements Serializable {

    /**
     * 日期
     */
    @TableField(value = "`date`")
    private LocalDate date;

    /**
     * 单位净值
     */
    @TableField(value = "net_worth")
    private Double netWorth;

    /**
     * 累计净值
     */
    @TableField(value = "total_worth")
    private Double totalWorth;

    /**
     * 分红
     */
    @TableField(value = "dividends")
    private Double dividends;

    public FundNetWorthEntity(String code, LocalDate date, Double netWorth, Double totalWorth, Double dividends) {
        super(code);
        this.date = date;
        this.netWorth = netWorth;
        this.totalWorth = totalWorth;
        this.dividends = dividends;
    }

    private static final long serialVersionUID = 1L;

    public static final String COL_DATE = "date";

    public static final String COL_NET_WORTH = "net_worth";

    public static final String COL_TOTAL_WORTH = "total_worth";

    public static final String COL_DIVIDENDS = "dividends";
}