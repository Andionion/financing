package cn.brody.financing.pojo.bo;

import cn.brody.financing.pojo.base.BaseFund;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author brody
 * @date 2021/10/30
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DelFundBO extends BaseFund implements Serializable {
    private static final long serialVersionUID = 7831881900434740436L;
}
