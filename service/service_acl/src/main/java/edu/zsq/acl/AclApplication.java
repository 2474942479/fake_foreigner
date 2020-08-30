package edu.zsq.acl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 张
 */
@SpringBootApplication
@MapperScan("edu.zsq.acl.mapper")
@ComponentScan("edu.zsq")
public class AclApplication {

    public static void main(String[] args) {
        SpringApplication.run(AclApplication.class,args);
    }

}
