package cn.brody.financing.database.entity;

import cn.brody.financing.pojo.bo.FundTradeAddInfoBO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * FundTradeEntity
 *
 * @author chenyifu6
 * @since 2024/11/08 09:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("fund_trade")
public class FundTradeEntity {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 基金代码
     */
    private String fundCode;
    /**
     * 基金名称
     */
    private String fundName;
    /**
     * 交易日期
     */
    private String tradeDate;
    /**
     * 交易类型
     * <p>
     * 见{@link cn.brody.financing.enums.TradeTypeEnum}
     */
    private String tradeType;
    /**
     * 交易金额
     */
    private Double amount;
    /**
     * 交易份额
     */
    private Double share;
    /**
     * 交易所属方
     */
    private String belong;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    public FundTradeEntity(String fundCode, String belong, String fundName, FundTradeAddInfoBO fundTradeAddInfoBO) {
        this.fundCode = fundCode;
        this.fundName = fundName;
        this.belong = belong;
        this.tradeDate = fundTradeAddInfoBO.getTradeDate();
        this.tradeType = fundTradeAddInfoBO.getTradeType().name().toLowerCase();
        this.amount = fundTradeAddInfoBO.getAmount();
        this.share = fundTradeAddInfoBO.getShare();
    }
}