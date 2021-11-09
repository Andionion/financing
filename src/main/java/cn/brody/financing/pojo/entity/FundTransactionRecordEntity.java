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
 * 交易记录表
 *
 * @author brodyChen
 * @date 2021/10/25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "fund_transaction_record")
@EqualsAndHashCode(callSuper = true)
public class FundTransactionRecordEntity extends BaseEntity implements Serializable {

    /**
     * 申购金额
     */
    @TableField(value = "subscription_amount")
    private Double subscriptionAmount;

    /**
     * 确认份额
     */
    @TableField(value = "confirm_share")
    private Double confirmShare;

    /**
     * 确认日期
     */
    @TableField(value = "'confirm_date'")
    private LocalDate confirmDate;

    /**
     * 申购日期
     */
    @TableField(value = "`subscription_date`")
    private LocalDate subscriptionDate;

    private static final long serialVersionUID = 1L;

    public static final String COL_SUBSCRIPTION_AMOUNT = "subscription_amount";

    public static final String COL_CONFIRM_SHARE = "confirm_share";

    public static final String COL_DATE = "date";
}