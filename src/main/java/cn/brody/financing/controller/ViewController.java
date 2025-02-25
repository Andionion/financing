package cn.brody.financing.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * ViewController
 *
 * @author chenyifu6
 * @since 2024/09/20 11:53
 */
@Controller
@RequestMapping("")
public class ViewController {

    @RequestMapping("")
    public ModelAndView index() {
        return new ModelAndView("index");
    }
}
