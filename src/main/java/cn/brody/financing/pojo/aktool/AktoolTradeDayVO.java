package cn.brody.financing.pojo.aktool;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * AktoolTradeDayVO
 *
 * @author BrodyChen
 * @since 2025/02/28 14:42
 */
@Data
public class AktoolTradeDayVO {

    /**
     * 交易日，格式yyyy-MM-dd'T'HH:mm:ss.SSS
     */
    @JSONField(name = "trade_date")
    private String tradeDate;
}
