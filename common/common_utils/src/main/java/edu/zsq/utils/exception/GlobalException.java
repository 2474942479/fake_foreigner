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
public class GlobalException {

    /**
     * 指定出现什么异常执行这个方法
     * 全局异常
     */

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public JsonResult failure(Exception e) {

        e.printStackTrace();
        return JsonResult.failure().message("执行了全局异常处理");

    }

    /**
     * 特定异常
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public JsonResult failure(NullPointerException e) {

        e.printStackTrace();
        return JsonResult.failure().message("发生了空指针错误");

    }


    /**
     * 自定义异常类
     */
    @ExceptionHandler(MyException.class)
    @ResponseBody
    public JsonResult failure(MyException e) {

        log.error(ExceptionUtils.outMore(e));
        e.printStackTrace();
        return JsonResult.failure().message(e.getMessage()).code(e.getCode());

    }

}
