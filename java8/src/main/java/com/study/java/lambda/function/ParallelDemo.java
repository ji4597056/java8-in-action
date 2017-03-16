package com.study.java.lambda.function;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jeffrey
 * @since 2017/03/16 11:22
 */
public class ParallelDemo {

    @Test
    public void test() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        //并行流
        long sum = list.parallelStream().filter(integer -> integer.intValue() > 3).count();
        Assert.assertEquals(2L, sum);
    }

    @Test
    public void test1() {
        testTime(10);
        testTime(100);
        testTime(10000);
        testTime(10000000);
    }

    @Test
    public void test2() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            list.add(i);
        }
        System.out.println(list.parallelStream().mapToInt(i -> i).sum());
    }

   private void testTime(int number) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            list.add(i);
        }
        List<Integer> newList;
        Long start = System.currentTimeMillis();
        newList = list.parallelStream().filter(integer -> integer % 2 == 0)
            .map(integer -> integer * 2).collect(toList());
        System.out.println(
            "并行,result:" + newList.size() + " ,time:" + (System.currentTimeMillis() - start));
        start = System.currentTimeMillis();
        newList = list.stream().filter(integer -> integer % 2 == 0).map(integer -> integer * 2)
            .collect(toList());
        System.out.println(
            "串行,result:" + newList.size() + " ,time:" + (System.currentTimeMillis() - start));
    }
}
