package edu.zsq.utils.exception.servicexception;

import edu.zsq.utils.exception.BaseException;
import edu.zsq.utils.exception.core.ExType;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 自定义业务异常类
 *
 * @author 张
 * @AllArgsConstructor 全部参数构造器
 * @NoArgsConstructor 无参构造器
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MyException extends BaseException {

    /**
     * 错误类型
     */
    private ExType exType;

    /**
     * 错误码
     */
    private int code;

    /**
     * 错误描述
     */
    private String desc;

    /**
     * 提示文案
     */
    private String copyright;

    public MyException() {
    }

    public MyException(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public MyException(String message, Throwable cause, ExType exType, int code, String desc) {
        super(message, cause);
        this.exType = exType;
        this.code = code;
        this.desc = desc;
    }

    public MyException(ExType exType, int code, String desc, String copyright) {
        this.exType = exType;
        this.code = code;
        this.desc = desc;
        this.copyright = copyright;
    }
}
