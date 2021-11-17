package cn.brody.financing.service;

import cn.brody.financing.pojo.bo.AddTradeBO;

/**
 * 交易记录 service
 *
 * @author brody
 * @date 2021/11/03
 */
public interface FundTradeService {

    /**
     * 添加交易记录
     * <p>
     * 将交易记录存储下来，如果当日净值存在，则计算份额，否则份额置为空
     *
     * @param addTradeBO 添加交易记录 BO
     */
    void addTradeRecord(AddTradeBO addTradeBO);
}