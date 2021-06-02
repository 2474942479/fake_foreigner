package edu.zsq.utils.properties;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author 张
 */
@Component
@RefreshScope
public class WxPayProperties implements InitializingBean {
    @Value("${wx.pay.appId}")
    private String appId;

    @Value("${wx.pay.partner}")
    private String partner;

    @Value("${wx.pay.partnerKey}")
    private String partnerKey;

    @Value("${wx.pay.notifyUrl}")
    private String notifyUrl;

    public static String WX_PAY_APP_ID;
    public static String WX_PAY_PARTNER;
    public static String WX_PAY_PARTNERKEY;
    public static String WX_PAY_NOTIFYURL;


    @Override
    public void afterPropertiesSet() {
        WX_PAY_APP_ID = appId;
        WX_PAY_PARTNER = partner;
        WX_PAY_PARTNERKEY = partnerKey;
        WX_PAY_NOTIFYURL = notifyUrl;
    }
}
