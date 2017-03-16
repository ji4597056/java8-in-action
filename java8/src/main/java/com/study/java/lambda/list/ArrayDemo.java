package com.study.java.lambda.list;

import java.util.Arrays;
import java.util.stream.IntStream;
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
}
