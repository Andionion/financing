package cn.brody.financing.controller;

import cn.brody.financing.service.IFundInvestmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ViewController
 *
 * @author chenyifu6
 * @since 2024/09/20 11:53
 */
@Controller
@RequestMapping("")
public class ViewController {

    @Autowired
    private IFundInvestmentService fundInvestmentService;

    @RequestMapping("")
    public ModelAndView index() {
        Map<String, Object> map = new HashMap<>();
        List<String> names = fundInvestmentService.listAllNames();
        map.put("names", names);
        return new ModelAndView("index", map);
    }
}
