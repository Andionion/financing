package cn.brody.financing.controller;

import cn.brody.financing.pojo.base.BaseResponse;
import cn.brody.financing.service.FundTradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author brody
 * @date 2021/11/28
 */
@RestController
@RequestMapping("/fund/trade")
public class FundTradeController {

    @Autowired
    private FundTradeService fundTradeService;

    @PostMapping("/import")
    public BaseResponse<?> importTradeRecord(MultipartFile file) {
        fundTradeService.importTradeRecord(file);
        return new BaseResponse<>();
    }

    @PostMapping("/dividend/calculate")
    public BaseResponse<?> calculateDividend() {
        fundTradeService.calculateDividend();
        return new BaseResponse<>();
    }
}
