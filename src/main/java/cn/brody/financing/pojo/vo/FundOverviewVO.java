package cn.brody.financing.pojo.vo;

import cn.brody.financing.pojo.mairui.MairuiFundOverviewVO;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * FundOverviewVO
 *
 * @author 94743
 * @since 2024/12/14 16:35
 */
@Data
@NoArgsConstructor
public class FundOverviewVO {
    /**
     * 基金代码
     */
    private String fundCode;
    /**
     * 基金名称
     */
    private String fundName;

    public FundOverviewVO(MairuiFundOverviewVO fundOverviewVO) {
        this.fundCode = fundOverviewVO.getDm();
        this.fundName = fundOverviewVO.getJc();
    }
}
