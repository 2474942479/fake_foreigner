package edu.zsq.utils.exception;

import edu.zsq.utils.exception.core.ExType;
import edu.zsq.utils.exception.core.IErrorEnum;

/**
 * @author zhangsongqi
 * @date 2:06 下午 2021/3/30
 */
public enum ErrorCode implements IErrorEnum {

    /**
     * 程序逻辑闭合，if else{xxx},switch default:xx
     */
    UNREACHABLE_ERROR(ExType.SYSTEM_ERROR, 2001, "unreachable error"),
    /**
     * 保证程序健壮性，生吞异常，不影响主流程
     */
    UNEXPECTED_ERROR(ExType.SYSTEM_ERROR, 2002, "unexpected error"),
    /**
     * 未定义异常，引用了错误码
     */
    UNDEFINED_ERROR(ExType.SYSTEM_ERROR, 2003, "undefined error {}"),
    /**
     * 程序中自定义文案系统异常
     */
    SYSTEM_ERROR(ExType.SYSTEM_ERROR, 2004, "{}"),
    /**
     * 传参JSR303校验失败
     */
    BUSINESS_ERROR(ExType.BUSINESS_ERROR, 2005, "{}"),
    /**
     * 参数错误
     */
    PARAM_ERROR(ExType.BUSINESS_ERROR, 2006, "参数错误: {}"),
    /**
     * 请先进行认证
     */
    UNAUTHORIZED(ExType.BUSINESS_ERROR, 2007, "未登入: {}"),
    /**
     * 无权查看
     */
    FORBIDDEN(ExType.BUSINESS_ERROR, 2008, "权限不足: {}"),
    /**
     * 未找到该路径
     */
    NOT_FOUND(ExType.BUSINESS_ERROR, 2009, "资源不存在: {}"),
    /**
     * 请求方式不支持
     */
    METHOD_NOT_ALLOWED(ExType.BUSINESS_ERROR, 2010, "请求方式不支持: {}"),
    /**
     * 409
     */
    CONFLICT(ExType.SYSTEM_ERROR, 2011, "请求资源状态冲突: {}"),
    /**
     * 429
     */
    TOO_MANY_REQUEST(ExType.SYSTEM_ERROR, 2012, "请求数过多: {}"),
    /**
     * 499
     */
    TIMEOUT(ExType.SYSTEM_ERROR, 2013, "请求超时: {}"),
    /**
     * 重复提交
     */
    REPEAT_REQUEST(ExType.SYSTEM_ERROR, 2014, "重复提交: {}"),
    /**
     * 503
     */
    SERVICE_UNAVAILABLE(ExType.SYSTEM_ERROR, 2015, "服务不可用: {}"),

    PAYING(ExType.SYSTEM_BUSY, 2016, "支付中"),

    GATEWAY_UNDEFINED_ERROR(ExType.SYSTEM_ERROR, 1000, "GateWay网关异常: {}")
    ;


    ErrorCode(int code, String msg) {
        this.exType = ExType.BUSINESS_ERROR;
        this.code = code;
        this.msg = msg;
    }

    ErrorCode(ExType exType, int code, String msg) {
        this.exType = exType;
        this.code = code;
        this.msg = msg;
    }

    private ExType exType;

    private int code;

    private String msg;

    @Override
    public ExType getExType() {
        return exType;
    }

    public void setExType(ExType exType) {
        this.exType = exType;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
