package cn.brody.financing.pojo.bo;

import cn.brody.financing.pojo.base.BaseFund;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 新增交易记录 BO
 * @author brody
 * @date 2021/10/30
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AddTransactionBO extends BaseFund implements Serializable {
    private static final long serialVersionUID = -1795618645361998657L;

    /**
     * 确认日期
     */
    private LocalDate confirmDate;

    /**
     * 交易类型，1 - 申购，2 - 赎回，3 - 分红
     */
    private Integer type;
}
