package cn.brody.financing.controller;

import cn.brody.financing.pojo.base.BaseResponse;
import cn.brody.financing.pojo.bo.FundCodeListBO;
import cn.brody.financing.service.IFundNetValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * FundNetValueController
 *
 * @author chenyifu6
 * @since 2024/11/05 17:05
 */
@RestController
@RequestMapping("/financing")
public class FundNetValueController {

    @Qualifier("fundNetValueServiceImpl")
    @Autowired
    private IFundNetValueService fundNetValueService;

    /**
     * 保存所有基金净值。
     *
     * @param bo      包含基金代码的请求体对象。
     * @param request HTTP请求对象。
     * @return 如果成功，返回一个空的BaseResponse对象；否则抛出异常。
     */
    @PostMapping("/fund/net-value/save-all")
    public BaseResponse<?> saveAllFundNetValue(@RequestBody FundCodeListBO bo, HttpServletRequest request) {
        fundNetValueService.saveAllFundNetValue(bo.getFundCodes());
        return new BaseResponse<>();
    }

    /**
     * 更新定时基金净值。
     *
     * @param bo      包含基金代码的请求体对象。
     * @param request HTTP请求对象。
     * @return 如果更新成功，返回一个空的BaseResponse对象；否则抛出异常。
     */
    @PostMapping("/fund/net-value/update-timed")
    public BaseResponse<?> updateTimedFundNetValue(@RequestBody FundCodeListBO bo, HttpServletRequest request) {
        fundNetValueService.updateTimedFundNetValue(bo.getFundCodes());
        return new BaseResponse<>();
    }

    /**
     * 获取最新的基金净值。
     *
     * @param bo      包含请求参数的LatestFundNetValueBO对象。
     * @param request HttpServletRequest对象，用于获取请求信息。
     * @return 返回一个BaseResponse对象，其中包含了处理结果的信息。
     */
    @PostMapping("/fund/net-value/latest")
    public BaseResponse<?> latestFundNetValue(@RequestBody FundCodeListBO bo, HttpServletRequest request) {
        return new BaseResponse<>(fundNetValueService.latestFundNetValue(bo.getFundCodes()));
    }
}
