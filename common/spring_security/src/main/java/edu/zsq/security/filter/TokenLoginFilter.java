package edu.zsq.security.filter;

import cn.hutool.core.io.IoUtil;
import com.alibaba.fastjson.JSON;
import edu.zsq.security.entity.MyUserDetails;
import edu.zsq.security.entity.UserInfoDTO;
import edu.zsq.security.security.TokenManager;
import edu.zsq.utils.exception.core.ExFactory;
import edu.zsq.utils.result.JsonResult;
import edu.zsq.utils.result.ResponseUtil;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 登录过滤器，继承UsernamePasswordAuthenticationFilter，对用户名密码进行登录校验
 * </p>
 *
 * @author 张
 */
public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final TokenManager tokenManager;
    private final RedisTemplate redisTemplate;

    public TokenLoginFilter(AuthenticationManager authenticationManager, TokenManager tokenManager, RedisTemplate redisTemplate) {
        this.authenticationManager = authenticationManager;
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
        this.setPostOnly(false);
//        设置登录是地址和提交方式
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/admin/acl/login", "POST"));
    }

    /**
     * 得到用户名密码
     *
     * @param request  请求值
     * @param response 返回值
     * @return 验证信息
     */
    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        String read = IoUtil.read(request.getInputStream(), StandardCharsets.UTF_8);
        UserInfoDTO userInfoDTO = JSON.parseObject(read, UserInfoDTO.class);
        String username = userInfoDTO.getUsername();
        String password = userInfoDTO.getPassword();
        if (StringUtils.isAnyBlank(username, password)) {
            throw ExFactory.throwSystem("系统异常，获取输入数据失败");
        }

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                username, password);
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));

//            从user中获取用户名密码 返回一个完全认证的对象，包括凭据
        return authenticationManager.authenticate(authRequest);
    }

    /**
     * 登录成功
     *
     * @param req   请求
     * @param res   返回
     * @param chain 过滤链
     * @param auth  验证信息
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
                                            Authentication auth) {
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
//        生成token
        String token = tokenManager.createToken(user.getUserInfo().getUsername());
//        登录成功  以用户名作为key 权限表作为value 放入redis中
        redisTemplate.opsForValue().set(user.getUserInfo().getUsername(), user.getPermissionValueList());

        Map<String, String> data = new HashMap<>(16);
        data.put("token", token);
        ResponseUtil.out(res, JsonResult.success(data, "登陆成功"));
    }

    /**
     * 登录失败
     *
     * @param request  请求
     * @param response 返回
     * @param e        异常
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException e) {
        ResponseUtil.out(response, JsonResult.failure(e.getMessage()));
    }
}
