package cn.brody.financing.controller;

import cn.brody.financing.pojo.base.BaseResponse;
import cn.brody.financing.pojo.bo.HousingProvidentFundAddBO;
import cn.brody.financing.service.IHousingProvidentFundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * HousingProvidentFundController
 *
 * @author BrodyChen
 * @since 2025/03/10 14:13
 */
@Controller
@RequestMapping("/financing/housing_provident_fund")
public class HousingProvidentFundController {

    @Autowired
    private IHousingProvidentFundService housingProvidentFundService;

    @RequestMapping("/add")
    @ResponseBody
    public BaseResponse<?> add(@RequestBody HousingProvidentFundAddBO bo) {
        housingProvidentFundService.add(bo);
        return new BaseResponse<>();
    }
}
