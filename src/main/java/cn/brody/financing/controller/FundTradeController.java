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
@RequestMapping("/financing/fund/trade")
public class FundTradeController {

    @Autowired
    private IFundTradeService fundTradeService;

    /**
     * 获取所有基金名称并返回。
     *
     * @return 返回一个包含所有基金名称的ModelAndView对象，路径为"/fund/index"。
     */
    @RequestMapping("")
    public ModelAndView index() {
        Map<String, Object> map = new HashMap<>();
        List<String> names = fundTradeService.listAllNames();
        map.put("names", names);
        return new ModelAndView("/fund/index", map);
    }

    /**
     * 计算并返回交易信息。
     *
     * @param belong 需要计算的交易所属类别。
     * @return 返回一个包含交易列表和所属类别的ModelAndView对象，用于展示在"fund/trade"页面上。
     */
    @RequestMapping("/calculate/{belong}")
    public ModelAndView calculate(@PathVariable("belong") String belong) {
        BaseList<FundTradeVO> fundCalculateVOBaseList = fundTradeService.calculate(belong);
        Map<String, Object> map = new HashMap<>(2);
        map.put("tradeList", fundCalculateVOBaseList.getList());
        map.put("belong", belong);
        return new ModelAndView("fund/trade", map);
    }

    /**
     * 添加交易。
     *
     * @param bo      需要添加的交易信息，包含交易的详细信息。
     * @param request Http请求对象，用于获取请求相关的信息。
     * @return 返回一个BaseResponse对象，该对象包含了操作的结果信息。
     */
    @PostMapping("/add")
    @ResponseBody
    public BaseResponse<?> trade(@RequestBody FundTradeAddBO bo, HttpServletRequest request) {
        fundTradeService.trade(bo);
        return new BaseResponse<>();
    }

    /**
     * 统计交易。
     *
     * @param request HttpServletRequest对象，用于获取请求信息。
     * @return 返回一个BaseResponse对象，其中包含计算结果。
     */
    @PostMapping("/calculate")
    @ResponseBody
    public BaseResponse<?> calculate(HttpServletRequest request) {
        return new BaseResponse<>(fundTradeService.calculate());
    }
}
