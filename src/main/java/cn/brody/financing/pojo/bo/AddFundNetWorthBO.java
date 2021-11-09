package cn.brody.financing.pojo.bo;

import cn.brody.financing.pojo.base.BaseFund;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * j
 *
 * @author Brody
 * @date 2021/11/08
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AddFundNetWorthBO extends BaseFund implements Serializable {
    private static final long serialVersionUID = 1320389810432470589L;

    /**
     * 开始时间
     */
    private LocalDate startDate;

    /**
     * 结束时间
     */
    private LocalDate endDate;

    public AddFundNetWorthBO(String code, LocalDate startDate, LocalDate endDate) {
        super(code);
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
