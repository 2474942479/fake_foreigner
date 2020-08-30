package edu.zsq.utils.exception.servicexception;

import edu.zsq.utils.exception.BaseException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 自定义业务异常类
 *
 * @author 张
 * @AllArgsConstructor 全部参数构造器
 * @NoArgsConstructor   无参构造器
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyException extends BaseException {

    private Integer status;

    private String msg;

    @Override
    public String toString() {
        return "MyNullPointerException{" +
                "message=" + msg +
                ", status=" + status +
                '}';
    }
}
