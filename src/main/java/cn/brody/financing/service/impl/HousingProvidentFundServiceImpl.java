package cn.brody.financing.service.impl;

import cn.brody.financing.database.dao.HousingProvidentFundDao;
import cn.brody.financing.database.entity.HousingProvidentFundEntity;
import cn.brody.financing.enums.HousingFundOpTypeEnum;
import cn.brody.financing.pojo.bo.HousingProvidentFundAddBO;
import cn.brody.financing.pojo.bo.HousingProvidentFundDelBO;
import cn.brody.financing.pojo.bo.HousingProvidentFundUpdateBO;
import cn.brody.financing.pojo.vo.HousingProvidentFundRecordVO;
import cn.brody.financing.pojo.vo.HousingProvidentFundStatisticsVO;
import cn.brody.financing.service.IHousingProvidentFundService;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * HousingProvidentFundServiceImpl
 *
 * @author BrodyChen
 * @since 2025/03/10 14:41
 */
@Slf4j
@Service
public class HousingProvidentFundServiceImpl implements IHousingProvidentFundService {

    @Autowired
    private HousingProvidentFundDao housingProvidentFundDao;

    @Override
    public void add(HousingProvidentFundAddBO bo) {
        HousingProvidentFundEntity housingProvidentFundEntity = buildEntity(bo.getOperationDate(), bo.getOperationType(), bo.getAmount());
        housingProvidentFundDao.save(housingProvidentFundEntity);
    }

    private HousingProvidentFundEntity buildEntity(LocalDate operationDate, String operationType, Double amount) {
        HousingProvidentFundEntity housingProvidentFundEntity = new HousingProvidentFundEntity();
        housingProvidentFundEntity.setOperationDate(operationDate);
        housingProvidentFundEntity.setOperationType(HousingFundOpTypeEnum.forValue(operationType));
        housingProvidentFundEntity.setAmount(BigDecimal.valueOf(amount));
        // 余额要根据之前的记录计算
        HousingProvidentFundEntity lastRecordEntity = housingProvidentFundDao.getLastRecord();
        BigDecimal currentBalance = housingProvidentFundEntity.getAmount();
        BigDecimal lastBalance = null == lastRecordEntity ? BigDecimal.ZERO : lastRecordEntity.getBalance();
        switch (housingProvidentFundEntity.getOperationType()) {
            case DEPOSIT:
            case INTEREST:
                currentBalance = lastBalance.add(currentBalance);
                break;
            case WITHDRAWAL:
                currentBalance = lastBalance.subtract(currentBalance);
                break;
            default:
                log.error("公积金操作类型错误，请检查");
                throw new RuntimeException("公积金操作类型错误，请检查");

        }
        housingProvidentFundEntity.setBalance(currentBalance);
        return housingProvidentFundEntity;
    }

    @Override
    public void update(HousingProvidentFundUpdateBO bo) {
        HousingProvidentFundEntity housingProvidentFundEntity = housingProvidentFundDao.getById(bo.getId());
        if (housingProvidentFundEntity == null) {
            log.error("公积金记录不存在，请检查，请求参数：{}", JSONUtil.toJsonStr(bo));
            throw new RuntimeException("公积金记录不存在，请检查");
        }
        HousingProvidentFundEntity newEntity = buildEntity(bo.getOperationDate(), bo.getOperationType(), bo.getAmount());
        newEntity.setId(bo.getId());
        housingProvidentFundDao.updateById(newEntity);
    }

    @Override
    public void delete(HousingProvidentFundDelBO bo) {
        housingProvidentFundDao.removeById(bo.getId());
    }

    @Override
    public List<HousingProvidentFundRecordVO> listAll() {
        List<HousingProvidentFundEntity> providentFundEntities = housingProvidentFundDao.list();
        if (CollectionUtil.isEmpty(providentFundEntities)) {
            return new ArrayList<>();
        }
        return providentFundEntities.stream().map(HousingProvidentFundRecordVO::new).collect(Collectors.toList());
    }

    @Override
    public HousingProvidentFundStatisticsVO tabulate() {
        HousingProvidentFundStatisticsVO result = new HousingProvidentFundStatisticsVO();
        List<HousingProvidentFundEntity> providentFundEntities = housingProvidentFundDao.list();
        if (CollectionUtil.isEmpty(providentFundEntities)) {
            return result;
        }
        // 各类型的总金额
        Map<HousingFundOpTypeEnum, BigDecimal> opTypeTotalMap = providentFundEntities.stream()
                .collect(Collectors.groupingBy(
                        HousingProvidentFundEntity::getOperationType,
                        // 对金额求和（BigDecimal 需要特殊处理）
                        Collectors.mapping(
                                HousingProvidentFundEntity::getAmount,
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)
                        ))
                );
        // 各分类入账
        opTypeTotalMap.forEach((opType, opTypeTotal) -> {
            switch (opType) {
                case DEPOSIT:
                    result.setTotalDeposit(opTypeTotal.doubleValue());
                    break;
                case WITHDRAWAL:
                    result.setTotalWithdrawal(opTypeTotal.doubleValue());
                    break;
                case INTEREST:
                    result.setTotalInterest(opTypeTotal.doubleValue());
                    break;
            }
        });
        // 当前余额























































































        
        result.setBalance();
        result.setLoanAvailable();
        return null;
    }
}
