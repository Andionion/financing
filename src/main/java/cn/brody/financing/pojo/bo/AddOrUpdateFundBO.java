package cn.brody.financing.pojo.bo;

import cn.brody.financing.pojo.base.BaseFund;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author chenyifu6
 * @date 2021/10/26
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AddOrUpdateFundBO extends BaseFund {
    private static final long serialVersionUID = 5179055650706814481L;

    public AddOrUpdateFundBO(String code) {
        super(code);
    }
}
