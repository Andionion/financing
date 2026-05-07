package cn.brody.financing.controller;

import cn.brody.financing.pojo.base.BaseResponse;
import cn.brody.financing.pojo.bo.GoldTradeAddBO;
import cn.brody.financing.pojo.vo.GoldStatisticsVO;
import cn.brody.financing.pojo.vo.GoldTradeVO;
import cn.brody.financing.service.IGoldTradeService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * GoldTradeController - 黄金交易REST API
 *
 * @author chenyifu6
 * @since 2025-02-25 13:55:11
 */
@RestController
@RequestMapping("/financing/gold")
public class GoldTradeController {

    @Autowired
    private IGoldTradeService goldTradeService;

    /**
     * 获取黄金交易列表（分页）。
     *
     * @param page 页码
     * @param size 每页大小
     * @return 返回黄金交易列表的BaseResponse对象。
     */
    @GetMapping("/list")
    public BaseResponse<IPage<GoldTradeVO>> getGoldList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size) {
        IPage<GoldTradeVO> pageResult = goldTradeService.listGoldTrades(page, size);
        return new BaseResponse<>(pageResult);
    }

    /**
     * 获取黄金统计数据。
     *
     * @return 返回黄金统计数据的BaseResponse对象。
     */
    @GetMapping("/tabulate")
    public BaseResponse<GoldStatisticsVO> getGoldStatistics() {
        GoldStatisticsVO statistics = goldTradeService.tabulate();
        return new BaseResponse<>(statistics);
    }

    /**
     * 添加黄金交易。
     *
     * @param bo 黄金交易添加业务对象
     * @return 返回操作结果的BaseResponse对象。
     */
    @PostMapping("/add")
    public BaseResponse<Void> addGoldTrade(@RequestBody GoldTradeAddBO bo) {
        goldTradeService.addGoldTrade(bo);
        return new BaseResponse<>();
    }

    /**
     * 更新黄金交易。
     *
     * @param id 交易ID
     * @param bo 黄金交易添加业务对象
     * @return 返回操作结果的BaseResponse对象。
     */
    @PostMapping("/update/{id}")
    public BaseResponse<Void> updateGoldTrade(@PathVariable Long id, @RequestBody GoldTradeAddBO bo) {
        // TODO: 实现更新黄金交易的逻辑
        return new BaseResponse<>();
    }

    /**
     * 删除黄金交易。
     *
     * @param id 交易ID
     * @return 返回操作结果的BaseResponse对象。
     */
    @DeleteMapping("/delete/{id}")
    public BaseResponse<Void> deleteGoldTrade(@PathVariable Long id) {
        // TODO: 实现删除黄金交易的逻辑
        return new BaseResponse<>();
    }

    /**
     * 获取黄金交易详情。
     *
     * @param id 交易ID
     * @return 返回黄金交易详情的BaseResponse对象。
     */
    @GetMapping("/info/{id}")
    public BaseResponse<GoldTradeVO> getGoldDetail(@PathVariable Long id) {
        // TODO: 实现获取黄金交易详情的逻辑
        return new BaseResponse<>();
    }

    /**
     * 计算黄金数据。
     *
     * @return 返回操作结果的BaseResponse对象。
     */
    @PostMapping("/calculate")
    public BaseResponse<Void> calculateGold() {
        // TODO: 实现计算黄金数据的逻辑
        return new BaseResponse<>();
    }
}
