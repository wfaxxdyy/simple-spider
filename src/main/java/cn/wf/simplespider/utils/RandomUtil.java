package cn.wf.simplespider.utils;

import java.util.Random;

/**
 * @Author: fan.wang
 * @Date: 2020/9/25 17:02
 * @description: 获取随机数
 */
public class RandomUtil {

    /**
     * 获取[a,b]区间内的随机整数
     * @param a
     * @param b
     * @return
     */
    public static int getRandomInt(int a, int b){
        return (int) (Math.random()*(b-a+1)+a);
    }
}
