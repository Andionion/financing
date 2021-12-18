package cn.brody.financing.service.impl;

import cn.brody.financing.mapper.FundBasicDao;
import cn.brody.financing.mapper.FundNetWorthDao;
import cn.brody.financing.mapper.FundPositionDao;
import cn.brody.financing.mapper.FundTradeRecordDao;
import cn.brody.financing.pojo.entity.FundBasicEntity;
import cn.brody.financing.pojo.entity.FundPositionEntity;
import cn.brody.financing.pojo.entity.FundTradeRecordEntity;
import cn.brody.financing.service.FundPositionService;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author brody
 * @date 2021/12/16
 */
@Slf4j
@Service
public class FundPositionServiceImpl implements FundPositionService {

    @Autowired
    private FundBasicDao fundBasicDao;
    @Autowired
    private FundPositionDao fundPositionDao;
    @Autowired
    private FundTradeRecordDao fundTradeRecordDao;
    @Autowired
    private FundNetWorthDao fundNetWorthDao;

    @Override
    public void calculateShare() {
        List<FundBasicEntity> fundBasicEntityList = fundBasicDao.list();
        List<String> fundCodeList = fundBasicEntityList.stream().map(FundBasicEntity::getCode).collect(Collectors.toList());
        log.info("查询所有基金代码：{}", fundCodeList);
        if (CollectionUtil.isNotEmpty(fundCodeList)) {
            List<FundPositionEntity> positionEntityList = new ArrayList<>();
            fundCodeList.forEach(code -> {
                FundPositionEntity fundPositionEntity = fundPositionDao.getByFundCode(code);
                if (ObjectUtil.isNull(fundPositionEntity)) {
                    fundPositionEntity = new FundPositionEntity();
                    fundPositionEntity.setCode(code);
                }
                List<FundTradeRecordEntity> tradeRecordEntityList = fundTradeRecordDao.listByFundCode(code);
                if (CollectionUtil.isEmpty(tradeRecordEntityList)) {
                    return;
                }
                Optional<Double> reduce = tradeRecordEntityList.stream().map(FundTradeRecordEntity::getConfirmShare).reduce(Double::sum);

                fundPositionEntity.setShare(reduce.map(aDouble -> BigDecimal.valueOf(aDouble).setScale(4, RoundingMode.HALF_UP).doubleValue()).orElse(0.0));
                if (fundPositionEntity.getShare() - 0.0 < 1.0) {
                    fundPositionEntity.setShare(0.0);
                }
                // 获取最新净值
                Double latestNetWorth = fundNetWorthDao.getLatestNetWorth(code);
                fundPositionEntity.setPresentValue(BigDecimal.valueOf(fundPositionEntity.getShare() * latestNetWorth).setScale(2, RoundingMode.HALF_UP).doubleValue());
                positionEntityList.add(fundPositionEntity);
            });
            log.info("准备更新或存储份额：{}", positionEntityList);
            fundPositionDao.saveOrUpdateBatch(positionEntityList);
        }
    }
}
