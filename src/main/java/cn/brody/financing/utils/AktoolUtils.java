package cn.brody.financing.utils;

import cn.brody.financing.constant.AkToolConstant;
import cn.brody.financing.constant.MaiRuiConstant;
import cn.brody.financing.pojo.aktool.AktoolFundNetValueVO;
import cn.brody.financing.pojo.aktool.AktoolFundOverviewVO;
import cn.brody.financing.pojo.mairui.MairuiFundOverviewVO;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
     * 获取基金概况
     *
     * @param fundCode 基金代码
     * @return 基金概况
     */
    public static AktoolFundOverviewVO getFundOverviewVO(String fundCode) {
        List cachedFundJson = RedisUtils.getString("fundOverview", List.class);
        List<AktoolFundOverviewVO> cachedFundList = JSONObject.parseArray(JSONObject.toJSONString(cachedFundJson), AktoolFundOverviewVO.class);
        if (CollectionUtil.isNotEmpty(cachedFundList)) {
            log.debug("从缓存中获取基金列表");
            // 从缓存列表中查找目标基金
            Optional<AktoolFundOverviewVO> fundOverviewOpt = cachedFundList.stream()
                    .filter(aktoolFundOverviewVO -> aktoolFundOverviewVO.getFundCode().equals(fundCode))
                    .findFirst();

            if (fundOverviewOpt.isPresent()) {
                log.debug("基金[{}]信息命中缓存", fundCode);
                return fundOverviewOpt.get();
            } else {
                log.warn("基金[{}]信息未在缓存列表中找到", fundCode);
            }
        }
        // 缓存中不存在或未找到目标基金，从远程接口获取
        String fundOverview = HttpUtils.get(AkToolConstant.getFundOverviewUrl());
        log.debug("向Aktool请求获取基金列表接口，响应：{}", fundOverview);

        if (StrUtil.isBlank(fundOverview)) {
            log.error("响应为空，基金代码：{}", fundCode);
            throw new RuntimeException("向Aktool请求获取基金概况失败，基金代码：" + fundCode);
        }
        // 解析响应数据
        List<AktoolFundOverviewVO> aktoolFundOverviewVOList = JSON.parseArray(fundOverview, AktoolFundOverviewVO.class);

        if (CollectionUtil.isEmpty(aktoolFundOverviewVOList)) {
            log.error("解析到的基金列表为空，基金代码：{}", fundCode);
            throw new RuntimeException("解析到的基金列表为空，基金代码：" + fundCode);
        }

        // 将基金列表存入缓存
        aktoolFundOverviewVOList = JSON.parseArray(fundOverview, AktoolFundOverviewVO.class);
        RedisUtils.setString("fundOverview", aktoolFundOverviewVOList);
        log.debug("基金列表已存入缓存");

        // 从列表中查找目标基金
        Optional<AktoolFundOverviewVO> fundOverviewOpt = aktoolFundOverviewVOList.stream()
                .filter(aktoolFundOverviewVO -> aktoolFundOverviewVO.getFundCode().equals(fundCode))
                .findFirst();

        if (fundOverviewOpt.isEmpty()) {
            log.error("未找到基金代码为[{}]的基金信息", fundCode);
            throw new RuntimeException("未找到基金代码为[" + fundCode + "]的基金信息");
        }

        return fundOverviewOpt.get();
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
