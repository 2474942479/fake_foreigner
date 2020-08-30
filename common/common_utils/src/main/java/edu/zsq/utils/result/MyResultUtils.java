package edu.zsq.utils.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 张
 */
@Data
public class MyResultUtils {
    @ApiModelProperty(value = "是否成功")
    private Boolean success;

    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private Map<String, Object> data = new HashMap<>();

    /**
     * 私有构造方法   只可以调用 ok() 和 error()方法  不是单例
     */
    private MyResultUtils() {
    }

    public static MyResultUtils ok() {
        MyResultUtils resultUtils = new MyResultUtils();
        resultUtils.setSuccess(true);
        resultUtils.setCode(20000);
        resultUtils.setMessage("成功");
        return resultUtils;
    }

    public static MyResultUtils error() {
        MyResultUtils resultUtils = new MyResultUtils();
        resultUtils.setSuccess(false);
        resultUtils.setCode(20001);
        resultUtils.setMessage("失败");
        return resultUtils;

    }


//  链式编程   ResultUtils.ok().success(true).code(200).message("成功").data()
//  this代表当前(调用者)对象

    public MyResultUtils success(Boolean success) {
        this.setSuccess(success);
        return this;
    }

    public MyResultUtils code(Integer code) {
        this.setCode(code);
        return this;
    }

    public MyResultUtils message(String message) {
        this.setMessage(message);
        return this;
    }

    public MyResultUtils data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    public MyResultUtils data(Map<String, Object> data) {
        this.setData(data);
        return this;
    }
}
