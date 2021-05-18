package edu.zsq.security.security;

import edu.zsq.utils.exception.ErrorCode;
import edu.zsq.utils.result.JsonResult;
import edu.zsq.utils.result.ResponseUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 张
 */
@Component
public class UnAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ResponseUtil.out(response, JsonResult.failure(ErrorCode.FORBIDDEN, accessDeniedException.getMessage()));
    }
}
