package com.study.java.exercise;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

    @Test
    public void testEncode() {
        assertEquals(")()())()(()()(",
            encode("Prespecialized"));
        assertEquals("))))())))", encode("   ()(   "));
    }

    /**
     * The goal of this exercise is to convert a string to a new string where each character in the
     * new string is '(' if that character appears only once in the original string, or ')' if that
     * character appears more than once in the original string. Ignore capitalization when
     * determining if a character is a duplicate.
     */
    private String encode(String word) {
        return word.toLowerCase().chars().mapToObj(ch -> String.valueOf((char) ch))
            .map(
                s -> word.toLowerCase().indexOf(s) == word.toLowerCase().lastIndexOf(s) ? "(" : ")")
            .collect(Collectors.joining());
    }

    @Test
    public void BasicTests() {
        assertEquals("Range: 01|01|18 Average: 01|38|05 Median: 01|32|34",
            stat("01|15|59, 1|47|16, 01|17|20, 1|32|34, 2|17|17"));
        assertEquals("Range: 00|31|17 Average: 02|26|18 Median: 02|22|00",
            stat("02|15|59, 2|47|16, 02|17|20, 2|32|34, 2|17|17, 2|22|00, 2|31|41"));
    }

    /**
     * @author Jeffrey
     * @since 2017/6/8 18:18
     */
    public static String stat(String strg) {
        int[] array = java.util.Arrays.stream(strg.split(", ")).mapToInt(data -> {
            String[] datas = data.split("\\|");
            return Integer.parseInt(datas[0]) * 3600 + Integer.parseInt(datas[1]) * 60 + Integer
                .parseInt(datas[2]);
        }).sorted().toArray();
        int max = array[array.length - 1];
        int min = array[0];
        int range = max - min;
        int length = array.length;
        int average = new Double(java.util.Arrays.stream(array).average().getAsDouble()).intValue();
        int median =
            length % 2 == 0 ? (array[length / 2 - 1] + array[length / 2]) / 2 : array[length / 2];
        return "Range: " + getStr(range) + " Average: " + getStr(average) + " Median: " + getStr(
            median);
    }

    private static String getStr(int num) {
        int hh = num / 3600;
        int mm = num % 3600 / 60;
        int ss = num % 3600 % 60;
        return (hh < 10 ? "0" + hh : hh) + "|" + (mm < 10 ? "0" + mm : mm) + "|" + (ss < 10 ? "0"
            + ss : ss);
    }

    @Test
    public void testValid() {
        assertEquals(true, isValid1("()"));
    }

    @Test
    public void testInvalid() {
        assertEquals(false, isValid1("[(])"));
    }

    /**
     * Write a function called validBraces that takes a string of braces, and determines if the
     * order of the braces is valid. validBraces should return true if the string is valid, and
     * false if it's invalid.
     */
    public boolean isValid(String braces) {
        java.util.Map<Character, Character> map = new java.util.HashMap<Character, Character>() {{
            put('(', ')');
            put('[', ']');
            put('{', '}');
        }};
        java.util.Stack<Character> stack = new java.util.Stack();
        for (int index = 0; index < braces.length(); index++) {
            char ch = braces.charAt(index);
            if (map.containsKey(ch)) {
                stack.push(ch);
            } else if (map.containsValue(ch)) {
                if (stack.isEmpty()) {
                    return false;
                }
                if (!java.util.Objects.equals(map.get(stack.pop()), ch)) {
                    return false;
                }
            } else {
                throw new IllegalArgumentException("invalid string!");
            }
        }
        if (stack.isEmpty()) {
            return true;
        }
        return false;
    }

    public boolean isValid1(String braces) {
        System.out.println(String.format("brances is %s", braces));
        int len = braces.length();
        for (int i = 0; i < len / 2; i++) {
            braces = braces.replace("()", "").replace("[]", "").replace("{}", "");
        }
        return braces.isEmpty();
    }

    @Test
    public void testSomeUnderscoreLowerStart() {
        String input = "the_Stealth_Warrior";
        System.out.println("input: " + input);
        assertEquals("theStealthWarrior", toCamelCase(input));
    }

    @Test
    public void testSomeDashLowerStart() {
        String input = "the-[r]tealth-Warrior";
        System.out.println("input: " + input);
        assertEquals("the[r]tealthWarrior", toCamelCase(input));
    }

    static String toCamelCase(String s) {
        Matcher matcher = Pattern.compile("[-|_](\\w)").matcher(s);
        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(buffer, matcher.group(1).toUpperCase());
        }
        return matcher.appendTail(buffer).toString();
    }

    @Test
    public void test_fractions() throws Exception {
        long[][] lst;
        lst = new long[][]{{1, 2}, {1, 3}, {1, 4}};
        assertEquals("(6,12)(4,12)(3,12)", convertFrac(lst));
    }

    public static String convertFrac(long[][] lst) {
        return null;
    }

    private static void testing(boolean actual, boolean expected) {
        assertEquals(expected, actual);
    }

    @Test
    public void testScramble() {
        System.out.println("Fixed Tests scramble");
        Long start = System.nanoTime();
        testing(scramble("rkqodlw", "world"), true);
        testing(scramble("cedewaraaossoqqyt", "codewars"), true);
        testing(scramble("katas", "steak"), false);
        testing(scramble("scriptjavx", "javascript"), false);
        testing(scramble("scriptingjava", "javascript"), true);
        testing(scramble("scriptsjava", "javascripts"), true);
        testing(scramble("javscripts", "javascript"), false);
        testing(scramble("aabbcamaomsccdd", "commas"), true);
        testing(scramble("commas", "commas"), true);
        testing(scramble("sammoc", "commas"), true);
        System.out.println(String.format("cost time: %s", System.nanoTime() - start));
    }

    /**
     * Write function scramble(str1,str2) that returns true if a portion of str1 characters can be
     * rearranged to match str2, otherwise returns false.
     */
    public static boolean scramble(String str1, String str2) {
        if (str2.length() > str1.length()) {
            return false;
        }
        for (String sub : str2.split("")) {
            if (!str1.contains(sub)) {
                return false;
            }
            str1 = str1.replaceFirst(sub, "");
        }
        return true;
    }

    public static boolean scramble1(String str1, String str2) {
        StringBuilder str = new StringBuilder(str1);
        for (int i = 0; i < str2.length(); i++) {
            if (str.indexOf("" + str2.charAt(i)) == -1) {
                return false;
            } else {
                str.setCharAt(str.indexOf("" + str2.charAt(i)), ' ');
            }
        }
        return true;
    }

    @Test
    public void testLongestSlideDown() {
        int[][] test = new int[][]{{75},
            {95, 64},
            {17, 47, 82},
            {18, 35, 87, 10},
            {20, 4, 82, 47, 65},
            {19, 1, 23, 75, 3, 34},
            {88, 2, 77, 73, 7, 63, 67},
            {99, 65, 4, 28, 6, 16, 70, 92},
            {41, 41, 26, 56, 83, 40, 80, 70, 33},
            {41, 48, 72, 33, 47, 32, 37, 16, 94, 29},
            {53, 71, 44, 65, 25, 43, 91, 52, 97, 51, 14},
            {70, 11, 33, 28, 77, 73, 17, 78, 39, 68, 17, 57},
            {91, 71, 52, 38, 17, 14, 91, 43, 58, 50, 27, 29, 48},
            {63, 66, 4, 68, 89, 53, 67, 30, 73, 16, 69, 87, 40, 31},
            {4, 62, 98, 27, 23, 9, 70, 98, 73, 93, 38, 53, 60, 4, 23},
        };
        assertEquals(1074, longestSlideDown(test));
    }

    /**
     *  /3/
     * \7\ 4
     * 2 \4\ 6
     * 8 5 \9\ 3
     */
    public static int longestSlideDown(int[][] pyramid) {
        int maxIndex = 0;
        int sum = 0;
        for (int i = 0; i < pyramid.length; i ++) {
            if (i == 0) {
                sum += pyramid[i][maxIndex];
            }else {
                int result = pyramid[i][maxIndex] > pyramid[i][maxIndex + 1]?pyramid[i][maxIndex]:pyramid[i][++maxIndex];
                sum += result;
            }
        }
        return sum;
    }

    /**
     * test
     */
    @Test
    public void test() {
//        Arrays.stream((1456 + "").split("")).forEach(System.out::println);
//        assertEquals(4, Arrays.stream((1456 + "").split("")).count());
        String input = "the-Stealth-Warrior";
        System.out.println(input.split("_|-")[0]);
    }
}
