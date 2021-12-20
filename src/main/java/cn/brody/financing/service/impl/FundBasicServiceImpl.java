package cn.brody.financing.service.impl;

import cn.brody.financing.mapper.FundBasicDao;
import cn.brody.financing.mapper.FundPositionDao;
import cn.brody.financing.mapper.FundTradeRecordDao;
import cn.brody.financing.pojo.bo.AddOrUpdateFundBO;
import cn.brody.financing.pojo.bo.DelFundBO;
import cn.brody.financing.pojo.entity.FundBasicEntity;
import cn.brody.financing.pojo.entity.FundPositionEntity;
import cn.brody.financing.pojo.entity.FundTradeRecordEntity;
import cn.brody.financing.pojo.vo.AnnualizedRateVO;
import cn.brody.financing.service.FundBasicService;
import cn.brody.financing.service.FundNetWorthService;
import cn.brody.financing.support.financial.response.FundDetailResponse;
import cn.brody.financing.support.financial.service.FinancialDataService;
import cn.brody.financing.util.IrrUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.decampo.xirr.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chenyifu6
 * @date 2021/10/26
 */
@Slf4j
@Service
public class FundBasicServiceImpl implements FundBasicService {
    @Autowired
    private FinancialDataService financialDataService;
    @Autowired
    private FundBasicDao fundBasicDao;
    @Autowired
    private FundTradeRecordDao fundTradeRecordDao;
    @Autowired
    private FundPositionDao fundPositionDao;
    @Autowired
    private FundNetWorthService fundNetWorthService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrUpdateFund(AddOrUpdateFundBO addOrUpdateFundBO) {
        log.debug("开始添加或修改基金，基金代码={}", addOrUpdateFundBO.getCode());
        FundDetailResponse fundDetailResponse = financialDataService.
                getFundDetail(addOrUpdateFundBO.getCode(), LocalDate.of(2020, 8, 31), null);
        FundBasicEntity fundBasicEntity = fundBasicDao.getByCode(addOrUpdateFundBO.getCode());
        if (ObjectUtil.isNull(fundBasicEntity)) {
            fundBasicEntity = new FundBasicEntity();
            fundBasicEntity.setName(fundDetailResponse.getName());
            fundBasicEntity.setType(fundDetailResponse.getType());
            fundBasicEntity.setCode(fundDetailResponse.getCode());
        }
        fundBasicEntity.setBuyRate(fundDetailResponse.getBuyRate());
        fundBasicEntity.setManager(fundDetailResponse.getManager());
        fundBasicEntity.setFundScale(fundDetailResponse.getFundScale());
        if (fundBasicDao.saveOrUpdate(fundBasicEntity)) {
            fundNetWorthService.addFundNetWorth(fundDetailResponse);
            log.debug("添加或修改基金成功，基金代码={}", fundBasicEntity);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delFund(DelFundBO delFundBO) {
        log.debug("开始删除基金，基金代码={}", delFundBO.getCode());
        FundBasicEntity fundBasicEntity = fundBasicDao.getByCode(delFundBO.getCode());
        if (ObjectUtil.isNull(fundBasicEntity)) {
            log.error("基金不存在，删除失败，{}", delFundBO.getCode());
            throw new NullPointerException("基金不存在，删除失败");
        }
        if (fundBasicDao.removeById(fundBasicEntity)) {
            log.info("删除基金成功");
        }
        fundNetWorthService.delFundNetWorth(delFundBO.getCode());
        log.debug("结束删除基金，基金代码={}", delFundBO.getCode());
    }

    @Override
    public List<AnnualizedRateVO> calculateAnnualizedRate() {
        List<FundBasicEntity> fundBasicEntityList = fundBasicDao.list();
        if (CollectionUtil.isEmpty(fundBasicEntityList)) {
            return new ArrayList<>();
        }
        List<AnnualizedRateVO> result = new ArrayList<>();
        List<FundPositionEntity> positionEntityList = new ArrayList<>();
        fundBasicEntityList.forEach(fundBasicEntity -> {
            // 查询所有的交易记录
            List<FundTradeRecordEntity> fundTradeRecordEntityList = fundTradeRecordDao.listByFundCode(fundBasicEntity.getCode());
            if (CollectionUtil.isEmpty(fundTradeRecordEntityList)) {
                return;
            }
            FundPositionEntity fundPositionEntity = fundPositionDao.getByFundCode(fundBasicEntity.getCode());
            List<Transaction> transactionList = fundTradeRecordEntityList.stream().map(trade -> new Transaction(trade.getAmount(), trade.getConfirmDate())).collect(Collectors.toList());
            if (BigDecimal.valueOf(fundPositionEntity.getPresentValue()).compareTo(BigDecimal.ZERO) != 0) {
                transactionList.add(new Transaction(fundPositionEntity.getPresentValue(), fundPositionEntity.getLastDate()));
            }
            log.info("开始计算收益率，{}", JSONObject.toJSONString(transactionList));
            BigDecimal annualizedRate = BigDecimal.valueOf(IrrUtil.xirr(transactionList) * 100).setScale(2, RoundingMode.HALF_UP);
            fundPositionEntity.setAnnualizedRate(annualizedRate.doubleValue());
            positionEntityList.add(fundPositionEntity);
            result.add(new AnnualizedRateVO(fundBasicEntity.getCode(), annualizedRate + "%", fundBasicEntity.getName()));
        });
        fundPositionDao.saveOrUpdateBatch(positionEntityList);
        return result;
    }
}
