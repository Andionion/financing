package cn.brody.financing.controller;

import cn.brody.financing.pojo.base.BaseList;
import cn.brody.financing.pojo.vo.FundTradeVO;
import cn.brody.financing.service.IFundInvestmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * FundInvestmentViewController
 *
 * @author chenyifu6
 * @since 2024/11/14 15:10
 */
@Controller
@RequestMapping("/financing/fund")
public class FundInvestmentViewController {

    @Autowired
    private IFundInvestmentService fundInvestmentService;

    @RequestMapping("")
    public ModelAndView index() {
        Map<String, Object> map = new HashMap<>();
        List<String> names = fundInvestmentService.listAllNames();
        map.put("names", names);
        return new ModelAndView("fund", map);
    }

    @RequestMapping("/investment/view/calculate/{belong}")
    public ModelAndView calculate(@PathVariable("belong") String belong) {
        BaseList<FundTradeVO> fundCalculateVOBaseList = fundInvestmentService.calculate(belong);
        Map<String, Object> map = new HashMap<>(2);
        map.put("tradeList", fundCalculateVOBaseList.getList());
        map.put("belong", belong);
        return new ModelAndView("fundInvestment", map);
    }
}
