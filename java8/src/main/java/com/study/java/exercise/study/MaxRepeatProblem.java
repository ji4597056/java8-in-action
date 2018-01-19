package com.study.java.exercise.study;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/**
 * 求最长重复子串
 *
 * @author Jeffrey
 * @since 2018/01/19 10:49
 */
public class MaxRepeatProblem {

    public static String maxRepeat(String str) {
        List<String> suffixStrs = new ArrayList<>();
        for (int i = 0; i < str.length(); i++) {
            suffixStrs.add(str.substring(i));
        }
        suffixStrs.sort(String::compareTo);
        String maxRepeat = "";
        for (int i = 0; i < suffixStrs.size() - 1; i++) {
            String s1 = suffixStrs.get(i);
            String s2 = suffixStrs.get(i + 1);
            int len = Math.min(s1.length(), s2.length());
            String common = "";
            int j = 0;
            while (j < len && s1.charAt(j) == s2.charAt(j)) {
                common += (char) s1.charAt(j++);
            }
            if (common.length() > maxRepeat.length()) {
                maxRepeat = common;
            }
        }
        return maxRepeat;
    }

    @Test
    public void testMaxRepeat() {
        Assert.assertEquals(maxRepeat("abczzacbca"), "bc");
        Assert.assertEquals(maxRepeat("abacbzzacbbca"), "acb");
        Assert.assertEquals(maxRepeat("ttabcftrgabcd"), "abc");
        Assert.assertEquals(maxRepeat("canffcancd"), "can");
    }
}
