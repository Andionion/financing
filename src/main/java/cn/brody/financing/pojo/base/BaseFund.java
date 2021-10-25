package cn.brody.financing.pojo.base;

import lombok.Data;

import java.io.Serializable;

/**
 * @author chenyifu6
 * @date 2021/10/26
 */
@Data
public class BaseFund implements Serializable {
    private static final long serialVersionUID = -5964142438705394161L;

    /**
     * 基金代码
     */
    private String code;
}
