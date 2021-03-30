package edu.zsq.utils.result;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import edu.zsq.utils.exception.ErrorCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 张
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public final class JsonResult<T> {

    /** 无data的成功响应 */
    public static final JsonResult<Void> OK = JsonResult.success();

    @ApiModelProperty(value = "是否成功")
    @JSONField
    private Boolean success;

    @ApiModelProperty(value = "返回码")
    @JSONField
    private Integer code = 0;

    @ApiModelProperty(value = "返回消息")
    @JSONField
    private String message;

    @ApiModelProperty(value = "返回数据")
    @JSONField
    private T data;



    /**
     * 私有构造方法   只可以调用 ok() 和 error()方法  不是单例
     */
    private JsonResult() {
        this.data = null;
    }

    private JsonResult(Boolean success, Integer code, String message, T data) {
        JsonResult<T> result = success();
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        result.setSuccess(success);
    }

    public static <T> JsonResult<T> success() {
        return new JsonResult<>();
    }

    public static <T> JsonResult<T> success(T data) {
        JsonResult<T> result = success();
        return result.success(Boolean.TRUE).data(data);
    }

    public static <T> JsonResult<T> failure() {
        JsonResult<T> result = new JsonResult<>();
        result.setSuccess(false);
        result.setCode(ErrorCode.BUSINESS_ERROR.getExType().getCode() + ErrorCode.BUSINESS_ERROR.getCode());
        return result;
    }

    public static <T> JsonResult<T> failure(ErrorCode errorCode, String message) {
         Integer code = errorCode.getExType().getCode() + errorCode.getCode();
        return new JsonResult<>(Boolean.FALSE, code, message, null);
    }

    public static <T> JsonResult<T> failure(ErrorCode errorCode, T data, String message) {
        Integer code = errorCode.getExType().getCode() + errorCode.getCode();
        return new JsonResult<T>(Boolean.FALSE, code, message, data);
    }

    public boolean isSuccess() {
        return this.code == 0;
    }

//  链式编程   ResultUtils.ok().success(true).code(200).message("成功").data()
//  this代表当前(调用者)对象

    public JsonResult<T> success(Boolean success) {
        this.success = success;
        return this;
    }

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
