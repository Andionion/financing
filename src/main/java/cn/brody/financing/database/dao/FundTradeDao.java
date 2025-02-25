package cn.brody.financing.database.dao;

import cn.brody.financing.database.entity.FundTradeEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * FundTradeDao
 *
 * @author chenyifu6
 * @since 2024/11/08 09:58
 */
public interface FundTradeDao extends IService<FundTradeEntity> {

    /**
     * @param belong
     * @return
     */
    List<FundTradeEntity> listByBelong(String belong);

    /**
     * 根据基金代码和所属人列出交易实体。
     *
     * @param fundCode 需要查询的基金代码。
     * @param belong   需要查询的基金所属类别。
     * @return 返回一个包含所有符合条件的FundTradeEntity对象的列表。
     */
    List<FundTradeEntity> listByFundCodeAndBelong(String fundCode, String belong);

    /**
     * 获取所有交易方
     *
     * @return
     */
    List<String> listAllBelongs();
}
