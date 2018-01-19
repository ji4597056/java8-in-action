package com.study.java.exercise.study;

import org.junit.Assert;
import org.junit.Test;

/**
 * 输入一个由数字组成的字符串，把它转换成整数并输出
 *
 * @author Jeffrey
 * @since 2018/01/17 10:32
 */
public class StrToIntProblem {

    public static int strToInt(String str) {
        int sign = 1;
        if (str.startsWith("-")) {
            str = str.substring(1);
            sign = -1;
        }
        int sum = 0;
        for (int i = 0; i < str.length(); i++) {
            int num = Character.getNumericValue(str.charAt(i));
            int newSum = sum * 10 + sign * num;
            if ((newSum - sign * num) / 10 != sum) {
                return sign > 0 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }
            sum = newSum;
        }
        return sum;
    }

    @Test
    public void strToInt() {
        String s1 = "123456";
        String s2 = "-123456";
        Assert.assertEquals(123456, strToInt(s1));
        Assert.assertEquals(-123456, strToInt(s2));
        System.out.println(strToInt("123456789"));
        System.out.println(strToInt("-123456789"));
    }

}
