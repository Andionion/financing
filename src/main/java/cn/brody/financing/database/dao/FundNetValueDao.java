package cn.brody.financing.database.dao;

import cn.brody.financing.database.entity.FundNetValueEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;
import java.util.List;

/**
 * FundNetValueDao
 *
 * @author chenyifu6
 * @since 2024/11/05 17:02
 */
public interface FundNetValueDao extends IService<FundNetValueEntity> {


    /**
     * 检查基金净值是否存在。
     *
     * @param fundCode 需要查询的基金代码。
     * @return 如果基金净值存在，返回true；否则返回false。
     */
    Boolean fundNetValueExists(String fundCode);


    /**
     * 检查特定基金代码在指定交易日期的净值是否存在。
     *
     * @param fundCode  需要查询的基金代码。
     * @param tradeDate 需要查询的交易日期。
     * @return 如果该基金代码在指定交易日期的净值存在，返回true；否则返回false。
     */
    Boolean fundNetValueExists(String fundCode, String tradeDate);


    /**
     * 获取基金净值列表。
     *
     * @param fundCodes 需要查询的基金代码列表。
     * @param tradeDate 交易日期，格式为"yyyy-MM-dd"。
     * @return 返回一个包含所有指定基金在指定交易日期的净值信息的列表。
     */
    List<FundNetValueEntity> listFundNetValue(Collection<String> fundCodes, String tradeDate);

    /**
     * 获取基金的最新净值。
     *
     * @param fundCode 需要查询的基金代码。
     * @return 返回一个FundNetValueEntity对象，该对象包含了指定基金代码的最新净值信息。
     */
    FundNetValueEntity getFundLatestNetValue(String fundCode);

    /**
     * 查找所有已存储净值的基金代码。
     *
     * @return 返回一个包含已存储净值的基金代码的字符串列表。
     */
    List<String> findAllSavedFundCode();
}
