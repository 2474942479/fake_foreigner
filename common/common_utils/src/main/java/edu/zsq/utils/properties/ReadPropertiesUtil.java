package edu.zsq.utils.properties;

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
 *
 * @author 张
 */
@Component
@PropertySource("classpath:aliyun.yml")
@ConfigurationProperties(prefix = "aliyun.file")
public class ReadPropertiesUtil implements InitializingBean {

    /**
     * 用spring中的Value注解读取配置文件内容
     */

    @Value("${keyid}")
    private String keyId;

    @Value("${keysecret}")
    private String keySecret;


    /**
     * 定义公开静态常量 在方法中进行复制 方便使用
     */
    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;


    @Override
    public void afterPropertiesSet() throws Exception {
        ACCESS_KEY_ID = keyId;
        ACCESS_KEY_SECRET = keySecret;
    }
}
