package com.study.java.lambda.list;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.Test;

/**
 * @author Jeffrey
 * @since 2017/03/16 16:16
 */
public class ArrayDemo {

    @Test
    public void test() {
        Integer[] values = new Integer[10];
        // 给数组赋值
        Arrays.parallelSetAll(values, value -> value + 1);
        Arrays.asList(values).forEach(System.out::println);
    }

    @Test
    public void test1() {
        List<String> strs = Arrays.asList("A", "BB", "CCC");
        System.out.println(strs);
        strs.stream().forEach(s -> {
            if (s.length() >= 2) {
//                strs.remove(s); //错误,不能操作原有的集合
            }
        });
        System.out.println(strs);

//        for (String str : strs){
//            if (Objects.equals("A", str)){
//                strs.remove(str);
//            }
//        }
    }

    @Test
    public void test2() {
        // 注意顺序问题,先filter后map
        Stream.of("d2", "a2", "b1", "b3", "c")
            .map(s -> {
                System.out.println("map: " + s);
                return s.toUpperCase();
            })
            .filter(s -> {
                System.out.println("filter: " + s);
                return s.startsWith("A");
            })
            .forEach(s -> System.out.println("forEach: " + s));
        System.out.println("============");
        Stream.of("d2", "a2", "b1", "b3", "c")
            .filter(s -> {
                System.out.println("filter: " + s);
                return s.startsWith("a");
            })
            .map(s -> {
                System.out.println("map: " + s);
                return s.toUpperCase();
            })
            .forEach(s -> System.out.println("forEach: " + s));
        System.out.println("============");
        Stream.of("d2", "a2", "b1", "b3", "c")
            .filter(s -> {
                System.out.println("filter:" + s);
                return true;
            })
            .sorted((s1, s2) -> {
                System.out.printf("sort: %s; %s\n", s1, s2);
                return s1.compareTo(s2);
            })
            .map(s -> {
                System.out.println("map:" + s);
                return s.toUpperCase();
            })
            .forEach(s -> System.out.println("forEach:" + s));

    }

    // 计算滑动窗口平均数
    public static double[] simpleMovingAverage(double[] values, int n) {
        double[] sums = Arrays.copyOf(values, values.length);
        Arrays.parallelPrefix(sums, Double::sum);
        int start = n - 1;
        return IntStream.range(start, sums.length).mapToDouble(i -> {
            double prefix = i == start ? 0 : sums[i - n];
            return (sums[i] - prefix) / n;
        }).toArray();
    }

    public int findMissingNumber(String sequence) {
        int missing = 0;
        String[] nums = sequence.split(" ");
        if (Arrays.stream(nums).allMatch(s -> s.matches("\\d+"))) {
            List<Integer> list = Arrays.stream(nums).map(Integer::parseInt).sorted()
                .collect(toList());
            list.forEach(System.out::println);
            for (int i = 0; i < list.size(); i++) {
                if (!Objects.equals(list.get(i) + 1, list.get(i + 1))) {
                    return list.get(i) + 1;
                }
            }

        } else {
            missing = 1;
        }
        return missing;
    }

    @Test
    public void testFindMissingNumber() {
        assertEquals("", 4, findMissingNumber("1 2 3 5"));
    }
}
