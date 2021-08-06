package edu.zsq.utils.exception.core;

import edu.zsq.utils.exception.core.ExDefinition;
import edu.zsq.utils.exception.core.ExType;

/**
 * @author zhangsongqi
 * @date 5:42 下午 2021/3/30
 */
public interface IErrorEnum {

    /**
     * 枚举转换成异常码定义
     *
     * @param messages 占位填充
     * @return 异常码定义
     */
    default ExDefinition toDefinition(Object... messages) {
        int exTypeCode = getExType().getCode();
        StringBuilder sb = new StringBuilder().append(Math.abs(getCode()));
        if (sb.length() > 4) {
            sb.substring(sb.length() - 4);
        }
        while (sb.length() < 4) {
            sb.insert(0,0);
        }
        String code = exTypeCode + sb.toString();
        return new ExDefinition(getExType(), Integer.parseInt(code), getMsg(), messages);
    }

    /**
     * 获取错误信息
     * @return 错误信息
     */
    String getMsg();

    /**
     * 获取错误码
     * @return 错误码
     */
    Integer getCode();

    /**
     * 获取错误类型
     * @return 错误分类
     */
    ExType getExType();
}
