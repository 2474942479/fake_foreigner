package edu.zsq.eduservice.wrapper;

import edu.zsq.service_user_api.service.UserStatisticsApi;
import edu.zsq.utils.exception.core.ExFactory;
import edu.zsq.utils.result.JsonResult;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author 张
 */
@Component
public class UserStatisticsWrapper {
    @Resource
    private UserStatisticsApi userStatisticsApi;


    public Integer getRegisterNumber(String day) {
        try {
            JsonResult<Integer> jsonResult = userStatisticsApi.getRegisterNumber(day);

            if (jsonResult.isSuccess()) {
                throw ExFactory.throwSystem("调用用户统计接口失败");
            }
            return jsonResult.getData();
        } catch (Exception e) {
            throw ExFactory.throwSystem("调用用户统计接口异常");
        }

    }
}
