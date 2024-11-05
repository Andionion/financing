package cn.brody.financing.control;

import cn.brody.financing.pojo.base.BaseResponse;
import cn.brody.financing.pojo.bo.FundNetValueBO;
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

    @PostMapping("/fund/net-value/save")
    public BaseResponse<?> reset(@RequestBody FundNetValueBO bo, HttpServletRequest request) {
        fundNetValueService.saveFundNetValue(bo.getFundCode());
        return new BaseResponse<>();
    }
}
