package cn.brody.financing.controller;

import cn.brody.financing.pojo.vo.GoldStatisticsVO;
import cn.brody.financing.service.IGoldTradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * GoldTradeController
 *
 * @author chenyifu6
 * @since 2025-02-25 13:55:11
 */
@Controller
@RequestMapping("/financing/gold/trade")
public class GoldTradeController {

    @Autowired
    private IGoldTradeService goldTradeService;


    @RequestMapping("")
    public ModelAndView index() {
        GoldStatisticsVO goldStatisticsVO = goldTradeService.tabulate();
        Map<String, Object> map = new HashMap<>(1);
        map.put("goldStatistics", goldStatisticsVO);
        return new ModelAndView("gold/trade", map);
    }

    @RequestMapping("/tabulate")
    @ResponseBody
    public GoldStatisticsVO tabulate() {
        return goldTradeService.tabulate();
    }
}
