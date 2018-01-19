package com.study.java.exercise.study;

import java.util.Arrays;
import org.junit.Assert;
import org.junit.Test;

/**
 * 给定一个字符串，求它的最长回文子串的长度。
 *
 * @author Jeffrey
 * @since 2018/01/18 11:10
 */
public class LongestPalindromeProblem {

    public static int longestPalindrome(String str) {
        // 插入$ #
        StringBuilder sb = new StringBuilder("$");
        str.chars().forEach(ch -> sb.append("#").append((char) ch));
        sb.append("#");
        System.out.println(sb.toString());
        /* Manacher算法
            记j = 2 * id - i，也就是说 j 是 i 关于 id 的对称点(j = id - (i - id))
            if (mx - i > P[j])
                P[i] = P[j];
            else
                P[i] = mx - i; // P[i] >= mx - i，取最小值，之后再匹配更新。
         */
        int[] p = new int[sb.length()];
        int id = 0, mx = 0;
        for (int i = 1; i < sb.length(); i++) {
            p[i] = mx > i ? Math.min(p[2 * id - i], mx - i) : 1;
            while ((i + p[i] < sb.length()) && (i - p[i] > 0) && sb.charAt(i + p[i]) == sb
                .charAt(i - p[i])) {
                p[i]++;
            }
            if (i + p[i] > mx) {
                mx = i + p[i];
                id = i;
            }
        }
        return Arrays.stream(p).max().getAsInt() - 1;
    }

    @Test
    public void testLongestPalindrome() {
        String s1 = "ABCD";
        Assert.assertEquals(longestPalindrome(s1), 1);
        String s2 = "ABCBA";
        Assert.assertEquals(longestPalindrome(s2), 5);
        String s3 = "ABCBAABCCB";
        Assert.assertEquals(longestPalindrome(s3), 6);
        String s4 = "abcbacccc";
        Assert.assertEquals(longestPalindrome(s4), 5);
    }
}
