package edu.zsq.msm.utils;


import java.text.DecimalFormat;
import java.util.*;

/**
 * 获取四位或六位随机数
 *
 * @author 张
 */
public class RandomUtil {

    private static final Random random = new Random();

    private static final DecimalFormat fourDf = new DecimalFormat("0000");
    private static final DecimalFormat sixDf = new DecimalFormat("000000");

    public static String getFourBitRandom() {
        return fourDf.format(random.nextInt(10000));
    }

    public static String getSixBitRandom() {
        return sixDf.format(random.nextInt(1000000));
    }


    /**
     * 给定数组,抽取n个数据
     * @param list
     * @param n
     * @return
     */
    public static ArrayList getRandom(List list, int n) {
        Random random = new Random();
        Map<Object, Object> hashMap = new HashMap<>();

//        生成随机数并存入hashMap
        for (int i = 0; i < list.size(); i++) {
            int number = random.nextInt(100) + 1;
            hashMap.put(number,i);
        }
//        从HashMap导入数组
        Object[] robjs = hashMap.values().toArray();

        ArrayList r = new ArrayList();

//        遍历数组并打印数据
        for (int i =0;i<n;i++){
            r.add(list.get((int)robjs[i]));
            System.out.println(list.get((int)robjs[i])+"\t");
        }
        System.out.println("\n");
        return r;
    }


}
