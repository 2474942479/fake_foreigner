package edu.zsq.servicebase;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * @author zhangsongqi
 * @date 10:58 上午 2021/4/20
 */
@Configuration
@EnableSwagger2WebMvc
public class Knife4jConfiguration {

    @Bean
    public Docket webApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("online_edu")
                .apiInfo(webApiInfo())
                .select()
                //这里指定Controller扫描包路径
//                        .apis(RequestHandlerSelectors.basePackage("com.github.xiaoymin.knife4j.controller"))
//                        .paths(Predicates.not(PathSelectors.regex("/admin/.*")))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo webApiInfo() {
        return new ApiInfoBuilder()
                .title("假老外外语学习平台API文档")
                .description("本文档描述了假老外外语学习平台微服务接口定义")
                .version("1.0")
                .contact(new Contact("张淞淇", "http://baidu.com", "2474942479@qq.com"))
                .build();
    }

}