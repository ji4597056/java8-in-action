package com.study.java.exercise;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import org.junit.Test;

/**
 * @author Jeffrey
 * @since 2017/03/21 16:24
 */
public class CodeWar {

    /**
     * Write a function that takes in a string of one or more words, and returns the same string,
     * but with all five or more letter words reversed (Just like the name of this . Strings
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
     * Given an array, find the int that appears an odd number of times.
     * There will always be only one integer that appears an odd number of times.
     * 心得:异或(^)相同数字^为0.
     */
    public static int findIt(int[] A) {
        return Arrays.stream(A).reduce(0, (left, right) -> left ^ right);
    }

    @Test
    public void findTest() {
        assertEquals(5, findIt(new int[]{20, 1, -1, 2, -2, 3, 3, 5, 5, 1, 2, 4, 20, 4, -1, -2, 5}));
        assertEquals(-1, findIt(new int[]{1, 1, 2, -2, 5, 2, 4, 4, -1, -2, 5}));
        assertEquals(5, findIt(new int[]{20, 1, 1, 2, 2, 3, 3, 5, 5, 4, 20, 4, 5}));
        assertEquals(10, findIt(new int[]{10}));
        assertEquals(10, findIt(new int[]{1, 1, 1, 1, 1, 1, 10, 1, 1, 1, 1}));
        assertEquals(1, findIt(new int[]{5, 4, 3, 2, 1, 5, 4, 3, 2, 10, 10}));
    }

    /**
     * An orderly trail of ants is marching across the park picnic area.
     * It looks something like this:
     * ..ant..ant.ant...ant.ant..ant.ant....ant..ant.ant.ant...ant..
     * 心得:通过replace(str, "")的size查看字符串长度,String.chars()
     */
    public static int deadAntCount(final String ants) {
        if (ants != null) {
            String str = java.util.Arrays.stream(ants.split("ant|[^ant]"))
                .collect(java.util.stream.Collectors.joining("")).trim();
            return str.length() - Math
                .min(Math.min(str.replace("a", "").length(), str.replace("n", "").length()),
                    str.replace("t", "").length());
        }
        return 0;
    }

    public static int deadAntCount1(final String ants) {
        if (ants != null) {
            String bits = ants.replaceAll("ant", "");
            return "ant".chars().map(x -> (int) bits.chars().filter(y -> y == x).count()).max()
                .getAsInt();
        }
        return 0;
    }

    @Test
    public void testDeadAntCount() {
        assertEquals(0, deadAntCount("ant ant ant ant"));
        assertEquals(0, deadAntCount(null));
        assertEquals(2, deadAntCount("ant anantt aantnt"));
        assertEquals(1, deadAntCount("ant ant .... a nt"));
    }

    /**
     * The prime numbers are not regularly spaced. For example from 2 to 3 the gap is 1. From 3 to 5
     * the gap is 2. From 7 to 11 it is 4. Between 2 and 50 we have the following pairs of 2-gaps
     * primes: 3-5, 5-7, 11-13, 17-19, 29-31, 41-43
     * 新的:orElse使用,BigInteger.isProbablePrime()判断素数
     */
    public static long[] gap(int g, long m, long n) {
        java.util.List<Long> list = java.util.stream.LongStream.rangeClosed(m, n).filter(value -> {
            if (value >= 2) {
                long k = (long) Math.sqrt(value);
                for (long i = 2; i <= k; i++) {
                    if (value % i == 0) {
                        return false;
                    }
                }
                return true;
            }
            return false;
        }).boxed().collect(java.util.stream.Collectors.toList());
        java.util.List<Long> result = list.stream().filter(aLong -> {
            if (list.indexOf(aLong) != list.size() - 1) {
                return list.get(list.indexOf(aLong) + 1) == aLong + g;
            }
            return false;
        })
            .collect(java.util.stream.Collectors.toList());
        if (result.size() == 0) {
            return null;
        } else {
            return new long[]{result.get(0), result.get(0) + g};
        }
    }

    public static long[] gap1(long g, long m, long n) {
        return LongStream.iterate(m % 2 == 0 ? m + 1 : m, l -> l + 2).limit((n - m) / 2)
            .filter(l -> BigInteger
                .valueOf(l).isProbablePrime(5) && BigInteger.valueOf(l + g).isProbablePrime(5))
            .filter(l -> LongStream.iterate(l + 2, c -> c + 2).limit((g - 2) / 2).parallel()
                .filter(c -> BigInteger.valueOf(c).isProbablePrime(5)).mapToObj(c -> false)
                .findAny().orElse(true)).mapToObj(l -> new long[]{l, l + g}).findFirst()
            .orElse(null);
    }

