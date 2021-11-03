package cn.brody.financing.service.impl;

import cn.brody.financing.mapper.FundBasicDao;
import cn.brody.financing.mapper.FundNetWorthDao;
import cn.brody.financing.pojo.bo.AddFundBO;
import cn.brody.financing.pojo.bo.DelFundBO;
import cn.brody.financing.pojo.entity.FundBasicEntity;
import cn.brody.financing.pojo.entity.FundNetWorthEntity;
import cn.brody.financing.service.FundOperationService;
import cn.brody.financing.support.financial.request.FundDetailRequest;
import cn.brody.financing.support.financial.response.FundDetailResponse;
import cn.brody.financing.support.financial.service.FinancialDataService;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void addFund(AddFundBO addFundBO) {
        log.debug("开始添加基金：{}", addFundBO.getCode());
        FundDetailResponse fundDetail = financialDataService.getFundDetail(new FundDetailRequest(addFundBO.getCode()));
        FundBasicEntity fundBasicEntity = fundBasicDao.getByCode(addFundBO.getCode());
        if (ObjectUtil.isNull(fundBasicEntity)) {
            fundBasicEntity = new FundBasicEntity();
            fundBasicEntity.setName(fundDetail.getName());
            fundBasicEntity.setType(fundDetail.getType());
            fundBasicEntity.setCode(fundDetail.getCode());
        }
        fundBasicEntity.setBuyRate(fundDetail.getBuyRate());
        fundBasicEntity.setOperatingRate(addFundBO.getOperatingRate());
        fundBasicEntity.setManager(fundDetail.getManager());
        fundBasicEntity.setFundScale(fundDetail.getFundScale());
        if (fundBasicDao.saveOrUpdate(fundBasicEntity)) {
            log.debug("添加基金成功：{}", fundBasicEntity);
        }
    }

    @Override
    public void delFund(DelFundBO delFundBO) {
        log.debug("开始删除基金：{}", delFundBO.getCode());
        FundBasicEntity fundBasicEntity = fundBasicDao.getByCode(delFundBO.getCode());
        if (ObjectUtil.isNull(fundBasicEntity)) {
            log.error("基金不存在，删除失败，{}", delFundBO.getCode());
            throw new NullPointerException("基金不存在，删除失败");
        }
        if (fundBasicDao.removeById(fundBasicEntity)) {
            log.info("删除基金成功");
        }
    }

    @Override
    public void addFundNetWorth(FundDetailResponse fundDetailResponse) {
        log.debug("开始添加基金净值记录");
        List<List<String>> netWorthDataList = fundDetailResponse.getNetWorthData();
        List<List<String>> totalNetWorthDataList = fundDetailResponse.getTotalNetWorthData();
        if (ArrayUtil.isNotEmpty(netWorthDataList) && ArrayUtil.isNotEmpty(totalNetWorthDataList)) {
            List<FundNetWorthEntity> fundNetWorthEntityList = new ArrayList<>();
            Map<String, List<String>> totalWorthMap = totalNetWorthDataList.stream().collect(Collectors.toMap(list -> list.get(0), list -> list));
            netWorthDataList.forEach(list -> {
                if (ArrayUtil.isNotEmpty(list)) {
                    // 历史单位净值信息 ["2001-12-18" , 1 , 0 , ""] 依次表示：日期；单位净值；净值涨幅；每份分红
                    String date = list.get(0);
                    String netWorth = list.get(1);
                    String dividends = list.get(3);
                    // 历史累计净值信息 ["2001-12-18" , 1 ] 依次表示：日期；累计净值
                    List<String> totalWorthData = totalWorthMap.get(date);
                    String totalWorth = totalWorthData.get(1);
                    fundNetWorthEntityList.add(new FundNetWorthEntity(LocalDateTimeUtil.parseDate(date), Double.parseDouble(netWorth),
                            Double.parseDouble(totalWorth), Double.parseDouble(dividends)));
                }
            });
            if (ArrayUtil.isNotEmpty(fundNetWorthEntityList)) {
                // todo 重写批量存储或修改，当日期已经存在时，不做操作（count），当没有这个日期时，进行插入操作
            }
        }
    }
}
