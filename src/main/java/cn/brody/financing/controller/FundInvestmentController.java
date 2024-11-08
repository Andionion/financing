package cn.brody.financing.controller;

import cn.brody.financing.pojo.base.BaseResponse;
import cn.brody.financing.pojo.bo.BondFundPurchaseBO;
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


    /**
     * 购买债券基金。
     *
     * @param bo      包含购买债券基金所需信息的BondFundPurchaseBO对象。
     * @param request HttpServletRequest对象，用于获取请求信息。
     * @return 返回一个BaseResponse对象，表示操作的结果。
     */
    @PostMapping("/bond/purchase")
    public BaseResponse<?> purchaseBondFund(@RequestBody BondFundPurchaseBO bo, HttpServletRequest request) {
        fundInvestmentService.purchaseBondFund(bo);
        return new BaseResponse<>();
    }

}
