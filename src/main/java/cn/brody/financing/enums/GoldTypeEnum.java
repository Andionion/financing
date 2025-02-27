package cn.brody.financing.enums;

/**
 * GoldTypeEnum
 *
 * @author BrodyChen
 * @since 2025/02/23 21:32
 */
public enum GoldTypeEnum {
    /**
     * 纸面金（对应 "paper"）
     */
    PAPER,
    /**
     * 实体金（对应 "physical"）
     */
    PHYSICAL,
    ;

    /**
     * 根据字符串获取对应的枚举值（大小写不敏感）
     *
     * @param goldType
     * @return
     */
    public static GoldTypeEnum forValue(String goldType) {
        return GoldTypeEnum.valueOf(goldType.toUpperCase());
    }
}
