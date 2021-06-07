package edu.zsq.order.common.enums;

import lombok.Getter;

import java.util.Arrays;

/**
 * @author 张
 */

public enum OrderStatusEnum {

    /**
     * 订单状态枚举
     */
    UNPAYMENT(0, "待付款"),
    DONE(1, "已完成"),
    UNSHIPPING(2, "已付款/待发货"),
    UNDELIVERY(3, "已发货/待收货"),
    ACCEPTED(4, "已确认收货"),
    CANCEL(5, "已超时关闭"),
    MANAGER_CANCEL(-1, "取消订单");

    @Getter
    private final int status;

    @Getter
    private final String desc;

    OrderStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public static OrderStatusEnum find(int status) {
        return Arrays.stream(OrderStatusEnum.values())
                .filter(orderStatusEnum -> orderStatusEnum.getStatus() == status)
                .findFirst()
                .orElse(null);
    }

}