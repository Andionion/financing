package cn.brody.financing.service;

import cn.brody.financing.pojo.vo.FundNetValueVO;
import cn.brody.financing.pojo.vo.FundOverviewVO;

import java.util.List;

/**
 * IThirdPlatformFundService
 *
 * @author 94743
 * @since 2024/12/14 16:33
 */
public interface IThirdPlatformFundService {

    /**
     * 获取基金金概况
     *
     * @param fundCode
     * @return
     */
    FundOverviewVO getFundOverview(String fundCode);

    /**
     * 获取基金净值历史
     *
     * @param fundCode
     * @return
     */
    List<FundNetValueVO> getFundNetValueFull(String fundCode);

    /**
     * 获取基金最新净值
     *
     * @param fundCode
     * @return
     */
    FundNetValueVO getFundNetValueLatest(String fundCode);
}
