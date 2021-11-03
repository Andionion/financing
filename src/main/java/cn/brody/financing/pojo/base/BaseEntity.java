package cn.brody.financing.pojo.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 父类表，包含id和基金代码
 *
 * @author Brody
 * @date 2021/10/25
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = -3367210749128533512L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 基金代码
     */
    @TableField(value = "code")
    private String code;

    public static final String COL_ID = "id";

    public static final String COL_CODE = "code";

    public BaseEntity(String code) {
        this.code = code;
    }
}
