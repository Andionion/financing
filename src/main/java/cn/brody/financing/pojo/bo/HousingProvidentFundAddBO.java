package cn.brody.financing.pojo.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * HousingProvidentFundAddBO
 *
 * @author BrodyChen
 * @since 2025/03/10 14:15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HousingProvidentFundAddBO {

    /**
     * 操作日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate operationDate;
    /**
     * 操作类型，见{@link cn.brody.financing.enums.HousingFundOpTypeEnum}
     */
    private String operationType;
    /**
     * 金额
     */
    private Double amount;

}
