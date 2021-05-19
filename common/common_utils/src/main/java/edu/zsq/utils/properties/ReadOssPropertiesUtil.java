package edu.zsq.utils.properties;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
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
public class ReadOssPropertiesUtil implements InitializingBean {

    /**
     * 用spring中的Value注解读取配置文件内容
     */

    @Value("${aliyun.oss.file.keyid}")
    private String keyId;

    @Value("${aliyun.oss.file.keysecret}")
    private String keySecret;

    @Value("${aliyun.oss.file.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.file.bucketname}")
    private String bucketName;


    /**
     * 定义公开静态常量 在方法中进行复制 方便使用
     */
    public static String END_POINT;
    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    public static String BUCKET_NAME;


    @Override
    public void afterPropertiesSet() {
        END_POINT = endpoint;
        ACCESS_KEY_ID = keyId;
        ACCESS_KEY_SECRET = keySecret;
        BUCKET_NAME = bucketName;
    }
}
