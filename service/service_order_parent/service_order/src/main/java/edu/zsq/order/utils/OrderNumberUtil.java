package edu.zsq.order.utils;

import com.google.common.collect.Lists;
import edu.zsq.order.common.enums.OrderChannelEnum;
import edu.zsq.order.common.enums.OrderTypeEnum;
import edu.zsq.order.common.enums.PaymentSourceEnum;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 生成订单号工具类
 *
 * @author 张
 */
public class OrderNumberUtil {

    public static List<Integer> randomNumberList;


    public static String getOrderNumber(OrderChannelEnum orderChannel, PaymentSourceEnum paymentSource, OrderTypeEnum orderType, String userId) {

        if (randomNumberList.isEmpty()) {
            setRandomNumberList();
        }

        // 生成15位订单号  1 (订单渠道) + 1 (支付来源) + 1 (订单类型) + 4(月日) + 4 (随机数) + 4 (userId后四位)
        int index = new Random().nextInt(randomNumberList.size());
        Integer randomNumber = randomNumberList.get(index);
        randomNumberList.remove(index);

        return String.valueOf(orderChannel.getType()) +
                paymentSource.getType() +
                orderType.getType() +
                LocalDate.now().getMonth() +
                LocalDate.now().getDayOfMonth() +
                randomNumber +
                userId;
    }

    private static void setRandomNumberList() {
        ThreadLocalRandom current = ThreadLocalRandom.current();
        Set<Integer> hashSet = new HashSet<>();
        while (hashSet.size() < 1000) {
            hashSet.add(current.nextInt(1000, 10000));
        }
        randomNumberList = new CopyOnWriteArrayList<>(hashSet);
    }

}
