package edu.zsq.utils.exception;

/**
 * @author zhangsongqi
 * @date 2:06 下午 2021/3/30
 */
public enum ExType {

    /**
     * 系统异常
     */
    SYSTEM_ERROR(10),
    /**
     * 系统繁忙
     */
    SYSTEM_BUSY(11),
    /**
     * 业务异常
     */
    BUSINESS_ERROR(20),
    ;

    ExType(int code) {
        this.code = code;
    }

    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
