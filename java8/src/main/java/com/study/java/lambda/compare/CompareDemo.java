package com.study.java.lambda.compare;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Stream;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jeffrey
 * @since 2017/03/17 10:50
 */
public class CompareDemo {

    @Test
    public void test() {
        Integer[] values = new Integer[]{1, 4, 3, 3, 3, 4, 2, 3, 2, 2, 1};
        // 自然排序
        Arrays.sort(values, Integer::compare);
        Arrays.asList(values).forEach(System.out::println);

        String[] strs = new String[]{"A", "C", "a", "B"};
        Arrays.sort(strs, String::compareToIgnoreCase);
        Arrays.asList(strs).forEach(System.out::println);
        Arrays.asList(Arrays.asList(strs).stream().toArray(String[]::new))
            .forEach(System.out::println);
    }

    @Test
    public void test1() {
        // generate()和iterate()创建无线循环,配合limit()使用
//        Stream.generate(() -> "string").forEach(System.out::println); // 死循环
//        Stream.generate(Math::random).forEach(System.out::println);
        Stream.iterate(BigInteger.ZERO, bigInteger -> bigInteger.add(BigInteger.ONE)).limit(10)
            .forEach(System.out::println);
        Stream.of("string").forEach(System.out::println);
    }

    @Test
    public void test2() {
        // 排序
        Arrays.asList("1", "333", "666666", "333", "2").stream()
            .sorted(Comparator.comparing(String::length).reversed().thenComparing(String::hashCode)).forEach(System.out::println);
        Arrays.asList("1", "333", "666666", "333", "2").stream()
            .sorted(Comparator.reverseOrder()).forEach(System.out::println);
        // 错误
//        Arrays.asList("1", "333", "666666", "333", "2").stream()
//            .sorted(Comparator.comparing(s -> s.length()).reversed()).forEach(System.out::println);
    }

    @Test
    public void test3() {
        // allMatch, noneMatch 全部/none 满足条件时返回true
        Assert.assertEquals(true,
            Arrays.asList(1, 2, 3, 4, 5, 6).stream().anyMatch(value -> value.equals(5)));
    }

    @Test
    public void test4() {
        ConcurrentMap<String, Set<String>> map = new ConcurrentHashMap<>();
        map.get("null").stream().forEach(s -> System.out.println(s));
    }
}
