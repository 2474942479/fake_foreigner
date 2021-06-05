package edu.zsq.service_order_api.service;

import edu.zsq.service_order_api.entity.OrderVO;
import edu.zsq.utils.result.JsonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author 张
 */
@FeignClient(value = "service8007-order", path = "/")
@Component
public interface OrderServiceApi {

    @GetMapping("/getOrderList")
    @ApiOperation(value = "获取所有订单列表")
    JsonResult<List<OrderVO>> getOrderList();

    @GetMapping("/orderService/order/isBuyCourse/{userId}/{courseId}")
    @ApiOperation(value = "根据订单id查询订单")
    JsonResult<Boolean> isBuyCourse(@PathVariable("userId") String userId, @PathVariable("courseId") String courseId);

}
