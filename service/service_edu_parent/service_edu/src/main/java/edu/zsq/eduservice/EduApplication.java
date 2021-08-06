package edu.zsq.eduservice;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 张
 * @EnableDiscoveryClient Nacos 注册
 * @EnableFeignClients Feign调用
 */
@SpringBootApplication
@MapperScan("edu.zsq.eduservice.mapper")
@ComponentScan("edu.zsq")
@EnableDiscoveryClient
@EnableFeignClients(value = {
        "edu.zsq.service_user_api.service"
})
public class EduApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class, args);
    }
}
