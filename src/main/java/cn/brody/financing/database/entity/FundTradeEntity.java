package cn.brody.financing.database.entity;

import cn.brody.financing.pojo.bo.FundTradeInfoBO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public FundTradeEntity(String fundCode, String belong, String fundName, FundTradeInfoBO fundTradeInfoBO) {
        this.fundCode = fundCode;
        this.fundName = fundName;
        this.belong = belong;
        this.tradeDate = fundTradeInfoBO.getTradeDate();
        this.tradeType = fundTradeInfoBO.getTradeType().name().toLowerCase();
        this.amount = fundTradeInfoBO.getAmount();
        this.share = fundTradeInfoBO.getShare();
    }
}