package edu.zsq.order.common.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @author 张
 */

public enum OrderTypeEnum {

    /**
     * 订单类型
     */
    NORMAL(0, "普通订单"),

    VIRTUAL(1, "虚拟订单"),

    GROUP(3, "拼团订单"),

    POINT(4, "积分兑换"),
    ;

    @Getter
    private final Integer type;

    @Getter
    private final String name;

    OrderTypeEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    public static String ofName(int status) {
        return Arrays.stream(OrderStatusEnum.values())
                .filter(orderStatusEnum -> orderStatusEnum.getStatus() == status)
                .findFirst()
                .map(OrderStatusEnum::getDesc)
                .orElse(null);
    }

}
