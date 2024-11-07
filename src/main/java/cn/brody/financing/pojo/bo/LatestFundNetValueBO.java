package cn.brody.financing.pojo.bo;

import lombok.Data;

import java.util.List;

/**
 * 获取最新基金净值请求参数
 *
 * @author BrodyChen
 * @since 2024/11/07 13:55
 */
@Data
public class LatestFundNetValueBO {

    /**
     * 基金代码列表
     */
    private List<String> fundCodes;
}
