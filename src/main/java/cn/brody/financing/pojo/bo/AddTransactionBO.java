package cn.brody.financing.pojo.bo;

import cn.brody.financing.pojo.base.BaseFund;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author brody
 * @date 2021/10/30
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AddTransactionBO extends BaseFund implements Serializable {
    private static final long serialVersionUID = -1795618645361998657L;

    /**
     *
     */
    private LocalDate date;
}
