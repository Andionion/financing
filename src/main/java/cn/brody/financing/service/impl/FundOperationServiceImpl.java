package cn.brody.financing.service.impl;

import cn.brody.financing.mapper.FundBasicDao;
import cn.brody.financing.mapper.FundNetWorthDao;
import cn.brody.financing.pojo.bo.AddFundBO;
import cn.brody.financing.pojo.bo.AddFundNetWorthBO;
import cn.brody.financing.pojo.bo.DelFundBO;
import cn.brody.financing.pojo.entity.FundBasicEntity;
import cn.brody.financing.pojo.entity.FundNetWorthEntity;
import cn.brody.financing.service.FundOperationService;
import cn.brody.financing.support.financial.response.FundDetailResponse;
import cn.brody.financing.support.financial.service.FinancialDataService;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author chenyifu6
 * @date 2021/10/26
 */
@Slf4j
@Service
public class FundOperationServiceImpl implements FundOperationService {
    @Autowired
    private FinancialDataService financialDataService;
    @Autowired
    private FundBasicDao fundBasicDao;
    @Autowired
    private FundNetWorthDao fundNetWorthDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addFund(AddFundBO addFundBO) {
        log.debug("开始添加基金，基金代码={}", addFundBO.getCode());
        FundDetailResponse fundDetailResponse = financialDataService.getFundDetail(addFundBO.getCode(), LocalDate.of(2020, 8, 31), null);
        FundBasicEntity fundBasicEntity = fundBasicDao.getByCode(addFundBO.getCode());
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
            addFundNetWorth(fundDetailResponse);
            log.debug("添加基金成功，基金代码={}", fundBasicEntity);
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
        delFundNetWorth(delFundBO.getCode());
        log.debug("结束删除基金，基金代码={}", delFundBO.getCode());
    }

    @Override
    public void addFundNetWorth(AddFundNetWorthBO addFundNetWorthBO) {
        log.debug("开始添加基金净值记录，addFundNetWorthBO={}", addFundNetWorthBO);
        FundDetailResponse fundDetailResponse = financialDataService
                .getFundDetail(addFundNetWorthBO.getCode(), addFundNetWorthBO.getStartDate(), addFundNetWorthBO.getEndDate());
        addFundNetWorth(fundDetailResponse);
        log.debug("结束添加基金净值记录，addFundNetWorthBO={}", addFundNetWorthBO);
    }

    @Override
    public void addFundNetWorth(FundDetailResponse fundDetailResponse) {
        log.debug("开始添加基金净值记录");
        List<List<String>> netWorthDataList = fundDetailResponse.getNetWorthData();
        List<List<String>> totalNetWorthDataList = fundDetailResponse.getTotalNetWorthData();
        if (CollectionUtil.isNotEmpty(netWorthDataList) && CollectionUtil.isNotEmpty(totalNetWorthDataList)) {
            List<FundNetWorthEntity> fundNetWorthEntityList = new ArrayList<>();
            Map<String, List<String>> totalWorthMap = totalNetWorthDataList.stream()
                    .collect(Collectors.toMap(list -> list.get(0), list -> list));
            netWorthDataList.forEach(list -> {
                if (CollectionUtil.isNotEmpty(list)) {
                    // 历史单位净值信息 ["2001-12-18" , 1 , 0 , ""] 依次表示：日期；单位净值；净值涨幅；每份分红
                    String date = list.get(0);
                    double netWorth = Double.parseDouble(list.get(1));
                    String dividendString = list.get(3);
                    String group0 = ReUtil.getGroup0("\\d\\.\\d+", dividendString);
                    double dividends = StrUtil.isBlank(group0) ? 0 : Double.parseDouble(group0);
                    // 历史累计净值信息 ["2001-12-18" , 1 ] 依次表示：日期；累计净值
                    List<String> totalWorthData = totalWorthMap.get(date);
                    double totalWorth = Double.parseDouble(totalWorthData.get(1) == null ? "0" : totalWorthData.get(1));
                    fundNetWorthEntityList.add(new FundNetWorthEntity(fundDetailResponse.getCode(), LocalDateTimeUtil.parseDate(date), netWorth, totalWorth, dividends));
                }
            });
            if (CollectionUtil.isNotEmpty(fundNetWorthEntityList)) {
                // 当没有这个日期时，进行插入操作
                List<FundNetWorthEntity> netWorthEntityList;
                List<FundNetWorthEntity> list = fundNetWorthDao.listNetWorthList(fundDetailResponse.getCode());
                if (CollectionUtil.isNotEmpty(list)) {
                    List<LocalDate> existNetWorthList = list.stream().map(FundNetWorthEntity::getDate).collect(Collectors.toList());
                    netWorthEntityList = fundNetWorthEntityList.stream().filter(netWorth -> !existNetWorthList.contains(netWorth.getDate()))
                            .collect(Collectors.toList());
                } else {
                    netWorthEntityList = fundNetWorthEntityList;
                }
                fundNetWorthDao.saveOrUpdateBatch(netWorthEntityList);
            }
        }
        log.debug("结束添加基金净值记录");
    }

    @Override
    public void delFundNetWorth(String code) {
        fundNetWorthDao.removeNetWorth(code);
    }
}
