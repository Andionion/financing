package cn.brody.financing.pojo.vo;

import cn.brody.financing.database.entity.HousingProvidentFundEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

/**
 * 公积金记录
 *
 * @author BrodyChen
 * @since 2025/03/10 14:32
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HousingProvidentFundRecordVO {
    /**
     * 操作日期
     */
    private String operationDate;
    /**
     * 操作类型，见{@link cn.brody.financing.enums.HousingFundOpTypeEnum}
     */
    private String operationType;
    /**
     * 金额
     */
    private Double amount;
    /**
     * 余额
     */
    private Double balance;

    public HousingProvidentFundRecordVO(HousingProvidentFundEntity housingProvidentFundEntity) {
        this.operationDate = housingProvidentFundEntity.getOperationDate().format(DateTimeFormatter.ISO_LOCAL_DATE);
        this.operationType = housingProvidentFundEntity.getOperationType().name().toLowerCase();
        this.amount = housingProvidentFundEntity.getAmount().doubleValue();
        this.balance = housingProvidentFundEntity.getBalance().doubleValue();
    }
}
