package edu.zsq.cms.wrapper;

import edu.zsq.utils.result.JsonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author 张
 */
@Component
@FeignClient(name = "service8007-order",fallback = OrderServiceImpl.class)
public interface OrderServiceWrapper {

    /**
     * 判断用户是否购买课程
     * @param userId 用户id
     * @param courseId  课程id
     * @return
     */
    @GetMapping("/orderService/order/isBuyCourse/{userId}/{courseId}")
    JsonResult<Boolean> isBuyCourse(@PathVariable("userId") String userId, @PathVariable("courseId") String courseId);
}
