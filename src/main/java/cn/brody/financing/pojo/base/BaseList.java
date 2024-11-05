package cn.brody.financing.pojo.base;

import lombok.Data;

import java.util.List;

/**
 * BaseList
 *
 * @author chenyifu6
 * @since 2024/11/05 16:17
 */
@Data
public class BaseList<T> {

    /**
     * 列表数据
     */
    private List<T> list;
    /**
     * 总数
     */
    private Integer total;
}
