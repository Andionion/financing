package cn.brody.financing.controller;

import cn.brody.financing.pojo.base.BaseResponse;
import cn.brody.financing.pojo.bo.AddOrUpdateFundBO;
import cn.brody.financing.pojo.bo.DelFundBO;
import cn.brody.financing.service.FundBasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 基金添加，删除controller
 *
 * @author chenyifu6
 * @date 2021/10/26
 */
@RestController
@RequestMapping("/fund/operation")
public class FundOperationController {

    @Autowired
    private FundBasicService fundBasicService;

    /**
     * 添加基金
     *
     * @param addOrUpdateFundBO
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<?> addFund(@RequestBody AddOrUpdateFundBO addOrUpdateFundBO) {
        fundBasicService.addOrUpdateFund(addOrUpdateFundBO);
        return new BaseResponse<>();
    }

    /**
     * 删除基金
     *
     * @param delFundBO
     * @return
     */
    @PostMapping("/del")
    public BaseResponse<?> delFund(@RequestBody DelFundBO delFundBO) {
        fundBasicService.delFund(delFundBO);
        return new BaseResponse<>();
    }
}
