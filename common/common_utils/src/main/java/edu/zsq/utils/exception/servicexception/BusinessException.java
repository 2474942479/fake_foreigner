package edu.zsq.utils.exception.servicexception;

import edu.zsq.utils.exception.BaseException;
import edu.zsq.utils.exception.core.ExDefinition;

/**
 * @author zhangsongqi
 * @date 5:29 下午 2021/3/30
 */
public class BusinessException extends BaseException {

    public BusinessException(ExDefinition exDefinition) {
        super(exDefinition);
    }

}
