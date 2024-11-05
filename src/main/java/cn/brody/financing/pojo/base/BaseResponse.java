package cn.brody.financing.pojo.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * BaseResponse
 *
 * @author chenyifu6
 * @since 2024/09/11 15:57
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse<T> {
    /**
     * 状态码，默认为0表示成功
     */
    private String code = "0";
    /**
     * 消息，默认为success
     */
    private String msg = "success";
    /**
     * 数据
     */
    private T data;

    public BaseResponse(T data) {
        this.data = data;
    }
}
