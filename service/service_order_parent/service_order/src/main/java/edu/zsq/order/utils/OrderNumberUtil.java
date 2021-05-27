package edu.zsq.order.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 生成订单号工具类
 * @author 张
 */
public class OrderNumberUtil {

    public static String getOrderNumber(){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = sdf.format(new Date());
        StringBuilder result = new StringBuilder();
        Random random = new Random();
        for (int i = 0 ;i< 4 ;i++){

            result.append(random.nextInt(10));
        }

        return date + result;
    }
}
