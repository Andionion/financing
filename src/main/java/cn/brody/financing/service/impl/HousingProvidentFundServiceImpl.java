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
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
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
        HousingProvidentFundEntity entity = new HousingProvidentFundEntity();
        entity.setOperationDate(operationDate);
        entity.setOperationType(HousingFundOpTypeEnum.forValue(operationType));
        entity.setAmount(BigDecimal.valueOf(amount));
        // 余额要根据之前的记录计算
        HousingProvidentFundEntity lastRecordEntity = housingProvidentFundDao.getLastRecord();
        BigDecimal lastBalance = null == lastRecordEntity ? BigDecimal.ZERO : lastRecordEntity.getBalance();
        BigDecimal currentBalance = entity.getAmount();
        switch (entity.getOperationType()) {
            case DEPOSIT:
            case INTEREST:
                entity.setBalance(lastBalance.add(currentBalance));
                break;
            case WITHDRAWAL:
                entity.setBalance(lastBalance.subtract(currentBalance));
                break;
            default:
                log.error("公积金操作类型错误，请检查");
                throw new RuntimeException("公积金操作类型错误，请检查");

        }
        return entity;
    }

    @Override
    public void addBatch(List<HousingProvidentFundAddBO> list) {
        // 0. 按操作日期升序排序（确保余额计算顺序正确）
        List<HousingProvidentFundAddBO> sortedBos = list.stream()
                .sorted(Comparator.comparing(HousingProvidentFundAddBO::getOperationDate))
                .collect(Collectors.toList());
        // 1. 获取当前数据库最新余额作为初始值
        HousingProvidentFundEntity lastRecordEntity = housingProvidentFundDao.getLastRecord();
        BigDecimal lastBalance = null == lastRecordEntity ? BigDecimal.ZERO : lastRecordEntity.getBalance();
        // 2. 创建实体列表并逐个计算余额
        List<HousingProvidentFundEntity> entities = new ArrayList<>(list.size());
        for (HousingProvidentFundAddBO bo : sortedBos) {
            HousingProvidentFundEntity entity = buildBatchEntity(bo, lastBalance);
            entities.add(entity);
            // 更新当前余额
            lastBalance = entity.getBalance();
        }
        // 3. 批量保存（需确保DAO支持批量操作）
        housingProvidentFundDao.saveBatch(entities);
    }

    private HousingProvidentFundEntity buildBatchEntity(HousingProvidentFundAddBO bo, BigDecimal lastBalance) {
        HousingProvidentFundEntity entity = new HousingProvidentFundEntity();
        entity.setOperationDate(bo.getOperationDate());
        entity.setOperationType(HousingFundOpTypeEnum.forValue(bo.getOperationType()));

        BigDecimal amount = BigDecimal.valueOf(bo.getAmount());
        entity.setAmount(amount);

        // 根据操作类型计算新余额
        switch (entity.getOperationType()) {
            case DEPOSIT:
            case INTEREST:
                entity.setBalance(lastBalance.add(amount));
                break;
            case WITHDRAWAL:
                entity.setBalance(lastBalance.subtract(amount));
                break;
            default:
                throw new IllegalArgumentException("无效操作类型: " + bo.getOperationType());
        }
        return entity;
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
        providentFundEntities.stream().
                max(Comparator.comparing(HousingProvidentFundEntity::getOperationDate)
                        .thenComparing(HousingProvidentFundEntity::getCreateTime))
                .ifPresent(lastRecord -> result.setBalance(lastRecord.getBalance().doubleValue()));
        // 可贷额度应该查询到最近的12个月的平均余额
        result.setLoanAvailable(calculateLast12MonthsAverageBalance(providentFundEntities).doubleValue());
        return result;
    }


    public BigDecimal calculateLast12MonthsAverageBalance(List<HousingProvidentFundEntity> records) {
        // 1. 校验列表非空
        if (records == null || records.isEmpty()) {
            return BigDecimal.ZERO;
        }

        // 2. 获取最大 operationDate 作为结束月份
        LocalDate maxDate = records.stream()
                .map(HousingProvidentFundEntity::getOperationDate)
                .max(LocalDate::compareTo)
                .orElseThrow(() -> new IllegalArgumentException("记录列表为空"));
        YearMonth endYearMonth = YearMonth.from(maxDate);
        YearMonth startYearMonth = endYearMonth.minusMonths(11); // 12个月范围

        // 3. 生成12个月的所有 YearMonth 对象
        List<YearMonth> requiredMonths = new ArrayList<>();
        YearMonth current = startYearMonth;
        for (int i = 0; i < 12; i++) {
            requiredMonths.add(current);
            current = current.plusMonths(1);
        }

        // 4. 按月份分组，并过滤出目标范围内的记录
        Map<YearMonth, List<HousingProvidentFundEntity>> groupedByMonth = records.stream()
                .filter(entity -> {
                    YearMonth ym = YearMonth.from(entity.getOperationDate());
                    return !ym.isBefore(startYearMonth) && !ym.isAfter(endYearMonth);
                })
                .collect(Collectors.groupingBy(entity -> YearMonth.from(entity.getOperationDate())));

        // 5. 获取每个月的最后一条记录的余额
        Map<YearMonth, BigDecimal> monthlyBalances = new LinkedHashMap<>();
        int count = 0;
        for (YearMonth ym : requiredMonths) {
            List<HousingProvidentFundEntity> monthlyRecords = groupedByMonth.get(ym);
            if (monthlyRecords == null || monthlyRecords.isEmpty()) {
                log.info("月份 {} 没有记录", ym);
                continue;
            }

            // 按日期排序，取当月最后一条记录
            HousingProvidentFundEntity lastRecord = monthlyRecords.stream()
                    .max(Comparator.comparing(HousingProvidentFundEntity::getOperationDate)
                            .thenComparing(HousingProvidentFundEntity::getCreateTime))
                    .orElse(null);
            monthlyBalances.put(ym, lastRecord.getBalance());
            count++;
        }

        // 6. 计算总和并求平均值
        BigDecimal sum = monthlyBalances.values().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal multiplicand = BigDecimal.valueOf((double) 15 / count);
        return sum.multiply(multiplicand).setScale(2, RoundingMode.HALF_UP);
    }
}
