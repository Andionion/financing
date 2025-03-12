package cn.brody.financing.pojo.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * HousingProvidentFundAddBO
 *
 * @author BrodyChen
 * @since 2025/03/10 14:15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HousingProvidentFundAddBatchBO {

    /**
     * 新增记录
     */
    private List<HousingProvidentFundAddBO> list;

}
