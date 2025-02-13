package cn.brody.financing.service.impl;

import cn.brody.financing.pojo.aktool.AktoolFundNetValueVO;
import cn.brody.financing.pojo.aktool.AktoolFundOverviewVO;
import cn.brody.financing.pojo.vo.FundNetValueVO;
import cn.brody.financing.pojo.vo.FundOverviewVO;
import cn.brody.financing.service.IThirdPlatformFundService;
import cn.brody.financing.utils.AktoolUtils;
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
        AktoolFundOverviewVO fundOverviewVO = AktoolUtils.getFundOverviewVO(fundCode);
        return new FundOverviewVO(fundOverviewVO);
    }

    @Override
    public List<FundNetValueVO> getFundNetValue(String fundCode) {
        FundOverviewVO fundOverview = getFundOverview(fundCode);
        List<AktoolFundNetValueVO> openFundNetValueFullList = AktoolUtils.getOpenFundNetValueFull(fundCode);
        return openFundNetValueFullList.stream()
                .map(aktoolFundNetValueVO -> new FundNetValueVO(aktoolFundNetValueVO, fundCode, fundOverview.getFundName()))
                .collect(Collectors.toList());
    }

    @Override
    public FundNetValueVO getFundNetValue(String fundCode, String tradeDate) {
        FundOverviewVO fundOverview = getFundOverview(fundCode);
        List<AktoolFundNetValueVO> openFundNetValueFullList = AktoolUtils.getOpenFundNetValueFull(fundCode);
        AktoolFundNetValueVO aktoolFundNetValueVO = openFundNetValueFullList.get(openFundNetValueFullList.size() - 1);
        return new FundNetValueVO(aktoolFundNetValueVO, fundCode, fundOverview.getFundName());
    }
}
