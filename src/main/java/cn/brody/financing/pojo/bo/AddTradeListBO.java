package cn.brody.financing.pojo.bo;

import cn.brody.financing.pojo.base.BaseFund;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author brody
 * @date 2022/01/10
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class AddTradeListBO extends BaseFund {

    private List<AddTradeBO> addTradeBOList;


    public AddTradeListBO() {
        addTradeBOList = new ArrayList<>();
    }
}
