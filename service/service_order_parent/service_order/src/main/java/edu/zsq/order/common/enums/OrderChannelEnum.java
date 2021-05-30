package edu.zsq.order.common.enums;

import lombok.Getter;

/**
 * @author 张
 */

public enum OrderChannelEnum {

    /**
     * 下单渠道
     */
    WEB(1, "网页端"),
    APP(2, "App端"),
    ;

    OrderChannelEnum(Integer type, String description) {
        this.type = type;
        this.description = description;
    }

    @Getter
    private final Integer type;

    @Getter
    private final String description;



}
