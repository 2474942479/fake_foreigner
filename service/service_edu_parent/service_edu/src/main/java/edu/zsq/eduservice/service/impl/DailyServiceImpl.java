package edu.zsq.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.zsq.eduservice.entity.Daily;
import edu.zsq.eduservice.entity.vo.DailyVO;
import edu.zsq.eduservice.mapper.DailyMapper;
import edu.zsq.eduservice.service.DailyService;
import edu.zsq.eduservice.wrapper.UserStatisticsWrapper;
import edu.zsq.service_user_api.service.UserStatisticsApi;
import edu.zsq.servicebase.common.Constants;
import edu.zsq.utils.exception.core.ExFactory;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 张
 */
@Service
public class DailyServiceImpl extends ServiceImpl<DailyMapper, Daily> implements DailyService {

    @Resource
    private UserStatisticsWrapper userStatisticsWrapper;

    @Override
    public void createStatisticsByDay(LocalDate day) {

        if (day == null) {
            throw ExFactory.throwBusiness("生成日期不能为空");
        }

        Integer registerNumber = userStatisticsWrapper.getRegisterNumber(day);

        // 统计日期
        Daily daily = new Daily();
        daily.setDateCalculated(day);
        daily.setRegisterNum(registerNumber);
        daily.setCourseNum(RandomUtils.nextInt(0, 10));
        daily.setLoginNum(RandomUtils.nextInt(0, 1000));
        daily.setVideoViewNum(RandomUtils.nextInt(0, 500));
        QueryWrapper<Daily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated", day);

        if (!saveOrUpdate(daily, wrapper)) {
            throw ExFactory.throwBusiness("系统异常, 操作失败");
        }
    }

    @Override
    public List<DailyVO> getDataBySearch(String begin, String end) {

        return lambdaQuery()
                .between(StringUtils.isNotBlank(begin), Daily::getDateCalculated, begin, end)
                .select(Daily::getCourseNum, Daily::getDateCalculated, Daily::getVideoViewNum, Daily::getRegisterNum, Daily::getLoginNum)
                .orderByAsc(Daily::getDateCalculated)
                .list()
                .stream()
                .map(this::convert2DailyVO)
                .collect(Collectors.toList());

    }

    @Override
    public DailyVO getStatisticsByDay(LocalDate day) {

        if (day == null) {
            throw ExFactory.throwBusiness("生成日期不能为空");
        }

        Daily daily = lambdaQuery()
                .eq(Daily::getDateCalculated, day)
                .last(Constants.LIMIT_ONE)
                .one();
        return convert2DailyVO(daily);
    }

    private DailyVO convert2DailyVO(Daily daily) {
        return DailyVO.builder()
                .courseNum(daily.getCourseNum())
                .dateCalculated(daily.getDateCalculated())
                .loginNum(daily.getLoginNum())
                .registerNum(daily.getRegisterNum())
                .videoViewNum(daily.getVideoViewNum())
                .build();
    }
}
