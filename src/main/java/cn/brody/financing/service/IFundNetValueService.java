package cn.brody.financing.service;

import cn.brody.financing.pojo.bo.LatestFundNetValueBO;
import cn.brody.financing.pojo.vo.FundNetValueVO;

import java.util.List;

/**
 * IFundNetValueService
 *
 * @author chenyifu6
 * @since 2024/11/05 14:49
 */
public interface IFundNetValueService {

    /**
     * 全量保存基金的历史基金净值
     *
     * @param fundCode 基金代码
     */
    void saveAllFundNetValue(String fundCode);

    /**
     * 定时更新保存基金的历史净值，每日更新即可
     *
     * @param fundCode 基金代码
     */
    void updateTimedFundNetValue(String fundCode);

    /**
     * 获取指定基金列表的最新净值
     *
     * @param bo 请求参数
     * @return
     */
    List<FundNetValueVO> latestFundNetValue(LatestFundNetValueBO bo);

}
