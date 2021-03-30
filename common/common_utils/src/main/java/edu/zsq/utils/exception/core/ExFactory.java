package edu.zsq.utils.exception.core;

import edu.zsq.utils.exception.BaseException;
import edu.zsq.utils.exception.ErrorCode;
import edu.zsq.utils.exception.servicexception.BusinessException;
import edu.zsq.utils.exception.servicexception.SystemException;

import java.util.Objects;

/**
 * @author zhangsongqi
 * @date 2:32 下午 2021/3/30
 */
public class ExFactory {

    public static ExDefinition of(IErrorEnum code, Object... messages) {

        return code.toDefinition(messages);
    }

    public static BaseException throwBusiness(Object message) {
        Objects.requireNonNull(message, "请描述业务异常");
        return throwWith(ErrorCode.BUSINESS_ERROR, message);
    }

    public static BaseException throwSystem(Object message) {
        Objects.requireNonNull(message, "请描述系统异常");
        return throwWith(ErrorCode.SYSTEM_ERROR, message);
    }

    private static BaseException throwWith(IErrorEnum code, Object... messages) {
        return throwWith(of(code, messages));
    }

    private static BaseException throwWith(ExDefinition exDefinition) {
        if (exDefinition.getExType().equals(ExType.BUSINESS_ERROR)) {
            return new BusinessException(exDefinition);
        } else {
            return new SystemException(exDefinition);
        }
    }


}
