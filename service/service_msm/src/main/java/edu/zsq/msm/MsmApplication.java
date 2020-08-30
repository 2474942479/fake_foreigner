package edu.zsq.msm;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 张
 * 取消数据源自动配置
 */
@EnableDiscoveryClient
@ComponentScan("edu.zsq")
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class MsmApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsmApplication.class,args);
    }

}
