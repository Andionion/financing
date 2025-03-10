package cn.brody.financing.database.entity;

import cn.brody.financing.enums.HousingFundOpTypeEnum;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 公积金账户操作记录实体
 *
 * @author BrodyChen
 * @since 2025/03/10 10:51
 */
@TableName("housing_provident_fund")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HousingProvidentFundEntity {
    /**
     * 唯一标识
     */
    @TableId
    private Long id;
    /**
     * 业务发生日期
     */
    private LocalDate operationDate;
    /**
     * 操作类型（如：DEPOSIT-存款, WITHDRAWAL-提款, INTEREST-利息结算）
     */
    @Enumerated(EnumType.STRING)
    private HousingFundOpTypeEnum operationType;
    /**
     * 金额
     */
    private BigDecimal amount;
    /**
     * 操作后账户余额
     */
    private BigDecimal balance;
    /**
     * 创建时间（数据库自动填充）
     */
    private LocalDateTime createTime;
    /**
     * 更新时间（数据库自动更新）
     */
    private LocalDateTime updateTime;
}
