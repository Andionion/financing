package cn.brody.financing.controller;

import cn.brody.financing.pojo.base.BaseResponse;
import cn.brody.financing.pojo.bo.FundTradeBO;
import cn.brody.financing.service.IFundInvestmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * FundInvestmentController
 *
 * @author chenyifu6
 * @since 2024/11/08 09:09
 */
@RestController
@RequestMapping("/financing/fund/investment")
public class FundInvestmentController {

    @Autowired
    private IFundInvestmentService fundInvestmentService;

    @PostMapping("/trade")
    public BaseResponse<?> trade(@RequestBody FundTradeBO bo, HttpServletRequest request) {
        fundInvestmentService.trade(bo);
        return new BaseResponse<>();
    }

    @PostMapping("/calculate")
    public BaseResponse<?> calculate(HttpServletRequest request) {
        return new BaseResponse<>(fundInvestmentService.calculate());
    }

}
