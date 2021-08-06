package edu.zsq.order.controller;


import edu.zsq.order.entity.dto.vo.WxCodeVO;
import edu.zsq.order.service.PayLogService;
import edu.zsq.utils.exception.ErrorCode;
import edu.zsq.utils.exception.servicexception.MyException;
import edu.zsq.utils.result.JsonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author zsq
 * @since 2021-04-18
 */
@RestController
@RequestMapping("/orderService/payLog")
public class PayLogController {


    @Resource
    private PayLogService payLogService;

    @GetMapping("/createWxCode/{orderNumber}")
    @ApiOperation(value = "生成二维码")
    public JsonResult<WxCodeVO> createWxCode(@PathVariable String orderNumber) {
        return JsonResult.success(payLogService.createWxCode(orderNumber));
    }

    @GetMapping("/getPayStatus/{orderNumber}")
    @ApiOperation(value = "查询订单状态  支付成功便更新订单状态并添加到支付日志表中")
    public JsonResult<Void> getPayStatus(@PathVariable String orderNumber){
        payLogService.getPayStatus(orderNumber);
        return JsonResult.success();

    }

}

