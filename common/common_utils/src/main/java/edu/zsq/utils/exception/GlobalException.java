package edu.zsq.utils.exception;

import edu.zsq.utils.exception.servicexception.MyException;
import edu.zsq.utils.result.MyResultUtils;
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
    public MyResultUtils error(Exception e) {

        e.printStackTrace();
        return MyResultUtils.error().message("执行了全局异常处理");

    }

    /**
     * 特定异常
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public MyResultUtils error(NullPointerException e) {

        e.printStackTrace();
        return MyResultUtils.error().message("发生了空指针错误").code(20001);

    }


    /**
     * 自定义异常类
     */
    @ExceptionHandler(MyException.class)
    @ResponseBody
    public MyResultUtils error(MyException e) {

        log.error(ExceptionUtils.outMore(e));
        e.printStackTrace();
        return MyResultUtils.error().message(e.getMsg()).code(e.getStatus());

    }

}
