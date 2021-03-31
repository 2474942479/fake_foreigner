package edu.zsq.utils.exception;

import edu.zsq.utils.exception.core.ExceptionUtils;
import edu.zsq.utils.exception.servicexception.MyException;
import edu.zsq.utils.result.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理类
 *
 * @author 张
 */
@ControllerAdvice
@Slf4j
public class GlobalException<T> {

    /**
     * 指定出现什么异常执行这个方法
     * 全局异常
     */

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public JsonResult<T> failure(Exception e) {
        log.error("系统错误", e);
        return JsonResult.failure("执行了全局异常处理");

    }

    /**
     * 特定异常
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public JsonResult<T> failure(NullPointerException e) {
        log.error("发生了空指针错误", e);
        return JsonResult.failure("发生了空指针错误");

    }


    /**
     * 自定义异常类
     */
    @ExceptionHandler(MyException.class)
    @ResponseBody
    public JsonResult<T> failure(MyException e) {
        log.error(ExceptionUtils.outMore(e));
        JsonResult<T> failure = JsonResult.failure(e.getMessage());
        failure.code(e.getCode());
        return failure;
    }

}
