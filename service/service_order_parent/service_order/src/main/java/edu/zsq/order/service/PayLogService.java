package edu.zsq.order.service;

import edu.zsq.order.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.zsq.order.entity.dto.vo.WxCodeVO;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author zsq
 * @since 2021-04-18
 */
public interface PayLogService extends IService<PayLog> {

    /**
     * 生成二维码
     *
     * @param orderNumber 订单编号
     * @return 二维码
     */
    WxCodeVO createWxCode(String orderNumber);

    /**
     * 调用wx接口查询扫码支付后返回的信息
     *
     * @param orderNumber 订单号
     */
    void getPayStatus(String orderNumber);
}
