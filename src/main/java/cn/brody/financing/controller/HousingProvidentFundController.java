package cn.brody.financing.controller;

import cn.brody.financing.pojo.base.BaseResponse;
import cn.brody.financing.pojo.bo.HousingProvidentFundAddBO;
import cn.brody.financing.pojo.bo.HousingProvidentFundDelBO;
import cn.brody.financing.pojo.bo.HousingProvidentFundUpdateBO;
import cn.brody.financing.pojo.vo.HousingProvidentFundRecordVO;
import cn.brody.financing.pojo.vo.HousingProvidentFundStatisticsVO;
import cn.brody.financing.service.IHousingProvidentFundService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * HousingProvidentFundController - 公积金REST API
 *
 * @author BrodyChen
 * @since 2025/03/10 14:13
 */
@RestController
@RequestMapping("/financing/hpf")
public class HousingProvidentFundController {

    @Autowired
    private IHousingProvidentFundService housingProvidentFundService;

    /**
     * 获取公积金记录列表（分页）。
     *
     * @param page 页码
     * @param size 每页大小
     * @return 返回公积金记录列表的BaseResponse对象。
     */
    @GetMapping("/list")
    public BaseResponse<IPage<HousingProvidentFundRecordVO>> getHpfList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size) {
        IPage<HousingProvidentFundRecordVO> pageResult = housingProvidentFundService.listAll(page, size);
        return new BaseResponse<>(pageResult);
    }

    /**
     * 获取公积金统计数据。
     *
     * @param belong 所有者（可选）
     * @return 返回公积金统计数据的BaseResponse对象。
     */
    @GetMapping("/tabulate")
    public BaseResponse<HousingProvidentFundStatisticsVO> getHpfStatistics(
            @RequestParam(required = false) String belong) {
        HousingProvidentFundStatisticsVO statistics = housingProvidentFundService.tabulate();
        return new BaseResponse<>(statistics);
    }

    /**
     * 添加公积金记录。
     *
     * @param bo 公积金添加业务对象
     * @return 返回操作结果的BaseResponse对象。
     */
    @PostMapping("/add")
    public BaseResponse<Void> addHpfRecord(@RequestBody HousingProvidentFundAddBO bo) {
        housingProvidentFundService.add(bo);
        return new BaseResponse<>();
    }

    /**
     * 批量添加公积金记录。
     *
     * @param list 公积金添加业务对象列表
     * @return 返回操作结果的BaseResponse对象。
     */
    @PostMapping("/add_batch")
    public BaseResponse<Void> addHpfRecordBatch(@RequestBody List<HousingProvidentFundAddBO> list) {
        housingProvidentFundService.addBatch(list);
        return new BaseResponse<>();
    }

    /**
     * 更新公积金记录。
     *
     * @param bo 公积金更新业务对象
     * @return 返回操作结果的BaseResponse对象。
     */
    @PostMapping("/update")
    public BaseResponse<Void> updateHpfRecord(@RequestBody HousingProvidentFundUpdateBO bo) {
        housingProvidentFundService.update(bo);
        return new BaseResponse<>();
    }

    /**
     * 删除公积金记录。
     *
     * @param bo 公积金删除业务对象
     * @return 返回操作结果的BaseResponse对象。
     */
    @PostMapping("/delete")
    public BaseResponse<Void> deleteHpfRecord(@RequestBody HousingProvidentFundDelBO bo) {
        housingProvidentFundService.delete(bo);
        return new BaseResponse<>();
    }

    /**
     * 获取公积金记录详情。
     *
     * @param id 记录ID
     * @return 返回公积金记录详情的BaseResponse对象。
     */
    @GetMapping("/detail/{id}")
    public BaseResponse<HousingProvidentFundRecordVO> getHpfDetail(@PathVariable Long id) {
        // TODO: 实现获取公积金记录详情的逻辑
        return new BaseResponse<>();
    }

    /**
     * 根据月份获取公积金记录。
     *
     * @param belong 所有者
     * @param month 月份
     * @return 返回公积金记录的BaseResponse对象。
     */
    @GetMapping("/month/{belong}/{month}")
    public BaseResponse<HousingProvidentFundRecordVO> getHpfByMonth(
            @PathVariable String belong, @PathVariable String month) {
        // TODO: 实现根据月份获取公积金记录的逻辑
        return new BaseResponse<>();
    }

    /**
     * 计算公积金数据。
     *
     * @param belong 所有者
     * @return 返回操作结果的BaseResponse对象。
     */
    @PostMapping("/calculate/{belong}")
    public BaseResponse<Void> calculateHpf(@PathVariable String belong) {
        // TODO: 实现计算公积金数据的逻辑
        return new BaseResponse<>();
    }
}
