package com.study.java.lambda.list;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.summarizingInt;
import static java.util.stream.Collectors.toList;

import com.sun.org.apache.xpath.internal.SourceTree;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Objects;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jeffrey
 * @since 2017/03/14 16:22
 */
public class ListDemo {

    @Test
    public void test() {
        List<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");
        list.add("C");
        // 惰性求值并没有输出结果
        list.stream().filter(str -> {
            System.out.println("惰性:" + str);
            return Objects.equals("A", str);
        });
        // 及早求值会有输出结果
        long count = list.stream().filter(str -> {
            System.out.println("及早:" + str);
            return Objects.equals("A", str);
        }).count();
        System.out.println(count); // 1
        System.out.println(list); // [A, B, C]
        // 打印
        List<String> newList = list.stream().filter(str -> {
            return Objects.equals("A", str);
        }).collect(Collectors.toList());
        System.out.println(newList); // [A]
        System.out.println(list); // [A, B, C]

        list.addAll(asList("C", "D"));
        TreeSet<String> set = list.stream().collect(Collectors.toCollection(TreeSet::new));
        System.out.println(set);
    }

    // 收集器collect

    @Test
    public void test1() {
        List<Object> list = asList(1, 2, 3, "4", "5");

        list.stream().forEach(System.out::println);

        // 分类(2类,true,false)
        list.stream()
            .collect(Collectors.partitioningBy(o -> o instanceof Integer ? true : false))
            .forEach((key, value) -> System.out.println("key:" + key + ",value:" + value));
    }

    @Test
    public void test2() {
        // 分类
        List<Integer> list = asList(1, 2, 3, 4, 5, 6, 1, 2, 3);
        list.stream().collect(groupingBy(integer -> integer.intValue()))
            .forEach((integer, integers) -> System.out
                .println("key:" + integer + ",value:" + integers));
        // 分类计数
        list.stream()
            .collect(groupingBy(integer -> integer.intValue(), Collectors.counting()))
            .forEach((integer, num) -> System.out.println("key:" + integer + ",value:" + num));
    }

    @Test
    public void test3() {
        // 遍历(转换为字符串)
        List<String> list = asList("A", "B", "C");
        String str = list.stream().collect(Collectors.joining(",", "[", "]"));
        Assert.assertEquals("[A,B,C]", str);
    }

    @Test
    public void test4() {
        List<Integer> list = asList(1, 2, 3, 4, 5, 6, 1, 2, 3);
        // 分类再进行map操作
        list.stream()
            .collect(groupingBy(num -> num.intValue(), mapping(num -> num + 10, toList())))
            .forEach((num, result) -> System.out.println("num:" + num + ",result:" + result));
    }

    @Test
    public void test5() {
        List<Integer> list = asList(1, 2, 3, 4, 5, 6, 1, 2, 3);
        // 统计
        IntSummaryStatistics summary = list.stream().collect(summarizingInt(Integer::intValue));
        String str = String.format("average:%f,count:%d,max:%d,min:%d,sum:%d", summary.getAverage(),
            summary.getCount(), summary.getMax(), summary.getMin(), summary.getSum());
        System.out.println(str);
    }

    @Test
    public void test6() {
        asList(asList("a", asList("b", asList("c", "d")), "e")).stream()
            .flatMap(o -> o instanceof List ? o.stream() : Stream.of(o))
            .forEach(System.out::println);
    }

    @Test
    public void test7() {
        asList(asList("a", "b"), asList("c", "d")).stream()
            .flatMap(o -> o instanceof List ? o.stream() : Stream.of(o))
            .forEach(System.out::println);
    }
}
