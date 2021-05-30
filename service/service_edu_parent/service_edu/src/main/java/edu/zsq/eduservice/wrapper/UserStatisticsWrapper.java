package edu.zsq.eduservice.wrapper;

import com.alibaba.fastjson.JSON;
import edu.zsq.service_user_api.service.UserStatisticsApi;
import edu.zsq.utils.exception.core.ExFactory;
import edu.zsq.utils.result.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;

/**
 * @author 张
 */
@Component
@Slf4j
public class UserStatisticsWrapper {
    @Resource
    private UserStatisticsApi userStatisticsApi;


    public Integer getRegisterNumber(LocalDate day) {
        JsonResult<Integer> jsonResult;
        try {
            jsonResult = userStatisticsApi.getRegisterNumber(day);
        } catch (Exception e) {
            log.error("调用用户统计接口异常, 参数: {}", day, e);
            throw ExFactory.throwSystem("调用用户统计接口异常");
        }

        if (!jsonResult.isSuccess()) {
            log.error("调用用户统计接口失败, 参数: {}, 响应: {}", day, JSON.toJSONString(jsonResult));
            throw ExFactory.throwSystem("调用用户统计接口失败");
        }
        return jsonResult.getData();
    }
}