    /**
     * Our 3 means 7 on the planet Twisted-3-7. And 7 means 3.
     * Your task is to create a method, that can sort an array the way it would be sorted on
     * Twisted-3-7.
     * 心得:交换字符
     */
    public static Integer[] sortTwisted37(Integer[] array) {
        return java.util.Arrays.stream(array).map(String::valueOf).map(s1 ->
            s1.chars().map(c1 -> {
                if (c1 == '3') {
                    return '7';
                } else if (c1 == '7') {
                    return '3';
                }
                return (char) c1;
            }).mapToObj(v1 -> String.valueOf((char) v1))
                .collect(java.util.stream.Collectors.joining(""))
        ).map(Integer::parseInt).sorted().map(String::valueOf).map(s2 ->
            s2.chars().map(c2 -> {
                if (c2 == '7') {
                    return '3';
                } else if (c2 == '3') {
                    return '7';
                }
                return (char) c2;
            }).mapToObj(v2 -> String.valueOf((char) v2))
                .collect(java.util.stream.Collectors.joining(""))
        ).map(Integer::parseInt).toArray(Integer[]::new);
    }

    private static Integer swap37(Integer i) {
        return Integer.valueOf(i.toString()
            .replace("7", "_")
            .replace("3", "7")
            .replace("_", "3")
        );
    }

    public static Integer[] sortTwisted371(Integer[] array) {
        return Arrays.stream(array)
            .map(CodeWar::swap37)
            .sorted(Integer::compare)
            .map(CodeWar::swap37)
            .toArray(Integer[]::new);
    }

    @Test
    public void basicTests() {
        assertEquals(Arrays.toString(new Integer[]{1, 2, 7, 4, 5, 6, 3, 8, 9}),
            Arrays.toString(sortTwisted37(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9})));
        assertEquals(Arrays.toString(new Integer[]{12, 14, 13}),
            Arrays.toString(sortTwisted37(new Integer[]{12, 13, 14})));
        assertEquals(Arrays.toString(new Integer[]{2, 7, 4, 3, 9}),
            Arrays.toString(sortTwisted37(new Integer[]{9, 2, 4, 7, 3})));
    }

    @Test
    public void testGap() {
        System.out.println("Fixed Tests");
        assertEquals("[101, 103]", Arrays.toString(gap(2, 100, 110)));
        assertEquals("[103, 107]", Arrays.toString(gap(4, 100, 110)));
        assertEquals(null, gap(6, 100, 110));
        assertEquals("[359, 367]", Arrays.toString(gap(8, 300, 400)));
        assertEquals("[337, 347]", Arrays.toString(gap(10, 300, 400)));
    }

    /**
     * You are given a list/array which contains only integers (positive and negative). Your job is
     * to sum only the numbers that are the same and consecutive. The result should be one list.
     */
    public static List<Integer> sumConsecutives(List<Integer> s) {
        return null;
    }

    @Test
    public void testSumConsecutives() {
        System.out.println("Basic Tests");
        List<Integer> i = Arrays.asList(1, 4, 4, 4, 0, 4, 3, 3, 1);
        List<Integer> o = Arrays.asList(1, 12, 0, 4, 6, 1);
        System.out.println("Input: {1,4,4,4,0,4,3,3,1}");
        assertEquals(o, sumConsecutives(i));
        i = Arrays.asList(-5, -5, 7, 7, 12, 0);
        o = Arrays.asList(-10, 14, 12, 0);
        System.out.println("Input: {-5,-5,7,7,12,0}");
        assertEquals(o, sumConsecutives(i));
    }

    /**
     * test
     */
    @Test
    public void test() {
//        Arrays.stream((1456 + "").split("")).forEach(System.out::println);
//        assertEquals(4, Arrays.stream((1456 + "").split("")).count());
        int[] A = {1, 2, 3};
        String[] B = {"1", "2", "3"};
        Arrays.asList(A).forEach(System.out::println);
        Arrays.asList(B).forEach(System.out::println);
        Arrays.stream(A).boxed().forEach(System.out::println);
        System.out.println(File.separator);
    }
}
