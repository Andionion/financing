package cn.brody.financing.schedule;

import cn.brody.financing.constant.AkToolConstant;
import cn.brody.financing.database.dao.TradeDateHistDao;
import cn.brody.financing.database.entity.TradeDateHistEntity;
import cn.brody.financing.pojo.aktool.AktoolTradeDayVO;
import cn.brody.financing.utils.HttpUtils;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
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
        // 如果最新交易日为空或者最新交易日+1天在当前时间之前，说明需要更新了
        if (null == lastTradeDateEntity || lastTradeDateEntity.getTradeDay().isBefore(LocalDate.now())) {
            log.info("开始更新交易日信息");
            // 如果数据库中没有交易日数据，或者当前日期大于数据库中的交易日，则进行数据更新操作
            String response = HttpUtils.get(AkToolConstant.getTradeDateHistSinaUrl());
            log.info("请求交易日数据，响应：{}", response);
            List<AktoolTradeDayVO> aktoolTradeDayList = JSONUtil.toList(response, AktoolTradeDayVO.class);
            if (CollectionUtil.isNotEmpty(aktoolTradeDayList)) {
                List<TradeDateHistEntity> tradeDateHistEntities = aktoolTradeDayList.stream()
                        .map(TradeDateHistEntity::new)
                        .collect(Collectors.toList());
                tradeDateHistDao.saveBatch(tradeDateHistEntities);
            }
        }
    }
}
