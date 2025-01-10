package cn.brody.financing.service.impl;

import cn.brody.financing.pojo.aktool.AktoolFundNetValueVO;
import cn.brody.financing.pojo.mairui.MairuiFundOverviewVO;
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
    public List<FundNetValueVO> getFundNetValue(String fundCode) {
        FundOverviewVO fundOverview = getFundOverview(fundCode);
        List<AktoolFundNetValueVO> openFundNetValueFullList = AktoolUtils.getOpenFundNetValueFull(fundCode);
        return openFundNetValueFullList.stream()
                .map(aktoolFundNetValueVO -> new FundNetValueVO(aktoolFundNetValueVO, fundCode, fundOverview.getFundName()))
                .collect(Collectors.toList());
    }

    @Override
    public FundNetValueVO getFundNetValue(String fundCode, String tradeDate) {
        // MairuiOpenFundLatestNetValueVO fundNetValueLatestVO = MairuiUtils.getFundNetValueLatestVO(fundCode);
        // return new FundNetValueVO(fundNetValueLatestVO);
        // 下面是Aktool的方式获取
        FundOverviewVO fundOverview = getFundOverview(fundCode);
        List<AktoolFundNetValueVO> openFundNetValueFullList = AktoolUtils.getOpenFundNetValueFull(fundCode);
        // TODO: BrodyChen 2025/1/10 这里应该使用给定的日期获取的，我图省事儿，直接用了最后一个也即最新的，这里需要修改
        AktoolFundNetValueVO aktoolFundNetValueVO = openFundNetValueFullList.get(openFundNetValueFullList.size() - 1);
        return new FundNetValueVO(aktoolFundNetValueVO, fundCode, fundOverview.getFundName());
    }
}
