package cn.brody.financing.utils;

import cn.brody.financing.constant.AkToolConstant;
import cn.brody.financing.pojo.aktool.AktoolFundNetValueVO;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * AktoolUtils
 *
 * @author 94743
 * @since 2024/12/14 16:04
 */
@Slf4j
public class AktoolUtils {

    /**
     * 获取ETF基金净值
     *
     * @param fundCode
     * @return
     */
    public static List<AktoolFundNetValueVO> getEtfFundNetValueFull(String fundCode) {
        return getEtfFundNetValueDate(fundCode, null);
    }

    /**
     * 获取指定日期的ETF基金净值
     *
     * @param fundCode
     * @param latestTradeDate
     * @return
     */
    public static List<AktoolFundNetValueVO> getEtfFundNetValueDate(String fundCode, String latestTradeDate) {
        String url;
        if (StrUtil.isNotBlank(latestTradeDate)) {
            url = AkToolConstant.getEtfFundNetValueDateUrl(fundCode, latestTradeDate);
        } else {
            url = AkToolConstant.getEtfFundNetValueFullUrl(fundCode);
        }
        return getAktoolFundNetValueList(fundCode, url);
    }

    /**
     * 获取开放型基金全部净值
     *
     * @param fundCode
     * @return
     */
    public static List<AktoolFundNetValueVO> getOpenFundNetValueFull(String fundCode) {
        String url = AkToolConstant.getOpenFundNetValueFullUrl(fundCode);
        return getAktoolFundNetValueList(fundCode, url);
    }

    /**
     * 获取Aktool净值
     *
     * @param fundCode
     * @param url
     * @return
     */
    private static List<AktoolFundNetValueVO> getAktoolFundNetValueList(String fundCode, String url) {
        String response = HttpUtils.get(url);
        log.info("向Aktool请求获取[{}]基金净值接口，响应：{}", fundCode, response);
        // 解析响应数据
        List<AktoolFundNetValueVO> fundNetValueList = JSON.parseArray(response, AktoolFundNetValueVO.class);
        if (CollectionUtil.isEmpty(fundNetValueList)) {
            log.error("基金净值数据为空，响应：{}", JSON.toJSONString(fundNetValueList));
            return new ArrayList<>();
        }
        return fundNetValueList;
    }
}
