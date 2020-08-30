package edu.zsq.statistics.service;

import edu.zsq.statistics.entity.Daily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author zsq
 * @since 2020-08-27
 */
public interface DailyService extends IService<Daily> {

    /**
     * 添加数据到统计表中
     * @param day
     * @return
     */
    boolean createStatisticsByDay(String day);

    /**
     * 根据日期范围获取统计数据
     * @param beginDate
     * @param endDate
     * @return
     */
    Map<String, Object> getDataBySearch(String beginDate,String endDate);
}
