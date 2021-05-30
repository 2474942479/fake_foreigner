package edu.zsq.order.common.enums;

import lombok.Getter;

/**
 * @author 张
 */

public enum PaymentSourceEnum {


    /**
     * 支付来源
     */
    ZFB(1, "支付宝"),
    WX(2, "微信"),
    ;

    PaymentSourceEnum(Integer type, String description) {
        this.type = type;
        this.description = description;
    }

    @Getter
    private final Integer type;

    @Getter
    private final String description;
}
