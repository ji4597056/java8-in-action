package com.study.java.exercise.study;

import org.junit.Assert;
import org.junit.Test;

/**
 * 给定两个分别由字母组成的字符串A和字符串B，字符串B的长度比字符串A短。请问，如何最快地判断字符串B中所有字母是否都在字符串A里
 *
 * 用位运算取代hashmap
 *
 * @author Jeffrey
 * @since 2018/01/17 9:48
 */
public class StringContainProblem {

    public static boolean StringContain(String s1, String s2) {
        long hash = 0L;
        for (int i = 0; i < s1.length(); i++) {
            char ch = s1.charAt(i);
            hash |= 2 << (ch - 'A');
        }
        for (int i = 0; i < s2.length(); i++) {
            char ch = s2.charAt(i);
            if ((hash & 2 << (ch - 'A')) == 0) {
                return false;
            }
        }
        return true;
    }

    @Test
    public void testStringContain() {
        String s1 = "ABCDEF";
        String s2 = "ABF";
        String s3 = "CDG";
        Assert.assertTrue(StringContain(s1, s2));
        Assert.assertFalse(StringContain(s1, s3));
    }
}
