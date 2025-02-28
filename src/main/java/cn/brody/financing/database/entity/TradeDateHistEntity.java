package cn.brody.financing.database.entity;

import cn.brody.financing.pojo.aktool.AktoolTradeDayVO;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
     * 主键
     */
    @TableId
    private Integer id;
    /**
     * 交易日
     */
    private LocalDate tradeDay;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    public TradeDateHistEntity(AktoolTradeDayVO tradeDayVO) {
        this.tradeDay = LocalDateTimeUtil
                .parse(tradeDayVO.getTradeDate(), DateTimeFormatter.ofPattern(DatePattern.UTC_SIMPLE_MS_PATTERN))
                .toLocalDate();
    }
}