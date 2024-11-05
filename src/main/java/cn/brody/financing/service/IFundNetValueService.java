package cn.brody.financing.service;

/**
 * IFundNetValueService
 *
 * @author chenyifu6
 * @since 2024/11/05 14:49
 */
public interface IFundNetValueService {

    /**
     * 保存基金的历史基金净值
     *
     * @param fundCode 基金代码
     */
    void saveFundNetValue(String fundCode);
}
