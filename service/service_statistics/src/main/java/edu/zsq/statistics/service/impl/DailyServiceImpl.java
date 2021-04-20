package edu.zsq.statistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.zsq.statistics.entity.Daily;
import edu.zsq.statistics.feign.UserServer;
import edu.zsq.statistics.mapper.DailyMapper;
import edu.zsq.statistics.service.DailyService;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author zsq
 * @since 2020-08-27
 */
@Service
public class DailyServiceImpl extends ServiceImpl<DailyMapper, Daily> implements DailyService {

    @Resource
    private UserServer userServer;

    /**
     * 添加数据到统计表中
     *
     * @param day
     * @return
     */
    @Override
    public boolean createStatisticsByDay(String day) {
        Integer registerNumber = userServer.getRegisterNumber(day);
        Daily daily = new Daily();
//        统计日期
        daily.setDateCalculated(day);
        daily.setRegisterNum(registerNumber);
        daily.setCourseNum(RandomUtils.nextInt(0, 100));
        daily.setLoginNum(RandomUtils.nextInt(0, 1000));
        daily.setVideoViewNum(RandomUtils.nextInt(0, 1000));
        QueryWrapper<Daily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated", day);
        boolean saveOrUpdate = this.saveOrUpdate(daily, wrapper);
        return saveOrUpdate;

    }

    @Override
    public Map<String, Object> getDataBySearch(String beginDate,String endDate) {
        QueryWrapper<Daily> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("date_calculated");
        if ( beginDate != null && !"".equals(beginDate) ) {
            wrapper.between("date_calculated", beginDate, endDate);
            wrapper.select("course_num","date_calculated","video_view_num","register_num","login_num");
        }

        List<Daily> dailies = baseMapper.selectList(wrapper);

        ArrayList<String> dateData = new ArrayList<>();
        ArrayList<Integer> registerData = new ArrayList<>();
        ArrayList<Integer> loginData = new ArrayList<>();
        ArrayList<Integer> videoData = new ArrayList<>();
        ArrayList<Integer> courseData = new ArrayList<>();
        for (Daily daily : dailies) {
            dateData.add(daily.getDateCalculated());
            registerData.add(daily.getRegisterNum());
            loginData.add(daily.getLoginNum());
            videoData.add(daily.getVideoViewNum());
            courseData.add(daily.getCourseNum());
        }

        Map<String, Object> map = new HashMap<>(5);
        map.put("xAxisData", dateData);
        map.put("registerData", registerData);
        map.put("loginData", loginData);
        map.put("videoData", videoData);
        map.put("courseData", courseData);
        return map;
    }
}
