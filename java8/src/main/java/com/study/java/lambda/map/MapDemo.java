package com.study.java.lambda.map;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jeffrey
 * @since 2017/03/14 16:53
 */
public class MapDemo {

    @Test
    public void test() {
        // stream.of(T t) 可以传入数组元素
        // 传给map的Lambda表达式接受一个String类型的参数，返回一个新的String
        List<String> collected = Stream.of("a", "b", "c")
            .map(string -> string.toUpperCase()).collect(Collectors.toList());
        Assert.assertEquals(Arrays.asList("A", "B", "C"), collected); // true

        collected = Stream.of(1, 2, 3).map(index -> index + "").collect(Collectors.toList());
        Assert.assertEquals(Arrays.asList("1", "2", "3"), collected); // true

        long count = Stream.of(collected).count();
//        Assert.assertEquals(3, count); // false(Actual:1)
        count = Stream.of("1", 1, collected).count();
        Assert.assertEquals(3, count); // true

        List<Object> newCollected = Stream.of("1", 1, collected).filter(obj -> {
            if (obj instanceof String) {
                return true;
            }
            return false;
        }).collect(Collectors.toList());
        Assert.assertEquals(Arrays.asList("1"), newCollected); // true
    }

    @Test
    public void test1() {
        // flatMap将每个列表转换为Stream对象,调用的Function()接口
        List<Integer> together = Stream.of(Arrays.asList(2, 1), Arrays.asList(3, 4))
            .flatMap(numbers -> numbers.stream())
            .collect(Collectors.toList());
        assertEquals(Arrays.asList(2, 1, 3, 4), together);

        // min/max
        Integer minInt = together.stream().min(Comparator.comparing(index -> index)).get();
        assertEquals(1, minInt.intValue());

        // sort排序
        together.sort(Comparator.comparing(Integer::intValue));
        System.out.println(together);
    }

    @Test
    public void test2() {
        // reduce 求和
        int count = Stream.of(1, 2, 3, 4, 5).reduce(0, (acc, element) -> {
            System.out.println(acc);
            return acc + element;
        });
        Assert.assertEquals(15, count);
    }

    @Test
    public void test3() {
        Map<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        // 可用于缓存处理,如果没有该key,处理后存入keyd
        map.computeIfAbsent("key2", s -> "value".concat(s.substring(s.length() - 1) + "_"));
        map.forEach((key, value) -> System.out.println("key:" + key + ",value:" + value));
    }
}
