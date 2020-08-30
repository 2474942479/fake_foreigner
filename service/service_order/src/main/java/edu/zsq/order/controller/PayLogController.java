package edu.zsq.order.controller;


import edu.zsq.order.service.PayLogService;
import edu.zsq.utils.exception.servicexception.MyException;
import edu.zsq.utils.result.MyResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author zsq
 * @since 2020-08-25
 */
@RestController
@RequestMapping("/orderService/payLog")
public class PayLogController {


    @Autowired
    private PayLogService payLogService;

    /**
     * 生成二维码
     * @return
     */
    @GetMapping("/createNative/{orderNumber}")
    public MyResultUtils createNative(@PathVariable String orderNumber) {
        Map map = payLogService.createNative(orderNumber);
        return MyResultUtils.ok().data(map);
    }


    /**
     * 查询订单状态  支付成功便添加到支付日志表中
     * @param orderNumber   根据订单号查询
     * @return
     */
    @GetMapping("/getPayStatus/{orderNumber}")
    public MyResultUtils getPayStatus(@PathVariable String orderNumber){
//        调用wx接口查询扫码支付后返回的信息
        Map<String,String> map = payLogService.getPayStatus(orderNumber);
        System.out.println(map);
        if (map == null){
            throw new MyException(20001,"支付出错了！请重试");
        }
        if ("SUCCESS".equals(map.get("trade_state"))){
//            向支付日志表中添加信息并修改订单列表的支付状态
            payLogService.insertPayLogAndUpdateStatus(map);
            return MyResultUtils.ok().message("支付成功！");
        }
        return MyResultUtils.error().code(25000).message("支付中！");
    }

}

