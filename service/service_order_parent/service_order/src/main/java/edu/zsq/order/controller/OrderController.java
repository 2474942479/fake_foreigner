package edu.zsq.order.controller;


import edu.zsq.order.entity.dto.OrderDTO;
import edu.zsq.order.entity.dto.OrderQueryDTO;
import edu.zsq.order.service.OrderService;
import edu.zsq.service_order_api.entity.OrderVO;
import edu.zsq.utils.page.PageData;
import edu.zsq.utils.result.JsonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author zsq
 * @since 2021-04-18
 */
@RestController
@RequestMapping("/orderService/order")
public class OrderController {

    @Resource
    private OrderService orderService;


    @PostMapping("/createOrder")
    @ApiOperation(value = "根据课程id和用户id创建订单，返回订单id")
    public JsonResult<String> createOrder(@RequestBody OrderDTO orderDTO) {
        return JsonResult.success(orderService.createOrder(orderDTO));
    }

    @PostMapping("/getOrderList")
    @ApiOperation(value = "根据订单id查询订单")
    public JsonResult<PageData<OrderVO>> getOrderList(@RequestBody OrderQueryDTO orderQueryDTO) {
        return JsonResult.success(orderService.getOrderList(orderQueryDTO));
    }

    @GetMapping("/getOrderInfo/{orderNumber}")
    @ApiOperation(value = "根据订单id查询订单")
    public JsonResult<OrderVO> getOrderInfo(@PathVariable String orderNumber) {
        return JsonResult.success(orderService.getOrderById(orderNumber));
    }

    @DeleteMapping("/removeOrder/{orderNumber}")
    @ApiOperation(value = "取消订单")
    public JsonResult<Void> removeOrder(@PathVariable String orderNumber) {
        orderService.removeOrder(orderNumber);
        return JsonResult.success();
    }


}

