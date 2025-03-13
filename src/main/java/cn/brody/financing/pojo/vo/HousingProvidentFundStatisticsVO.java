package cn.brody.financing.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * HousingProvidentFundStatisticsVO
 *
 * @author BrodyChen
 * @since 2025/03/10 14:32
 */
@Data
@AllArgsConstructor
public class HousingProvidentFundStatisticsVO {
    /**
     * 统计日
     */
    private String currentDate;
    /**
     * 总入账
     */
    private Double totalDeposit;
    /**
     * 总提取
     */
    private Double totalWithdrawal;
    /**
     * 总结息
     */
    private Double totalInterest;
    /**
     * 余额
     */
    private Double balance;
    /**
     * 可贷款额度
     */
    private Double loanAvailable;
    /**
     * 公积金记录列表
     */
    List<HousingProvidentFundRecordVO> recordList;

    public HousingProvidentFundStatisticsVO() {
        this.totalDeposit = BigDecimal.ZERO.doubleValue();
        this.totalWithdrawal = BigDecimal.ZERO.doubleValue();
        this.totalInterest = BigDecimal.ZERO.doubleValue();
        this.balance = BigDecimal.ZERO.doubleValue();
        this.loanAvailable = BigDecimal.ZERO.doubleValue();
        this.recordList = new ArrayList<>();
    }
}
