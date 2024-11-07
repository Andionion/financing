package cn.brody.financing.schedule;

import cn.brody.financing.constant.AkToolConstant;
import cn.brody.financing.database.dao.TradeDateHistDao;
import cn.brody.financing.database.entity.TradeDateHistEntity;
import cn.brody.financing.utils.HttpUtils;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

/**
 * UpdateTradeDaySchedule
 *
 * @author chenyifu6
 * @since 2024/11/07 15:18
 */
@Slf4j
@Component
public class UpdateTradeDaySchedule {

    @Autowired
    private TradeDateHistDao tradeDateHistDao;

    @PostConstruct
    public void init() {
        updateTradeDay();
    }

    @Scheduled(cron = "0 0 2 1 1 *")
    public void updateTradeDay() {
        TradeDateHistEntity lastTradeDateEntity = tradeDateHistDao.getLastTradeDate();
        if (null == lastTradeDateEntity || DateUtil.parse(DateUtil.now()).isAfter(DateUtil.parse(lastTradeDateEntity.getTradeDate(), DatePattern.PURE_DATE_FORMAT))) {
            log.info("开始更新交易日信息");
            // 如果数据库中没有交易日数据，或者当前日期大于数据库中的交易日，则进行数据更新操作
            String response = HttpUtils.get(AkToolConstant.getTradeDateHistSinaUrl());
            log.info("请求交易日数据，响应：{}", response);
            List<TradeDateHistEntity> tradeDateHistEntities = JSON.parseArray(response, TradeDateHistEntity.class);
            // 响应为格式yyyy-MM-dd'T'HH:mm:ss.SSS，修改为yyyyMMdd格式
            if (CollectionUtil.isNotEmpty(tradeDateHistEntities)) {
                tradeDateHistEntities = tradeDateHistEntities.stream()
                        .map(tradeDateHistEntity -> {
                            String pureDate = DatePattern.PURE_DATE_FORMAT
                                    .format(DateUtil.parse(tradeDateHistEntity.getTradeDate(), DatePattern.UTC_SIMPLE_MS_PATTERN));
                            return new TradeDateHistEntity(pureDate);
                        })
                        .collect(Collectors.toList());
                tradeDateHistDao.clear();
                tradeDateHistDao.saveBatch(tradeDateHistEntities);
            }
        }
    }
}
