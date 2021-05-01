package edu.zsq.security.filter;

import edu.zsq.security.security.TokenManager;
import edu.zsq.utils.exception.ErrorCode;
import edu.zsq.utils.result.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 授权过滤器
 * </p>
 *
 * @author 张
 */
@Slf4j
public class TokenAuthenticationFilter extends BasicAuthenticationFilter {
    private final TokenManager tokenManager;
    private RedisTemplate redisTemplate;

    public TokenAuthenticationFilter(AuthenticationManager authManager, TokenManager tokenManager, RedisTemplate redisTemplate) {
        super(authManager);
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        if (!req.getRequestURI().contains("admin")) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = null;
        try {
            authentication = getAuthentication(req);
        } catch (Exception e) {
            JsonResult.failure(ErrorCode.UNDEFINED_ERROR, "获取授权失败");
        }

        if (authentication == null) {
            JsonResult.failure(ErrorCode.FORBIDDEN, "请管理员授权后再登录");
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }


    /**
     * 获取授权
     *
     * @param request 请求
     * @return 授权信息
     */
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        // 从header里获取token
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token.trim())) {
            return null;
        }

//            从token中获取用户名
        String userName = tokenManager.getUserFromToken(token);

        if (StringUtils.isEmpty(userName)) {
            return null;
        }

//          根据用户名从redis中获取权限列表
        List<String> permissionValueList = (List<String>) redisTemplate.opsForValue().get(userName);
//            Collection<GrantedAuthority> authorities = new ArrayList<>();

//            给当前用户 循环 赋予权限
//            for (String permissionValue : permissionValueList) {
//                if (StringUtils.isEmpty(permissionValue)) {
//                    continue;
//                }
//                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(permissionValue);
//                authorities.add(authority);
//            }

        if (CollectionUtils.isEmpty(permissionValueList)) {
            return null;
        }

        List<SimpleGrantedAuthority> authorities = permissionValueList.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(userName, token, authorities);
    }
}
