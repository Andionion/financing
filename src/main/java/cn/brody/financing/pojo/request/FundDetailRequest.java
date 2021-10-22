package cn.brody.financing.pojo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author Brody
 * @date 2021/10/21
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FundDetailRequest implements Serializable {
    private static final long serialVersionUID = -3088635899042313403L;

    /**
     * 基金代码 (必填，多个用逗号隔开)
     */
    private String code;
    /**
     * 开始时间，标准时间格式 yyyy-MM-dd
     */
    private LocalDate startDate;
    /**
     * 截至时间，标准时间格式 yyyy-MM-dd
     */
    private LocalDate endDate;
}
