package cn.brody.financing.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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


    @RequestMapping("")
    public ModelAndView index() {
        Map<String, Object> map = new HashMap<>();
        return new ModelAndView("/gold/trade", map);
    }
}
