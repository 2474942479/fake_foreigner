package edu.zsq.utils.result;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import edu.zsq.utils.exception.ErrorCode;
import edu.zsq.utils.exception.core.ExDefinition;
import edu.zsq.utils.exception.core.ExFactory;
import edu.zsq.utils.exception.core.IErrorEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 张
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public final class JsonResult<T> {

    /**
     * 无data的成功响应
     */
    public static final JsonResult<Void> OK = JsonResult.success();

    @ApiModelProperty(value = "返回码")
    @JSONField
    private Integer code = 0;

    @ApiModelProperty(value = "返回消息")
    @JSONField
    private String message;

    @ApiModelProperty(value = "返回数据")
    @JSONField
    private T data;

    @ApiModelProperty(value = "返回数据")
    @JSONField
    private ExDefinition exDefinition;

    /**
     * 私有构造方法   只可以调用 ok() 和 error()方法  不是单例
     */
    private JsonResult() {
        this.data = null;
    }

    private JsonResult(ExDefinition exDefinition) {
        this.exDefinition = exDefinition;
        this.code = exDefinition.getCode();
        this.message = exDefinition.getDesc();
        this.data = null;
    }

    public static <T> JsonResult<T> success() {
        return new JsonResult<>();
    }

    public static <T> JsonResult<T> success(T data) {
        JsonResult<T> result = success();
        return result.data(data);
    }

    public static <T> JsonResult<T> failure(String message) {
        return failure(ErrorCode.BUSINESS_ERROR,message);
    }

    public static <T> JsonResult<T> failure(IErrorEnum iErrorEnum, Object... messages) {
        return new JsonResult<T>(ExFactory.of(iErrorEnum,messages));
    }

    public static <T> JsonResult<T> failure(ExDefinition exDefinition) {
        return new JsonResult<T>(exDefinition);
    }

    public static <T> JsonResult<T> failure(ExDefinition exDefinition, T data) {
        JsonResult<T> result = new JsonResult<>(exDefinition);
        result.data(data);
        return result;
    }

    public boolean isSuccess() {
        return this.code == 0;
    }

//  链式编程   ResultUtils.ok().success(true).code(200).message("成功").data()
//  this代表当前(调用者)对象

    public JsonResult<T> code(Integer code) {
        this.code = code;
        return this;
    }

    public JsonResult<T> message(String message) {
        this.message = message;
        return this;
    }

    public JsonResult<T> data(T data) {
        this.data = data;
        return this;
    }
}
