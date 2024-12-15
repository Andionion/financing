package cn.brody.financing.utils;

import cn.brody.financing.constant.MaiRuiConstant;
import cn.brody.financing.pojo.mairui.MairuiOpenFundLatestNetValueVO;
import cn.brody.financing.pojo.mairui.MairuiFundOverviewVO;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

/**
 * MairuiUtils
 *
 * @author 94743
 * @since 2024/12/14 16:17
 */
@Slf4j
public class MairuiUtils {

    /**
     * 获取基金概况
     *
     * @param fundCode
     * @return
     */
    public static MairuiFundOverviewVO getFundOverviewVO(String fundCode) {
        String fundOverview = HttpUtils.get(MaiRuiConstant.getFundOverviewUrl(fundCode));
        log.info("向Mairui请求获取[{}]基金信息接口，响应：{}", fundCode, fundOverview);
        if (StrUtil.isBlank(fundOverview)) {
            log.error("响应为空");
            throw new RuntimeException("向Mairui请求获取基金概况失败");
        }
        return JSON.parseObject(fundOverview, MairuiFundOverviewVO.class);
    }

    /**
     * 获取开放型基金最新净值
     *
     * @param fundCode 基金代码
     * @return 开放型基金最新净值
     */
    public static MairuiOpenFundLatestNetValueVO getFundNetValueLatestVO(String fundCode) {
        String openFundNetValueLatest = HttpUtils.get(MaiRuiConstant.getOpenFundNetValueLatestUrl(fundCode));
        if (StrUtil.isBlank(openFundNetValueLatest)) {
            log.error("响应为空");
            throw new RuntimeException("向Mairui请求获取开放型基金最新净值失败");
        }
        return JSON.parseObject(openFundNetValueLatest, MairuiOpenFundLatestNetValueVO.class);
    }
}
