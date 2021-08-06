package edu.zsq.utils.exception;

import edu.zsq.utils.exception.core.ExDefinition;

/**
 * 自定义异常根类
 *
 * @author 张
 */
public class BaseException extends RuntimeException {

    public BaseException() {
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(ExDefinition exDefinition) {
        super(exDefinition.getDesc());
    }
}
