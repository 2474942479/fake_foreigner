package edu.zsq.order.service;

import edu.zsq.order.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author zsq
 * @since 2020-08-25
 */
public interface PayLogService extends IService<PayLog> {

    /**
     * 生成二维码
     * @param orderNumber 订单编号
     * @return
     */
    Map createNative(String orderNumber);

    /**
     * 调用wx接口查询扫码支付后返回的信息
     * @param orderNumber
     * @return
     */
    Map<String, String> getPayStatus(String orderNumber);

    /**
     * 向支付日志表中添加信息并修改订单列表的支付状态
     * @param map
     */
    void insertPayLogAndUpdateStatus(Map<String, String> map);
}
