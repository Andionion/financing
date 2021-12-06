package cn.brody.financing.pojo.entity;

import cn.brody.financing.pojo.base.BaseEntity;
import cn.brody.financing.pojo.bo.AddTradeBO;
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
@TableName(value = "fund_trade_record")
@EqualsAndHashCode(callSuper = true)
public class FundTradeRecordEntity extends BaseEntity implements Serializable {

    /**
     * 申购金额
     */
    @TableField(value = "amount")
    private Double amount;

    /**
     * 交易类型，1 - 申购，2 - 赎回，3 - 分红
     */
    private Integer type;

    /**
     * 确认份额
     */
    @TableField(value = "confirm_share")
    private Double confirmShare;

    /**
     * 确认日期
     */
    @TableField(value = "confirm_date")
    private LocalDate confirmDate;

    private static final long serialVersionUID = 1L;


    public FundTradeRecordEntity(AddTradeBO addTradeBO) {
        super(addTradeBO.getCode());
        amount = addTradeBO.getAmount();
        type = addTradeBO.getType();
        confirmDate = addTradeBO.getConfirmDate();
    }

    public FundTradeRecordEntity(String code, Double amount, Integer type, Double confirmShare, LocalDate confirmDate) {
        super(code);
        this.amount = amount;
        this.type = type;
        this.confirmShare = confirmShare;
        this.confirmDate = confirmDate;
    }
}