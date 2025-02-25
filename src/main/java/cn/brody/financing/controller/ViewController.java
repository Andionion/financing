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
public class ViewController {

    /**
     * 处理首页请求。
     *
     * @return 返回一个ModelAndView对象，该对象包含视图名称"index"。
     */
    @RequestMapping("")
    public ModelAndView index() {
        return new ModelAndView("index");
    }
}
