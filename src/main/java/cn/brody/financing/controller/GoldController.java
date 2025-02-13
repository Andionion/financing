package cn.brody.financing.controller;

import cn.brody.financing.pojo.base.BaseResponse;
import cn.brody.financing.pojo.bo.BondFundPurchaseBO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * GoldController
 *
 * @author 94743
 * @since 2025/01/11 16:36
 */
@RestController
@RequestMapping("/financing/gold")
public class GoldController {

    @PostMapping("/investment")
    public BaseResponse<?> purchaseBondFund(@RequestBody BondFundPurchaseBO bo, HttpServletRequest request) {
        return new BaseResponse<>();
    }
}
