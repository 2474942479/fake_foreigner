package edu.zsq.security.security;

import edu.zsq.utils.exception.ErrorCode;
import edu.zsq.utils.result.JsonResult;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 未授权的统一处理方式(没有权限的提示)
 * </p>
 *
 * @author 张
 */
public class UnauthorizedEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) {
        JsonResult.failure(ErrorCode.FORBIDDEN, "请管理员添加权限后重新登陆");
    }
}
