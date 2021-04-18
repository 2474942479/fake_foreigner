package edu.zsq.cms.wrapper;

import edu.zsq.utils.result.JsonResult;
import org.springframework.stereotype.Component;
import springfox.documentation.spring.web.json.Json;

/**
 * @author 张
 */
@Component
public class OrderServiceImpl implements OrderServiceWrapper{

    /**
     * 判断用户是否购买课程
     * 用户未登录 抛异常HystrixRuntimeException:OrderService#isBuyCourse(String,String) failed and fallback failed.
     * 解决 直接返回false
     * @param userId 用户id
     * @param courseId  课程id
     * @return
     */
    @Override
    public JsonResult<Boolean> isBuyCourse(String userId, String courseId) {

        return JsonResult.failure("订单系统异常");
    }
}
