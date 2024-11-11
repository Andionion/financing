package cn.brody.financing.service.impl;

import cn.brody.financing.constant.AkToolConstant;
import cn.brody.financing.database.dao.FundNetValueDao;
import cn.brody.financing.database.dao.TradeDateHistDao;
import cn.brody.financing.database.entity.FundNetValueEntity;
import cn.brody.financing.database.entity.TradeDateHistEntity;
import cn.brody.financing.pojo.vo.FundNetValueVO;
import cn.brody.financing.service.IFundNetValueService;
import cn.brody.financing.utils.HttpUtils;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * FundNetValueServiceImpl
 *
 * @author chenyifu6
 * @since 2024/11/05 14:51
 */
@Slf4j
@Service
public class FundNetValueServiceImpl implements IFundNetValueService {

    @Autowired
    private FundNetValueDao fundNetValueDao;
    @Autowired
    private TradeDateHistDao tradeDateHistDao;

    @Override
    public void saveAllFundNetValue(List<String> fundCodes) {
        for (String fundCode : fundCodes) {
            if (fundNetValueDao.fundNetValueExists(fundCode)) {
                log.info("基金[{}]已经全量更新过，不需要再次全量更新", fundCode);
                continue;
            }
            String fundFullNetValueUrl = AkToolConstant.getFundFullNetValueUrl(fundCode);
            requestAndSave(fundCode, fundFullNetValueUrl);
        }
    }

    @Override
    public void updateTimedFundNetValue(List<String> fundCodes) {
        for (String fundCode : fundCodes) {
            // 获取最新交易日
            TradeDateHistEntity previousTradeDate = tradeDateHistDao.getPreviousTradeDate();
            // 查询基金的该交易日是否已经更新
            if (fundNetValueDao.fundNetValueExists(fundCode, previousTradeDate.getTradeDate())) {
                log.info("基金[{}]在[{}]的净值已经更新过，不需要再次更新", fundCode, previousTradeDate.getTradeDate());
                continue;
            }
            String fundLatestNetValueUrl = AkToolConstant.getFundLatestNetValueUrl(fundCode, previousTradeDate.getTradeDate());
            requestAndSave(fundCode, fundLatestNetValueUrl);
        }
    }

    @Override
    public List<FundNetValueVO> latestFundNetValue(List<String> fundCodes) {
        TradeDateHistEntity previousTradeDate = tradeDateHistDao.getPreviousTradeDate();
        List<FundNetValueEntity> fundNetValueEntities = fundNetValueDao.listFundNetValue(fundCodes, previousTradeDate.getTradeDate());
        return fundNetValueEntities.stream()
                .map(fundNetValueEntity -> BeanUtil.copyProperties(fundNetValueEntity, FundNetValueVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 请求并保存基金净值数据。
     *
     * @param fundCode 基金代码。
     * @param url      请求的URL地址。
     */
    private void requestAndSave(String fundCode, String url) {
        String response = HttpUtils.get(url);
        log.info("请求获取[{}]基金净值接口，响应：{}", fundCode, response);
        // 解析响应数据
        List<FundNetValueVO> fundNetValueList = JSON.parseArray(response, FundNetValueVO.class);
        if (CollectionUtil.isEmpty(fundNetValueList)) {
            log.warn("基金净值数据为空");
            return;
        }
        // 请求基金信息
        // String fundOverview = HttpUtils.get(MaiRuiConstant.getFundOverviewUrl(fundCode));
        // log.info("请求获取[{}]基金信息接口，响应：{}", fundCode, fundOverview);
        // String fundName = JSON.parseObject(fundOverview).get("jc").toString();
        String fundName = null;
        // 保存数据
        List<FundNetValueEntity> fundNetValueEntities = fundNetValueList.stream()
                .map(fundNetValueVO -> new FundNetValueEntity(fundNetValueVO, fundCode, fundName))
                .collect(Collectors.toList());
        fundNetValueDao.saveBatch(fundNetValueEntities);
    }
}
