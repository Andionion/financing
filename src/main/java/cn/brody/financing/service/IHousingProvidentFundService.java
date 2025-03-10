package cn.brody.financing.service;

import cn.brody.financing.pojo.bo.HousingProvidentFundAddBO;
import cn.brody.financing.pojo.bo.HousingProvidentFundDelBO;
import cn.brody.financing.pojo.bo.HousingProvidentFundUpdateBO;
import cn.brody.financing.pojo.vo.HousingProvidentFundRecordVO;
import cn.brody.financing.pojo.vo.HousingProvidentFundStatisticsVO;

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
     * 获取所有记录列表
     *
     * @return 记录列表
     */
    List<HousingProvidentFundRecordVO> listAll();

    /**
     * 统计
     *
     * @return 统计结果
     */
    HousingProvidentFundStatisticsVO tabulate();
}
