package cn.brody.financing.service;

import cn.brody.financing.pojo.bo.AddOrUpdateFundBO;
import cn.brody.financing.pojo.bo.DelFundBO;

/**
 * 基金添加，删除等操作的service
 *
 * @author chenyifu6
 * @date 2021/10/26
 */
public interface FundBasicService {

    /**
     * 添加基金
     *
     * @param addOrUpdateFundBO 添加基金的请求类
     */
    void addOrUpdateFund(AddOrUpdateFundBO addOrUpdateFundBO);

    /**
     * 删除基金
     *
     * @param delFundBO
     */
    void delFund(DelFundBO delFundBO);

}
