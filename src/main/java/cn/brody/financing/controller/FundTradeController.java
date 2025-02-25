package cn.brody.financing.controller;

import cn.brody.financing.pojo.base.BaseList;
import cn.brody.financing.pojo.base.BaseResponse;
import cn.brody.financing.pojo.bo.FundTradeAddBO;
import cn.brody.financing.pojo.vo.FundTradeVO;
import cn.brody.financing.service.IFundTradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * FundTradeController
 *
 * @author chenyifu6
 * @since 2024/11/14 15:10
 */
@Controller
@RequestMapping("/financing/fund")
public class FundTradeController {

    @Autowired
    private IFundTradeService fundInvestmentService;

    @RequestMapping("")
    public ModelAndView index() {
        Map<String, Object> map = new HashMap<>();
        List<String> names = fundInvestmentService.listAllNames();
        map.put("names", names);
        return new ModelAndView("/fund/index", map);
    }

    @RequestMapping("/trade/calculate/{belong}")
    public ModelAndView calculate(@PathVariable("belong") String belong) {
        BaseList<FundTradeVO> fundCalculateVOBaseList = fundInvestmentService.calculate(belong);
        Map<String, Object> map = new HashMap<>(2);
        map.put("tradeList", fundCalculateVOBaseList.getList());
        map.put("belong", belong);
        return new ModelAndView("fund/trade", map);
    }

    @PostMapping("/trade/add")
    @ResponseBody
    public BaseResponse<?> trade(@RequestBody FundTradeAddBO bo, HttpServletRequest request) {
        fundInvestmentService.trade(bo);
        return new BaseResponse<>();
    }

    @PostMapping("/trade/calculate")
    @ResponseBody
    public BaseResponse<?> calculate(HttpServletRequest request) {
        return new BaseResponse<>(fundInvestmentService.calculate());
    }
}
