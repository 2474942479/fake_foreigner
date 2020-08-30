package edu.zsq.statistics;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author 张
 *
 * @EnableScheduling 开启定时任务
 */
@SpringBootApplication
@MapperScan("edu.zsq.statistics.mapper")
@ComponentScan("edu.zsq")
@EnableDiscoveryClient
@EnableFeignClients
@EnableScheduling
public class StaApplication {

    public static void main(String[] args) {
        SpringApplication.run(StaApplication.class,args);
    }
}
