package edu.zsq.gateway.config;

/**
 * <p>
 * 处理跨域
 * CORS跨域资源共享
 * </p>
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

/**
 * @author 张
 *
 *@Configuration  开启此配置类 加上后要去掉@CrossOrign注解
 */
@Configuration
public class CorsConfig {
    @Bean
    public CorsWebFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        //1、配置跨域
        // 允许任何头
        config.addAllowedHeader("*");
        // 允许任何方法（post、get等）
        config.addAllowedMethod("*");
        // 允许任何域名使用
        config.addAllowedOrigin("*");
        //允许携带cookie访问
//        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        source.registerCorsConfiguration("/**", config);

        return new CorsWebFilter(source);
    }
}
