package cn.brody.financing.pojo.vo;

import cn.brody.financing.pojo.base.BaseFund;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Brody
 * @date 2021/12/19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AnnualizedRateVO extends BaseFund implements Serializable {

    /**
     * 基金名称
     */
    private String name;
    /**
     * 年化利率
     */
    private String annualizedRate;

    public AnnualizedRateVO(String code, String annualizedRate, String name) {
        super(code);
        this.annualizedRate = annualizedRate;
        this.name = name;
    }
}




