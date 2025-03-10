package cn.brody.financing.enums;

/**
 * HousingFundOpTypeEnum
 *
 * @author BrodyChen
 * @since 2025/03/10 11:07
 */
public enum HousingFundOpTypeEnum {
    /**
     * 存款
     */
    DEPOSIT,
    /**
     * 提款
     */
    WITHDRAWAL,
    /**
     * 结息
     */
    INTEREST,
    ;

    /**
     * 根据字符串获取对应的枚举值（大小写不敏感）
     *
     * @param operationType
     * @return
     */
    public static HousingFundOpTypeEnum forValue(String operationType) {
        return HousingFundOpTypeEnum.valueOf(operationType.toUpperCase());
    }
}
