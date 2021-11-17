package cn.brody.financing.mapper;

import cn.brody.financing.cache.MybatisRedisCache;
import cn.brody.financing.pojo.entity.FundTradeRecordEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Brody
 * @date 2021/10/25
 **/
@Mapper
@CacheNamespace(implementation = MybatisRedisCache.class, eviction = MybatisRedisCache.class)
public interface FundTradeRecordMapper extends BaseMapper<FundTradeRecordEntity> {
}