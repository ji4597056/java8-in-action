package com.study.java.exercise.study;

import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jeffrey
 * @since 2018/01/18 13:13
 */
public class ExerciseTest {

    // 在一个字符串中找到第一个只出现一次的字符。如输入abaccdeff，则输出b。
    public static String test1(String str) {
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < str.length(); i++) {
            Character ch = str.charAt(i);
            if (!map.containsKey(ch)) {
                map.put(ch, 1);
            } else {
                map.put(ch, map.get(ch) + 1);
            }
        }
        for (int i = 0; i < str.length(); i++) {
            if (map.get(str.charAt(i)) == 1) {
                return String.valueOf(str.charAt(i));
            }
        }
        return null;
    }

    @Test
    public void testTest1() {
        String str = "abaccdeff";
        Assert.assertEquals("b", test1(str));
    }

    //输入一个字符串，输出该字符串中对称的子字符串的最大长度。比如输入字符串“google”，由于该字符串里最长的对称子字符串是“goog”，因此输出4。
    public static String test2(String str) {
        StringBuilder sb = new StringBuilder("$");
        str.chars().forEach(ch -> sb.append("#").append((char) ch));
        sb.append("#");
        //
        int[] p = new int[sb.length()];
        int id = 0, mx = 0;
        for (int i = 1; i < sb.length(); i++) {
            p[i] = mx > i ? Math.min(p[2 * id - i], mx - i) : 1;
            while ((i + p[i]) < sb.length() && sb.charAt(i - p[i]) == sb.charAt(i + p[i])) {
                p[i]++;
            }
            if (i + p[i] > mx) {
                id = i;
                mx = i + p[i];
            }
        }
        //
        int maxValue = 0;
        int maxId = 0;
        for (int i = 1; i < p.length; i++) {
            if (p[i] > maxValue) {
                maxValue = p[i];
                maxId = i;
            }
        }
        return sb.toString().substring(maxId - maxValue + 1, maxId + maxValue).replace("#", "");
    }

    @Test
    public void testTest2() {
        String s0 = "abgoogle";
        Assert.assertEquals("goog", test2(s0));
        String s1 = "google";
        Assert.assertEquals("goog", test2(s1));
        String s2 = "abcbacccc";
        Assert.assertEquals("abcba", test2(s2));
    }
}
