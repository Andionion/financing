package cn.brody.financing.service;

import cn.brody.financing.pojo.bo.HousingProvidentFundAddBO;
import cn.brody.financing.pojo.bo.HousingProvidentFundDelBO;
import cn.brody.financing.pojo.bo.HousingProvidentFundUpdateBO;
import cn.brody.financing.pojo.vo.HousingProvidentFundRecordVO;
import cn.brody.financing.pojo.vo.HousingProvidentFundStatisticsVO;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * IHousingProvidentFundService
 *
 * @author BrodyChen
 * @since 2025/03/10 14:38
 */
public interface IHousingProvidentFundService {

    /**
     * 新增
     *
     * @param bo 请求参数
     */
    void add(HousingProvidentFundAddBO bo);

    /**
     * 批量新增
     *
     * @param bo
     */
    void addBatch(List<HousingProvidentFundAddBO> bo);

    /**
     * 修改
     *
     * @param bo 参数
     */
    void update(HousingProvidentFundUpdateBO bo);

    /**
     * 删除
     *
     * @param bo
     */
    void delete(HousingProvidentFundDelBO bo);

    /**
     * 获取所有记录列表（分页）
     *
     * @param page 页码
     * @param size 每页大小
     * @return 分页记录列表
     */
    IPage<HousingProvidentFundRecordVO> listAll(int page, int size);

    /**
     * 统计
     *
     * @return 统计结果
     */
    HousingProvidentFundStatisticsVO tabulate();
}
