package cn.brody.financing.service.impl;

import cn.brody.financing.pojo.aktool.AktoolFundNetValueVO;
import cn.brody.financing.pojo.mairui.MairuiFundOverviewVO;
import cn.brody.financing.pojo.mairui.MairuiOpenFundLatestNetValueVO;
import cn.brody.financing.pojo.vo.FundNetValueVO;
import cn.brody.financing.pojo.vo.FundOverviewVO;
import cn.brody.financing.service.IThirdPlatformFundService;
import cn.brody.financing.utils.AktoolUtils;
import cn.brody.financing.utils.MairuiUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ThirdPlatformFundServiceImpl
 *
 * @author 94743
 * @since 2024/12/15 10:58
 */
@Slf4j
@Service
public class ThirdPlatformFundServiceImpl implements IThirdPlatformFundService {

    @Override
    public FundOverviewVO getFundOverview(String fundCode) {
        MairuiFundOverviewVO fundOverviewVO = MairuiUtils.getFundOverviewVO(fundCode);
        return new FundOverviewVO(fundOverviewVO);
    }

    @Override
    public List<FundNetValueVO> getFundNetValueFull(String fundCode) {
        FundOverviewVO fundOverview = getFundOverview(fundCode);
        List<AktoolFundNetValueVO> openFundNetValueFullList = AktoolUtils.getOpenFundNetValueFull(fundCode);
        return openFundNetValueFullList.stream()
                .map(aktoolFundNetValueVO -> new FundNetValueVO(aktoolFundNetValueVO, fundCode, fundOverview.getFundName()))
                .collect(Collectors.toList());
    }

    @Override
    public FundNetValueVO getFundNetValueLatest(String fundCode) {
        MairuiOpenFundLatestNetValueVO fundNetValueLatestVO = MairuiUtils.getFundNetValueLatestVO(fundCode);
        return new FundNetValueVO(fundNetValueLatestVO);
    }
}
