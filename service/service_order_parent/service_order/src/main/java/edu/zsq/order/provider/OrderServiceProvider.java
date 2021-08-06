package edu.zsq.order.provider;

import edu.zsq.order.service.OrderService;
import edu.zsq.service_order_api.entity.OrderVO;
import edu.zsq.service_order_api.service.OrderServiceApi;
import edu.zsq.utils.result.JsonResult;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 张
 */
@RestController
public class OrderServiceProvider implements OrderServiceApi {

    @Resource
    private OrderService orderService;

    @Override
    public JsonResult<List<OrderVO>> getOrderList() {
        return JsonResult.success(orderService.getAllOrder());
    }

    @Override
    public JsonResult<Boolean> isBuyCourse(String userId, String courseId) {
        return JsonResult.success(orderService.isBuyCourse(userId, courseId));
    }
}
