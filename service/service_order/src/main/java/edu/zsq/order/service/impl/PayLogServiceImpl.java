package edu.zsq.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.wxpay.sdk.WXPayUtil;
import edu.zsq.order.entity.Order;
import edu.zsq.order.entity.PayLog;
import edu.zsq.order.mapper.PayLogMapper;
import edu.zsq.order.service.OrderService;
import edu.zsq.order.service.PayLogService;
import edu.zsq.order.utils.HttpClientUtil;
import edu.zsq.order.utils.ReadPropertiesUtils;
import edu.zsq.utils.exception.servicexception.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
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

    /**
     * 生成二维码
     *
     * @param orderNumber 订单编号
     * @return
     */
    @Override
    public Map createNative(String orderNumber) {
        try {
//          1  根据订单号查询订单信息
            QueryWrapper<Order> wrapper = new QueryWrapper<>();
            wrapper.eq("order_number", orderNumber);
            Order orderInfo = orderService.getOne(wrapper);

//           2  根据map设置生成二维码
            Map map = new HashMap(9);
            map.put("appid", ReadPropertiesUtils.ACCESS_APP_ID);
            map.put("mch_id", ReadPropertiesUtils.ACCESS_PARTNER);
            map.put("nonce_str", WXPayUtil.generateNonceStr());
//            主体：课程名称
            map.put("body", orderInfo.getCourseTitle());
//            订单编号
            map.put("out_trade_no", orderInfo.getOrderNumber());
            map.put("total_fee", orderInfo.getPayMoney().multiply(new BigDecimal("100")).longValue() + "");
//            支付地址
            map.put("spbill_create_ip", "127.0.0.1");
            map.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify\n");
//            根据价格生成二维码
            map.put("trade_type", "NATIVE");

            //3、HTTPClient来根据URL访问第三方接口并且传递参数】
            HttpClientUtil client = new HttpClientUtil("https://api.mch.weixin.qq.com/pay/unifiedorder");

            //client设置参数    转换xml格式并用商户key加密
            client.setXmlParam(WXPayUtil.generateSignedXml(map, ReadPropertiesUtils.ACCESS_PARTNER_KEY));
//            设置支持https
            client.setHttps(true);
            client.post();

//            4 得到请求返回xml格式结果
            String xml = client.getContent();
//            转换map集合
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);

//          5  封装最终返回结果
            Map m = new HashMap<>();
            m.put("orderNumber", orderNumber);
            m.put("courseId", orderInfo.getCourseId());
            m.put("payMoney", orderInfo.getPayMoney());
//            返回二维码操作状态
            m.put("result_code", resultMap.get("result_code"));
//            返回二维码地址
            m.put("code_url", resultMap.get("code_url"));
            return m;
        } catch (Exception e) {
            throw new MyException(20001, "微信收款二维码获取失败");
        }


    }

    /**
     * 调用wx接口查询扫码支付后返回的信息
     *
     * @param orderNumber
     * @return
     */
    @Override
    public Map<String, String> getPayStatus(String orderNumber) {

        try {
            //1、封装参数
            Map map = new HashMap<>(4);
            map.put("appid", ReadPropertiesUtils.ACCESS_APP_ID);
            map.put("mch_id", ReadPropertiesUtils.ACCESS_PARTNER);
            map.put("nonce_str", WXPayUtil.generateNonceStr());
            map.put("out_trade_no", orderNumber);

            //2、设置请求
            HttpClientUtil client = new HttpClientUtil("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(map, ReadPropertiesUtils.ACCESS_PARTNER_KEY));
            client.setHttps(true);
            client.post();
            //3、返回第三方的数据
            String xml = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
            //6、转成Map
            //7、返回
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(20001, "支付状态查询出错了");
        }
    }

    @Override
    public void insertPayLogAndUpdateStatus(Map<String, String> map) {

        //获取订单id
        String orderNumber = map.get("out_trade_no");
        //根据订单id查询订单信息
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_number", orderNumber);
        Order order = orderService.getOne(wrapper);

        if (order.getStatus().intValue() == 1) {
            return;
        }
        order.setStatus(1);
        orderService.updateById(order);

        //添加支付日志
        PayLog payLog = new PayLog();
        //支付订单号
        payLog.setOrderNumber(order.getOrderNumber());
        payLog.setPayTime(new Date());
        //支付类型
        payLog.setPayType(1);
        //支付金额(分)
        payLog.setPayMoney(order.getPayMoney());
        //支付流水号
        payLog.setTransactionId(map.get("transaction_id"));
        //支付状态
        payLog.setTradeState(map.get("trade_state"));

        payLog.setAttr(JSONObject.toJSONString(map));
        //插入到支付日志表
        baseMapper.insert(payLog);

    }
}
