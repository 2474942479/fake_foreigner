package edu.zsq.eduservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.zsq.eduservice.entity.Daily;
import edu.zsq.eduservice.entity.vo.DailyVO;

import java.util.List;
import java.util.Map;

/**
 * @author 张
 */
public interface DailyService extends IService<Daily> {

    /**
     * 添加mock数据到统计表中
     * @param day 日期
     */
    void createStatisticsByDay(String day);

    /**
     * 根据日期范围获取统计数据
     * @param begin 开始时间
     * @param end 结束时间
     * @return 统计数据
     */
    List<DailyVO> getDataBySearch(String begin, String end);
}
