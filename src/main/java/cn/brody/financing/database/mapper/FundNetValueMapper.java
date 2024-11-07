package cn.brody.financing.database.mapper;

import cn.brody.financing.database.entity.FundNetValueEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * FundNetValueMapper
 *
 * @author chenyifu6
 * @since 2024/11/05 16:45
 */
@Mapper
public interface FundNetValueMapper extends BaseMapper<FundNetValueEntity> {

    @Select("SELECT DISTINCT fund_code FROM fund_net_value")
    List<String> selectDistinctFundCodes();
}