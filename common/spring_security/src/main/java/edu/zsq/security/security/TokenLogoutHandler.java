package edu.zsq.security.security;

import edu.zsq.utils.result.JsonResult;
import edu.zsq.utils.result.ResponseUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 登出业务逻辑类
 * </p>
 * @author 张
 */
public class TokenLogoutHandler implements LogoutHandler {

    private final TokenManager tokenManager;
    private final RedisTemplate<String, List<String>> redisTemplate;

    public TokenLogoutHandler(TokenManager tokenManager, RedisTemplate<String, List<String>> redisTemplate) {
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String token = request.getHeader("token");
        if (token != null) {
            tokenManager.removeToken(token);

            //清空当前用户缓存中的权限数据
            String userName = tokenManager.getUserFromToken(token);
            redisTemplate.delete(userName);
        }
        ResponseUtil.out(response, JsonResult.success());
    }
}
