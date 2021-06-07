package edu.zsq.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.wxpay.sdk.WXPayUtil;
import edu.zsq.order.common.enums.OrderStatusEnum;
import edu.zsq.order.common.enums.PaymentSourceEnum;
import edu.zsq.order.entity.Order;
import edu.zsq.order.entity.PayLog;
import edu.zsq.order.entity.dto.vo.WxCodeVO;
import edu.zsq.order.mapper.PayLogMapper;
import edu.zsq.order.service.OrderService;
import edu.zsq.order.service.PayLogService;
import edu.zsq.order.utils.HttpClientUtil;
import edu.zsq.servicebase.common.Constants;
import edu.zsq.utils.exception.ErrorCode;
import edu.zsq.utils.exception.core.ExFactory;
import edu.zsq.utils.exception.servicexception.MyException;
import edu.zsq.utils.properties.WxPayProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author zsq
 * @since 2021-04-18
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {

    @Resource
    private OrderService orderService;

    private static final String SUCCESS_VALUE = "SUCCESS";

    /**
     * 生成二维码
     *
     * @param orderNumber 订单编号
     * @return 二维码信息
     */
    @Override
    public WxCodeVO createWxCode(String orderNumber) {
        try {
            // 1  根据订单号查询订单信息
            QueryWrapper<Order> wrapper = new QueryWrapper<>();
            wrapper.eq("order_number", orderNumber);
            Order orderInfo = orderService.getOne(wrapper);

            // 2  根据map设置生成二维码
            Map<String, String> map = new HashMap<>(9);
            map.put("appid", WxPayProperties.WX_PAY_APP_ID);
            map.put("mch_id", WxPayProperties.WX_PAY_PARTNER);
            map.put("nonce_str", WXPayUtil.generateNonceStr());
            // 主体：课程名称
            map.put("body", orderInfo.getCourseTitle());
            // 订单编号
            map.put("out_trade_no", orderInfo.getOrderNumber());
            map.put("total_fee", orderInfo.getPayMoney().multiply(new BigDecimal("100")).longValue() + "");
            // 支付地址
            map.put("spbill_create_ip", "127.0.0.1");
            map.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify\n");
            // 根据价格生成二维码
            map.put("trade_type", "NATIVE");

            // 3、HTTPClient来根据URL访问第三方接口并且传递参数】
            HttpClientUtil client = new HttpClientUtil("https://api.mch.weixin.qq.com/pay/unifiedorder");
            // client设置参数    转换xml格式并用商户key加密
            client.setXmlParam(WXPayUtil.generateSignedXml(map, WxPayProperties.WX_PAY_PARTNERKEY));
            // 设置支持https
            client.setHttps(true);
            client.post();

            // 4 得到请求返回xml格式结果
            String xml = client.getContent();
            // 转换map集合
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);

            if (resultMap.isEmpty()) {
                throw ExFactory.throwBusiness("获取二维码失败");
            }
            // 5  封装最终返回结果
            return WxCodeVO.builder()
                    .orderNumber(orderInfo.getOrderNumber())
                    .courseId(orderInfo.getCourseId())
                    .payMoney(orderInfo.getPayMoney())
                    .resultCode(resultMap.get("result_code"))
                    .codeUrl(resultMap.get("code_url"))
                    .build();

        } catch (Exception e) {
            throw ExFactory.throwBusiness("微信收款二维码获取失败");
        }
    }

    /**
     * 调用wx接口查询扫码支付后返回的信息
     *
     * @param orderNumber 订单号
     */
    @Override
    public void getPayStatus(String orderNumber) {

        Order order = orderService.lambdaQuery().eq(Order::getOrderNumber, orderNumber).last(Constants.LIMIT_ONE).one();

        if (order.getPayMoney().compareTo(BigDecimal.ZERO) == 0) {
            order.setStatus(OrderStatusEnum.DONE.getStatus());
            if (!orderService.updateById(order)) {
                throw ExFactory.throwSystem("服务器异常, 修改订单信息失败");
            }
            return;
        }


        try {
            // 1 封装参数
            Map<String, String> map = new HashMap<>(4);
            map.put("appid", WxPayProperties.WX_PAY_APP_ID);
            map.put("mch_id", WxPayProperties.WX_PAY_PARTNER);
            map.put("nonce_str", WXPayUtil.generateNonceStr());
            map.put("out_trade_no", orderNumber);

            // 2 设置请求
            HttpClientUtil client = new HttpClientUtil("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(map, WxPayProperties.WX_PAY_PARTNERKEY));
            client.setHttps(true);
            client.post();
            // 3 返回第三方的数据
            String xml = client.getContent();
            Map<String, String> wxInfo = WXPayUtil.xmlToMap(xml);
            if (wxInfo.isEmpty()) {
                throw ExFactory.throwBusiness("支付出错了,请重试!");
            }

            if (!SUCCESS_VALUE.equals(wxInfo.get("trade_state"))) {
                throw ExFactory.throwWith(ErrorCode.PAYING);
            }
            // 向支付日志表中添加信息并修改订单列表的支付状态
            insertPayLogAndUpdateStatus(wxInfo);
        } catch (Exception e) {
            throw ExFactory.throwBusiness(e.getMessage());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void insertPayLogAndUpdateStatus(Map<String, String> map) {

        //根据订单id查询订单信息
        Order order = orderService.lambdaQuery()
                .eq(Order::getOrderNumber, map.get("out_trade_no"))
                .last(Constants.LIMIT_ONE)
                .one();

        if (OrderStatusEnum.DONE.getStatus() == order.getStatus()
                || OrderStatusEnum.MANAGER_CANCEL.getStatus() == order.getStatus()
                || OrderStatusEnum.CANCEL.getStatus() == order.getStatus()) {
            return;
        }

        if (OrderStatusEnum.UNPAYMENT.getStatus() != order.getStatus()) {
            throw ExFactory.throwBusiness("订单状态信息异常");
        }

        order.setStatus(OrderStatusEnum.DONE.getStatus());
        if (!orderService.updateById(order)) {
            throw ExFactory.throwSystem("服务器异常, 修改订单状态失败");
        }

        //添加支付日志
        PayLog payLog = new PayLog();
        //支付订单号
        payLog.setOrderNumber(order.getOrderNumber());
        payLog.setPayTime(LocalDateTime.now());
        //支付类型
        payLog.setPayType(PaymentSourceEnum.WX.getType());
        //支付金额(分)
        payLog.setPayMoney(order.getPayMoney());
        //支付流水号
        payLog.setTransactionId(map.get("transaction_id"));
        //支付状态
        payLog.setTradeState(map.get("trade_state"));

        payLog.setAttr(JSONObject.toJSONString(map));
        //插入到支付日志表
        if (!save(payLog)) {
            throw ExFactory.throwSystem("服务器异常, 订单支付日志添加失败");
        }

    }
}
