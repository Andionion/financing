package cn.brody.financing.database.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * FundNetValueEntity
 *
 * @author chenyifu6
 * @since 2024/11/05 16:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("trade_date_hist")
public class TradeDateHistEntity {

    /**
     * 主键，自增
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 交易日
     */
    @TableField("trade_date")
    private String tradeDate;

    public TradeDateHistEntity(String tradeDate) {
        this.tradeDate = tradeDate;
    }
}