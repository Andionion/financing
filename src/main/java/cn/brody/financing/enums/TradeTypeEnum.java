package cn.brody.financing.enums;

/**
 * 交易类型
 *
 * @author BrodyChen
 * @since 2025/02/13 16:17
 */
public enum TradeTypeEnum {

    /**
     * 申购
     */
    PURCHASE,
    /**
     * 赎回
     */
    REDEEM,
    ;

    /**
     * 根据字符串获取对应的枚举值（大小写不敏感）
     *
     * @param tradeType
     * @return
     */
    public static TradeTypeEnum forValue(String tradeType) {
        return TradeTypeEnum.valueOf(tradeType.toUpperCase());
    }
}
