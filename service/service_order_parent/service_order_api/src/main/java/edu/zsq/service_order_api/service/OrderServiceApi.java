package edu.zsq.service_order_api.service;

import edu.zsq.service_order_api.entity.OrderVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author 张
 */
@FeignClient(value = "service8007-order", path = "/")
@Component
public interface OrderServiceApi {

    @GetMapping("/getOrderList")
    @ApiOperation(value = "根据订单id查询订单")
    List<OrderVO> getOrderList();

}
