package cn.brody.financing.pojo.bo;

import cn.brody.financing.pojo.base.BaseFund;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author chenyifu6
 * @date 2021/10/26
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AddFundBO extends BaseFund {
    private static final long serialVersionUID = 8926823947869559164L;

    /**
     * 运作费率
     */
    private Double operatingRate;
}
