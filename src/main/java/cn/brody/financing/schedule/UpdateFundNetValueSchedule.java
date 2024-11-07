package cn.brody.financing.schedule;

import cn.brody.financing.database.dao.FundNetValueDao;
import cn.brody.financing.service.IFundNetValueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 定时更新基金净值
 *
 * @author chenyifu6
 * @since 2024/11/07 15:18
 */
@Slf4j
@Component
public class UpdateFundNetValueSchedule {

    @Autowired
    private FundNetValueDao fundNetValueDao;
    @Autowired
    private IFundNetValueService fundNetValueService;


    @Scheduled(cron = "0 0 2 * * *")
    public void updateFundNetValue() {
        log.info("开始执行定时更新基金净值任务");
        List<String> allSavedFundCodes = fundNetValueDao.findAllSavedFundCode();
        fundNetValueService.updateTimedFundNetValue(allSavedFundCodes);
        log.info("定时更新基金净值任务执行完毕");
    }
}
