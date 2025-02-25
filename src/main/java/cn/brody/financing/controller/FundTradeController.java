package cn.brody.financing.controller;

import cn.brody.financing.pojo.base.BaseResponse;
import cn.brody.financing.pojo.bo.FundTradeAddBO;
import cn.brody.financing.pojo.vo.FundStatisticsVO;
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
        List<String> belongs = fundTradeService.listAllBelongs();
        map.put("belongs", belongs);
        return new ModelAndView("/fund/index", map);
    }


    /**
     * 获取指定类别的交易记录。
     *
     * @param belong 需要查询的交易类别。
     * @return 返回一个包含交易列表和类别的ModelAndView对象，用于展示在"/fund/trade"页面上。
     */
    @RequestMapping("/tabulate/{belong}")
    public ModelAndView tabulate(@PathVariable("belong") String belong) {
        List<FundStatisticsVO> fundStatisticsVOList = fundTradeService.tabulate(belong);
        Map<String, Object> map = new HashMap<>(2);
        map.put("statisticsList", fundStatisticsVOList);
        map.put("belong", belong);
        return new ModelAndView("/fund/statistics", map);
    }

    /**
     * 获取基金交易列表。
     *
     * @param belong   基金所属类别。
     * @param fundCode 基金代码。
     * @return 返回一个包含基金交易列表的ModelAndView对象，该对象用于展示基金交易信息。
     */
    @RequestMapping("/{belong}/{fundCode}")
    public ModelAndView listFundTrade(@PathVariable("belong") String belong, @PathVariable("fundCode") String fundCode) {
        List<FundTradeVO> fundTradeList = fundTradeService.listFundTrade(fundCode, belong);
        Map<String, Object> map = new HashMap<>(4);
        map.put("belong", belong);
        map.put("fundCode", fundCode);
        map.put("fundName", fundTradeList.get(0).getFundName());
        map.put("fundTradeList", fundTradeList);
        return new ModelAndView("/fund/trade", map);
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
}
