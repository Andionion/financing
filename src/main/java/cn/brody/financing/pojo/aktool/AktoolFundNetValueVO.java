package cn.brody.financing.pojo.aktool;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * FundNetValueVO 净值响应
 * <p>
 * [
 * {
 * "净值日期": "2024-10-31T00:00:00.000",
 * "单位净值": 1.1309,
 * "累计净值": 1.2169,
 * "日增长率": 0.01,
 * "申购状态": "开放申购",
 * "赎回状态": "开放赎回"
 * }
 * ]
 *
 * @author chenyifu6
 * @since 2024/11/05 14:39
 */
@Data
public class AktoolFundNetValueVO {
    /**
     * 净值日期
     */
    @JSONField(name = "净值日期")
    private String netValueDate;
    /**
     * 单位净值
     */
    @JSONField(name = "单位净值")
    private Double unitNetValue;
    /**
     * 日增长率
     */
    @JSONField(name = "日增长率")
    private Double dailyGrowthRate;
    /**
     * 累计净值
     */
    @JSONField(name = "累计净值")
    private Double accumulatedNetValue;
    /**
     * 申购状态
     */
    @JSONField(name = "申购状态")
    private String subscriptionStatus;
    /**
     * 赎回状态
     */
    @JSONField(name = "赎回状态")
    private String redemptionStatus;
}
