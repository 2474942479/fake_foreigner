package edu.zsq.user.provider;

import edu.zsq.service_user_api.service.UserStatisticsApi;
import edu.zsq.user.service.UserService;
import edu.zsq.utils.result.JsonResult;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 张
 */
@RestController
public class UserStatisticsProvider implements UserStatisticsApi {

    @Resource
    private UserService userService;

    @Override
    public JsonResult<Integer> getRegisterNumber(String day) {
        return JsonResult.success(userService.getRegisterNumber(day));
    }
}
