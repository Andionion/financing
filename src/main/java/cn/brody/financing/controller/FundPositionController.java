package cn.brody.financing.controller;

import cn.brody.financing.pojo.base.BaseResponse;
import cn.brody.financing.service.FundPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author brody
 * @date 2021/12/16
 */
@RestController
@RequestMapping("/fund/position")
public class FundPositionController {
    @Autowired
    private FundPositionService fundPositionService;

    @PostMapping("/share/calculate")
    public BaseResponse<?> calculateShare() {
        fundPositionService.calculateShare();
        return new BaseResponse<>();
    }
}
