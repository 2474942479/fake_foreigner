package edu.zsq.order.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 读取配置文件中的常量工具类
 * 集成InitializingBean接口实现方法 方便后边使用定义的属性
 * InitializingBean接口为bean提供了初始化方法的方式，
 * 它只包括afterPropertiesSet方法，凡是继承该接口的类，在初始化bean的时候都会执行该方法。
 * @author 张
 */
@Component
@PropertySource("classpath:application.yml")
@ConfigurationProperties(prefix = "tx.wx")
public class ReadPropertiesUtils implements InitializingBean {

    /**
     * 用spring中的Value注解读取配置文件内容
     */

    @Value("${appId}")
    private String appId;

    @Value("${partner}")
    private String partner;

    @Value("${partnerKey}")
    private String partnerKey;

    @Value("${notifyUrl}")
    private String notifyUrl;


    /**
     *     定义公开静态常量 在方法中进行复制 方便使用
     */
    public static String ACCESS_APP_ID;
    public static String ACCESS_PARTNER;
    public static String ACCESS_PARTNER_KEY;
    public static String ACCESS_NOTIFY_URL;


    @Override
    public void afterPropertiesSet() throws Exception {
        ACCESS_APP_ID = appId;
        ACCESS_PARTNER = partner;
        ACCESS_PARTNER_KEY = partnerKey;
        ACCESS_NOTIFY_URL = notifyUrl;
    }
}
