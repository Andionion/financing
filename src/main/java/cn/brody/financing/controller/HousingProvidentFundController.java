package cn.brody.financing.controller;

import cn.brody.financing.pojo.base.BaseResponse;
import cn.brody.financing.pojo.bo.HousingProvidentFundAddBO;
import cn.brody.financing.pojo.bo.HousingProvidentFundDelBO;
import cn.brody.financing.pojo.bo.HousingProvidentFundUpdateBO;
import cn.brody.financing.pojo.vo.HousingProvidentFundStatisticsVO;
import cn.brody.financing.service.IHousingProvidentFundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @RequestMapping("")
    public ModelAndView index() {
        HousingProvidentFundStatisticsVO tabulate = housingProvidentFundService.tabulate();
        Map<String, Object> map = new HashMap<>(1);
        map.put("recordStatistics", tabulate);
        return new ModelAndView("housingProvidentFund/recordAndTabulate", map);
    }

    @PostMapping("/add")
    @ResponseBody
    public BaseResponse<?> add(@RequestBody HousingProvidentFundAddBO bo) {
        housingProvidentFundService.add(bo);
        return new BaseResponse<>();
    }

    @PostMapping("/add_batch")
    @ResponseBody
    public BaseResponse<?> addBatch(@RequestBody List<HousingProvidentFundAddBO> list) {
        housingProvidentFundService.addBatch(list);
        return new BaseResponse<>();
    }

    @PostMapping("/update")
    @ResponseBody
    public BaseResponse<?> update(@RequestBody HousingProvidentFundUpdateBO bo) {
        housingProvidentFundService.update(bo);
        return new BaseResponse<>();
    }

    @PostMapping("/delete")
    @ResponseBody
    public BaseResponse<?> delete(@RequestBody HousingProvidentFundDelBO bo) {
        housingProvidentFundService.delete(bo);
        return new BaseResponse<>();
    }

    @GetMapping("/list")
    @ResponseBody
    public BaseResponse<?> listAll() {
        return new BaseResponse<>(housingProvidentFundService.listAll());
    }

    @GetMapping("/tabulate")
    @ResponseBody
    public BaseResponse<?> tabulate() {
        return new BaseResponse<>(housingProvidentFundService.tabulate());
    }
}
