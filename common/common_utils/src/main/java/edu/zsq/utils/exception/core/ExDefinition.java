package edu.zsq.utils.exception.core;

import lombok.Data;
import org.slf4j.helpers.MessageFormatter;

/**
 * @author zhangsongqi
 * @date 5:42 下午 2021/3/30
 */
@Data
public class ExDefinition {

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

    public ExDefinition() {

    }


    public ExDefinition(ExType exType, int code, String descFormat, Object... messages) {
        this.exType = exType;
        this.code = code;
        if (messages != null && messages.length > 0) {
            this.desc = MessageFormatter.arrayFormat(descFormat, messages).getMessage();
        } else {
            this.desc = descFormat;
        }
    }
}
