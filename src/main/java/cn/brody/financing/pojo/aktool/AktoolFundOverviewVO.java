package cn.brody.financing.pojo.aktool;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * AktoolFundOverviewVO
 *
 * @author 94743
 * @since 2025/02/02 23:06
 */
@Data
public class AktoolFundOverviewVO {

    /**
     * 基金代码
     */
    @JSONField(name = "基金代码")
    private String fundCode;

    /**
     * 拼音缩写
     */
    @JSONField(name = "拼音缩写")
    private String pinyinAbbreviation;

    /**
     * 基金简称
     */
    @JSONField(name = "基金简称")
    private String fundShortName;

    /**
     * 基金类型
     */
    @JSONField(name = "基金类型")
    private String fundType;

    /**
     * 拼音全称
     */
    @JSONField(name = "拼音全称")
    private String pinyinFullName;
}
