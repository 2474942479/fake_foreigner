package edu.zsq.statistics.schedule;


import edu.zsq.statistics.service.DailyService;
import edu.zsq.statistics.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/** 定时器任务
 * @author 张
 */
@Component
public class ScheduledTask {

    @Resource
    private DailyService dailyService;

    /**
     *     默认当前年 只写6位
     */
    @Scheduled(cron = "0 58,59 16 * * ?")
    public void task1(){

        dailyService.createStatisticsByDay(DateUtil.formatDate(DateUtil.addDays(new Date(),-1)));

    }



}
