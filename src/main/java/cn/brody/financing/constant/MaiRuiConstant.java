package cn.brody.financing.constant;

import cn.brody.financing.utils.UrlBuilderUtils;

/**
 * <a href="https://www.mairui.club/jjdata.html">基金数据 API 文档</a>
 *
 * @author chenyifu6
 * @since 2024/11/08 11:06
 */
public class MaiRuiConstant {

    /**
     * 服务地址
     */
    private static final String SERVER_ADDRESS = "http://api.mairui.club/jj";
    /**
     * licence
     */
    private static final String LICENCE = "A84FD9E6-3C71-476D-BE5C-13DE08D162AD";

    /**
     * 基金概况基金净值URI
     */
    private static final String URI_FUND_OVERVIEW = "/jjgk/%s/%s";
    /**
     * 获取开放型基金净值
     */
    private static final String URI_OPEN_FUND_NET_VALUE_LATEST = "/hqzksjz/%s/%s";
    /**
     * 基金历史净值
     */
    private static final String URI_FUND_NET_VALUE_HISTORY = "/lsjz/%s/%s";


    /**
     * 获取基金概览的URL。
     *
     * @param fundCode 需要查询的基金代码。
     * @return 返回构建好的基金概览URL，该URL包含了基金代码和许可证信息。
     */
    public static String getFundOverviewUrl(String fundCode) {
        return UrlBuilderUtils.
                fromBaseUrl(String.format(SERVER_ADDRESS + URI_FUND_OVERVIEW, fundCode, LICENCE))
                .build();
    }

    /**
     * 获取开放型基金净值URL。
     *
     * @param fundCode 需要查询的基金代码。
     * @return 返回构建好的基金概览URL，该URL包含了基金代码和许可证信息。
     */
    public static String getOpenFundNetValueLatestUrl(String fundCode) {
        return UrlBuilderUtils.
                fromBaseUrl(String.format(SERVER_ADDRESS + URI_OPEN_FUND_NET_VALUE_LATEST, fundCode, LICENCE))
                .build();
    }

    /**
     * 获取开放型基金净值URL。
     *
     * @param fundCode 需要查询的基金代码。
     * @return 返回构建好的基金概览URL，该URL包含了基金代码和许可证信息。
     */
    public static String getUriFundNetValueHistoryUrl(String fundCode) {
        return UrlBuilderUtils.
                fromBaseUrl(String.format(SERVER_ADDRESS + URI_FUND_NET_VALUE_HISTORY, fundCode, LICENCE))
                .build();
    }
}
