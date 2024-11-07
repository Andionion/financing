package cn.brody.financing.service.impl;

import cn.brody.financing.constant.AkToolConstant;
import cn.brody.financing.database.dao.FundNetValueDao;
import cn.brody.financing.database.entity.FundNetValueEntity;
import cn.brody.financing.pojo.bo.LatestFundNetValueBO;
import cn.brody.financing.pojo.vo.FundNetValueVO;
import cn.brody.financing.service.IFundNetValueService;
import cn.brody.financing.utils.HttpUtils;
import cn.brody.financing.utils.UrlBuilderUtils;
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

    @Override
    public void saveAllFundNetValue(String fundCode) {
        String eftFundNetValueUrl = UrlBuilderUtils
                .fromBaseUrl(String.format(AkToolConstant.SERVER_ADDRESS + AkToolConstant.URI_ETF_FUND_NET_VALUE, fundCode))
                .build();
        String response = HttpUtils.get(eftFundNetValueUrl);
        log.info("请求ETF_FUND_NET_VALUE，响应：{}", response);
        // 解析响应数据
        List<FundNetValueVO> fundNetValueList = JSON.parseArray(response, FundNetValueVO.class);
        if (CollectionUtil.isEmpty(fundNetValueList)) {
            log.warn("基金净值数据为空");
            return;
        }
        // 保存数据
        List<FundNetValueEntity> fundNetValueEntities = fundNetValueList.stream().map(fundNetValueVO -> {
            FundNetValueEntity fundNetValueEntity = BeanUtil.copyProperties(fundNetValueVO, FundNetValueEntity.class);
            fundNetValueEntity.setFundCode(fundCode);
            return fundNetValueEntity;
        }).collect(Collectors.toList());
        fundNetValueDao.saveBatch(fundNetValueEntities);
    }

    @Override
    public void updateTimedFundNetValue(String fundCode) {
        
    }

    @Override
    public List<FundNetValueVO> latestFundNetValue(LatestFundNetValueBO bo) {
        return List.of();
    }
}
