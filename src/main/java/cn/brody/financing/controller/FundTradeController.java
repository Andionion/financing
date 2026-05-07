package cn.brody.financing.controller;

import cn.brody.financing.pojo.base.BaseResponse;
import cn.brody.financing.pojo.bo.FundTradeAddBO;
import cn.brody.financing.pojo.vo.FundStatisticsVO;
import cn.brody.financing.pojo.vo.FundTradeVO;
import cn.brody.financing.service.IFundTradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * FundTradeController - 基金交易REST API
 *
 * @author chenyifu6
 * @since 2024/11/14 15:10
 */
@RestController
@RequestMapping("/financing/fund")
public class FundTradeController {

    @Autowired
    private IFundTradeService fundTradeService;

    /**
     * 获取所有所有者列表。
     *
     * @return 返回包含所有所有者名称的BaseResponse对象。
     */
    @GetMapping("/owners")
    public BaseResponse<List<String>> getOwners() {
        List<String> belongs = fundTradeService.listAllBelongs();
        return new BaseResponse<>(belongs);
    }

    /**
     * 获取基金首页数据（所有所有者的基金列表）。
     *
     * @return 返回基金首页数据的BaseResponse对象。
     */
    @GetMapping("/index")
    public BaseResponse<List<FundTradeVO>> getFundIndex() {
        // TODO: 实现获取基金首页数据的逻辑
        return new BaseResponse<>();
    }

    /**
     * 获取指定所有者和基金代码的交易信息。
     *
     * @param belong 所有者
     * @param fundCode 基金代码
     * @return 返回基金交易信息的BaseResponse对象。
     */
    @GetMapping("/info/{belong}/{fundCode}")
    public BaseResponse<List<FundTradeVO>> getFundInfo(@PathVariable String belong, @PathVariable String fundCode) {
        List<FundTradeVO> fundTradeList = fundTradeService.listFundTrade(fundCode, belong);
        return new BaseResponse<>(fundTradeList);
    }

    /**
     * 获取指定所有者的基金交易列表。
     *
     * @param belong 所有者
     * @return 返回基金交易列表的BaseResponse对象。
     */
    @GetMapping("/list/{belong}")
    public BaseResponse<List<FundTradeVO>> getFundTradeList(@PathVariable String belong) {
        // TODO: 实现获取基金交易列表的逻辑
        return new BaseResponse<>();
    }

    /**
     * 获取指定所有者的基金统计数据。
     *
     * @param belong 所有者
     * @return 返回基金统计数据的BaseResponse对象。
     */
    @GetMapping("/tabulate/{belong}")
    public BaseResponse<List<FundStatisticsVO>> getFundStatistics(@PathVariable String belong) {
        List<FundStatisticsVO> statisticsList = fundTradeService.tabulate(belong);
        return new BaseResponse<>(statisticsList);
    }

    /**
     * 添加基金交易。
     *
     * @param bo 基金交易添加业务对象
     * @return 返回操作结果的BaseResponse对象。
     */
    @PostMapping("/add")
    public BaseResponse<Void> addFundTrade(@RequestBody FundTradeAddBO bo) {
        fundTradeService.add(bo);
        return new BaseResponse<>();
    }

    /**
     * 更新基金交易。
     *
     * @param id 交易ID
     * @param bo 基金交易添加业务对象
     * @return 返回操作结果的BaseResponse对象。
     */
    @PostMapping("/update/{id}")
    public BaseResponse<Void> updateFundTrade(@PathVariable Long id, @RequestBody FundTradeAddBO bo) {
        // TODO: 实现更新基金交易的逻辑
        return new BaseResponse<>();
    }

    /**
     * 删除基金交易。
     *
     * @param id 交易ID
     * @return 返回操作结果的BaseResponse对象。
     */
    @DeleteMapping("/delete/{id}")
    public BaseResponse<Void> deleteFundTrade(@PathVariable Long id) {
        // TODO: 实现删除基金交易的逻辑
        return new BaseResponse<>();
    }

    /**
     * 计算基金数据。
     *
     * @param belong 所有者
     * @return 返回操作结果的BaseResponse对象。
     */
    @PostMapping("/calculate/{belong}")
    public BaseResponse<Void> calculateFund(@PathVariable String belong) {
        // TODO: 实现计算基金数据的逻辑
        return new BaseResponse<>();
    }
}
