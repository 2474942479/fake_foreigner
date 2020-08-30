package edu.zsq.user.uitls;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author 张
 */
@Component
@PropertySource("classpath:application.yml")
@ConfigurationProperties(prefix = "wx.open")
public class ConstantWxUtil implements InitializingBean {

    @Value("${app_id}")
    private String appId;

    @Value("${app_secret}")
    private String appSecret;

    @Value("${redirect_url}")
    private String redirectUrl;

    public static String WX_OPEN_APP_ID;
    public static String WX_OPEN_APP_SECRET;
    public static String WX_OPEN_REDIRECT_URL;

    @Override
    public void afterPropertiesSet() throws Exception {
        WX_OPEN_APP_ID = appId;
        WX_OPEN_APP_SECRET = appSecret;
        WX_OPEN_REDIRECT_URL = redirectUrl;
    }
}
