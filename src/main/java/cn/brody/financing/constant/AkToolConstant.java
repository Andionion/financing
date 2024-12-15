package cn.brody.financing.constant;

import cn.brody.financing.utils.UrlBuilderUtils;

/**
 * AkToolConstant
 *
 * @author chenyifu6
 * @since 2024/11/05 15:43
 */
public class AkToolConstant {

    /**
     * 服务地址
     */
    private static final String SERVER_ADDRESS = "http://47.97.28.176:8080";
    /**
     * 全部日期的ETF基金净值URI
     */
    private static final String URI_ETF_FUND_NET_VALUE_FULL = "/api/public/fund_etf_fund_info_em?fund=%s";
    /**
     * 最新日期的ETF基金净值URI
     */
    private static final String URI_ETF_FUND_NET_VALUE_DATE = "/api/public/fund_etf_fund_info_em?fund=%s&start_date=%s&end_date=%s";
    /**
     * 获取开放型基金历史净值
     */
    private static final String URI_OPEN_FUND_NET_VALUE_FULL = "/api/public/fund_open_fund_info_em?symbol=%s&indicator=%s";
    /**
     * 交易日期历史记录
     */
    private static final String URI_TRADE_DATE_HIST_SINA = "/api/public/tool_trade_date_hist_sina";


    /**
     * 获取基金全部日期净值的URL。
     *
     * @param fundCode 基金代码。
     * @return 返回构建好的基金全部日期净值的URL。
     */
    public static String getEtfFundNetValueFullUrl(String fundCode) {
        return UrlBuilderUtils.
                fromBaseUrl(String.format(AkToolConstant.SERVER_ADDRESS + AkToolConstant.URI_ETF_FUND_NET_VALUE_FULL, fundCode))
                .build();
    }

    /**
     * 获取基金最新净值的URL。
     *
     * @param fundCode  基金代码。
     * @param tradeDate 交易日期。
     * @return 返回构建好的基金最新净值的URL。
     */
    public static String getEtfFundNetValueDateUrl(String fundCode, String tradeDate) {
        return UrlBuilderUtils
                .fromBaseUrl(String.format(SERVER_ADDRESS + URI_ETF_FUND_NET_VALUE_DATE, fundCode, tradeDate, tradeDate))
                .build();
    }

    /**
     * 获取开放型基金全部净值 URL
     *
     * @param fundCode
     * @return
     */
    public static String getOpenFundNetValueFullUrl(String fundCode) {
        return UrlBuilderUtils
                .fromBaseUrl(String.format(SERVER_ADDRESS + URI_OPEN_FUND_NET_VALUE_FULL, fundCode, "单位净值走势"))
                .build(Boolean.TRUE);
    }

    /**
     * 获取新浪交易日期历史数据的URL。
     *
     * @return 返回构建好的新浪交易日期历史数据URL。
     */
    public static String getTradeDateHistSinaUrl() {
        return UrlBuilderUtils.fromBaseUrl(SERVER_ADDRESS + URI_TRADE_DATE_HIST_SINA).build();
    }

}
