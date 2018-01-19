package com.study.java.exercise.study;

import java.util.Arrays;
import org.junit.Assert;
import org.junit.Test;

/**
 * 单词翻转。输入一个英文句子，翻转句子中单词的顺序，但单词内字符的顺序不变，句子中单词以空格符隔开。为简单起见，标点符号和普通字母一样处理。例如，输入“I am a
 * student.”，则输出“student. a am I”。
 *
 * (A^ + B^)^ = BA
 *
 * @author Jeffrey
 * @since 2018/01/16 23:08
 */
public class ReverseWordsInSentence {

    public static String reverse(String str) {
        char[] chars = str.toCharArray();
        for (int i = 0; i < str.length() / 2; i++) {
            char tmp = chars[i];
            chars[i] = chars[str.length() - i - 1];
            chars[str.length() - i - 1] = tmp;
        }
        return String.valueOf(chars);
    }

    public static String reverseSentence(String str) {
        StringBuilder builder = new StringBuilder();
        String[] strs = str.split(" ");
        Arrays.stream(strs).forEach(s -> builder.append(reverse(s) + " "));
        return reverse(builder.substring(0, builder.length() - 1));
    }

    public static String reverseSentence(String str, int size) {
        String s1 = str.substring(0, size);
        String s2 = str.substring(size);
        return reverse(reverse(s1) + reverse(s2));
    }

    @Test
    public void testReverseSentence() {
        Assert.assertEquals(reverseSentence("I am a student."), "student. a am I");
        Assert.assertEquals(reverseSentence("abcdefg", 2), "cdefgab");
    }
}
