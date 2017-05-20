package com.study.java.exercise;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.Test;

/**
 * @author Jeffrey
 * @since 2017/03/21 16:24
 */
public class CodeWar {

    /**
     * Write a function that takes in a string of one or more words, and returns the same string,
     * but with all five or more letter words reversed (Just like the name of this Kata). Strings
     * passed in will consist of only letters and spaces. Spaces will be included only when more
     * than one word is present.
     */
    public String spinWords(String sentence) {
        return java.util.Arrays.stream(sentence.split(" ")).map(s ->
            s.length() > 4 ? new StringBuilder(s).reverse().toString() : s
        ).collect(java.util.stream.Collectors.joining(" "));
    }

    @Test
    public void testSpinWords() {
        assertEquals("emocleW", spinWords("Welcome"));
        assertEquals("Hey wollef sroirraw", spinWords("Hey fellow warriors"));
    }

    /**
     * Welcome. In this kata, you are asked to square every digit of a number.
     * For example, if we run 9119 through the function, 811181 will come out.
     */
    public int squareDigits(int n) {
        return Integer.parseInt(
            java.util.Arrays.stream((String.valueOf(n)).split(""))
                .map(s -> (int) Math.pow(Integer.parseInt(s), 2) + "").
                collect(java.util.stream.Collectors.joining("")));
    }

    public int squareDigits1(int n) {
        String result = "";
        while (n != 0) {
            int digit = n % 10;
            result = digit * digit + result;
            n /= 10;
        }
        return Integer.parseInt(result);
    }

    public int squareDigits2(int n) {
        return Integer.parseInt(
            String.valueOf(n).chars().map(i -> Integer.parseInt(String.valueOf((char) i)))
                .mapToObj(i -> String.valueOf(i * i)).collect(
                Collectors.joining("")));
    }

    @Test
    public void testSquareDigits() {
        assertEquals(811181, squareDigits(9119));
        IntStream.range(1, 50000).forEach(value -> System.out.println(squareDigits(value)));
    }


    /**
     * test
     */
    @Test
    public void test() {
        Arrays.stream((1456 + "").split("")).forEach(System.out::println);
        assertEquals(4, Arrays.stream((1456 + "").split("")).count());
    }
}
