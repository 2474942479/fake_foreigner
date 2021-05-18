package edu.zsq.security.config;

import edu.zsq.security.filter.TokenAuthenticationFilter;
import edu.zsq.security.filter.TokenLoginFilter;
import edu.zsq.security.provider.MyAuthenticationProvider;
import edu.zsq.security.security.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * Security核心配置类
 * </p>
 *
 * @author 张
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class TokenWebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 自定义UserDetailsService
     * 自定义该接口的实现类，查询数据库 获取权限列表
     */
    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private TokenManager tokenManager;

    @Resource
    private DefaultPasswordEncoder defaultPasswordEncoder;

    @Resource
    private RedisTemplate<String, List<String>> redisTemplate;

    @Resource
    private UnAuthorizedEntryPoint unAuthorizedEntryPoint;

    @Resource
    private UnAccessDeniedHandler unAccessDeniedHandler;


    /**
     * 配置设置
     *
     * @param http http
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling()
                .authenticationEntryPoint(unAuthorizedEntryPoint)
                .accessDeniedHandler(unAccessDeniedHandler)
                .and()
                .csrf()
                .disable()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                // 退出地址
                .and()
                .logout()
                .logoutUrl("/admin/acl/index/logout")
                .addLogoutHandler(new TokenLogoutHandler(tokenManager, redisTemplate))
                .and()
                .addFilter(new TokenLoginFilter(authenticationManager(), tokenManager, redisTemplate))
                .addFilter(new TokenAuthenticationFilter(authenticationManager(), tokenManager, redisTemplate))
                .httpBasic();
    }

    /**
     * 密码处理
     *
     * @param auth auth
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(new MyAuthenticationProvider(userDetailsService, defaultPasswordEncoder));
        auth.userDetailsService(userDetailsService).passwordEncoder(defaultPasswordEncoder);
    }

    /**
     * 配置哪些请求不拦截
     *
     * @param web web
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/api/**",
                "/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**"
        );
    }
}