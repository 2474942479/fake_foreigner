package edu.zsq.security.security;

import edu.zsq.utils.exception.ErrorCode;
import edu.zsq.utils.result.JsonResult;
import edu.zsq.utils.result.ResponseUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 未授权的统一处理方式(没有权限的提示)
 * </p>
 *
 * @author 张
 */
@Component
public class UnAuthorizedEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) {
        ResponseUtil.out(response, JsonResult.failure(ErrorCode.FORBIDDEN, authException.getMessage()));
    }
}
