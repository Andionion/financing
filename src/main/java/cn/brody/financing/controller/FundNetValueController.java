package cn.brody.financing.controller;

import cn.brody.financing.pojo.base.BaseResponse;
import cn.brody.financing.pojo.bo.FundCodeBO;
import cn.brody.financing.pojo.bo.LatestFundNetValueBO;
import cn.brody.financing.service.IFundNetValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * FundNetValueController
 *
 * @author chenyifu6
 * @since 2024/11/05 17:05
 */
@RestController
@RequestMapping("/financing")
public class FundNetValueController {

    @Qualifier("fundNetValueServiceImpl")
    @Autowired
    private IFundNetValueService fundNetValueService;

    @PostMapping("/fund/net-value/save-all")
    public BaseResponse<?> saveAllFundNetValue(@RequestBody FundCodeBO bo, HttpServletRequest request) {
        fundNetValueService.saveAllFundNetValue(bo.getFundCode());
        return new BaseResponse<>();
    }

    @PostMapping("/fund/net-value/update-timed")
    public BaseResponse<?> updateTimedFundNetValue(@RequestBody FundCodeBO bo, HttpServletRequest request) {
        fundNetValueService.updateTimedFundNetValue(bo.getFundCode());
        return new BaseResponse<>();
    }

    @PostMapping("/fund/net-value/latest")
    public BaseResponse<?> latestFundNetValue(@RequestBody LatestFundNetValueBO bo, HttpServletRequest request) {
        fundNetValueService.latestFundNetValue(bo);
        return new BaseResponse<>();
    }
}
