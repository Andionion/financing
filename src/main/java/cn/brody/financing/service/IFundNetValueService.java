package cn.brody.financing.service;

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
     * 保存所有基金的网络价值。
     *
     * @param fundCodes 需要保存网络价值的基金代码列表。
     */
    void saveAllFundNetValue(List<String> fundCodes);


    /**
     * 更新定时基金净值。
     *
     * @param fundCodes 需要更新净值的基金代码列表。
     */
    void updateTimedFundNetValue(List<String> fundCodes);


    /**
     * 获取最新基金净值。
     *
     * @param fundCodes 需要查询的基金代码列表。
     * @return 返回一个包含所有输入基金代码的最新基金净值的列表，每个元素是一个FundNetValueVO对象，其中包含了基金代码、当前净值等信息。
     */
    List<FundNetValueVO> latestFundNetValue(List<String> fundCodes);

}
