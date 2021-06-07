package edu.zsq.order.utils;

import edu.zsq.order.common.enums.OrderChannelEnum;
import edu.zsq.order.common.enums.OrderTypeEnum;
import edu.zsq.order.common.enums.PaymentSourceEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 生成订单号工具类
 *
 * @author 张
 */
public class OrderNumberUtil {

    public static List<Integer> randomNumberList;


    private static void setRandomNumberList() {
        ThreadLocalRandom current = ThreadLocalRandom.current();
        Set<Integer> hashSet = new HashSet<>();
        while (hashSet.size() < 1000) {
            hashSet.add(current.nextInt(1000, 10000));
        }
        randomNumberList = new CopyOnWriteArrayList<>(hashSet);
    }

    public static String getOrderNumber(OrderChannelEnum orderChannel, OrderTypeEnum orderType, String userId) {

        if (randomNumberList == null || randomNumberList.isEmpty()) {
            setRandomNumberList();
        }

        // 生成 16 - 18位订单号  1 (订单渠道) + 1 (订单类型) + 2-4(月日) + 4 (时间戳)  + 4 (随机数) + 4 (userId后四位)
        int index = new Random().nextInt(randomNumberList.size());
        Integer randomNumber = randomNumberList.get(index);
        randomNumberList.remove(index);

        String timeStamp = String.valueOf(LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli());
        return String.valueOf(orderChannel.getType()) +
                orderType.getType() +
                LocalDate.now().getMonthValue() +
                LocalDate.now().getDayOfMonth() +
                timeStamp.substring(timeStamp.length() - 4) +
                randomNumber +
                userId.substring(userId.length() - 4);
    }

}
