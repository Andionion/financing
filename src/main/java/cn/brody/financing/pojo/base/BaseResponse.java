package cn.brody.financing.pojo.base;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Brody
 * @date 2021/10/21
 **/
@Data
public class BaseResponse<T> implements Serializable {
    private static final long serialVersionUID = 6939293086084693903L;

    public static final int SUCCESS = 200;

    private Integer code;

    private String message;

    private T data;

    public BaseResponse() {
        code = SUCCESS;
        message = "成功";
    }
}
