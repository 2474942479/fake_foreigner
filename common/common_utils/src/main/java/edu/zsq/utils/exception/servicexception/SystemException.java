package edu.zsq.utils.exception.servicexception;

import edu.zsq.utils.exception.BaseException;
import edu.zsq.utils.exception.core.ExDefinition;

/**
 * @author zhangsongqi
 * @date 5:30 下午 2021/3/30
 */
public class SystemException extends BaseException {
    public ExDefinition exDefinition;

    public SystemException(ExDefinition exDefinition) {
        super(exDefinition);
        this.exDefinition = exDefinition;
    }
}
