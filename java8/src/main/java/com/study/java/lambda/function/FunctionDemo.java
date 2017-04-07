package com.study.java.lambda.function;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jeffrey
 * @since 2017/03/15 10:10
 */
public class FunctionDemo {

    @Test
    public void test() {
        // 高阶函数是指接受另外一个函数作为参数,或返回一个函数的函数.
        // 使用Lambda表达式获取值而不是变量.
        // 当lambda表达式使用this指针时,会调用引用创建该lambda表达式的类指针
    }

    @Test
    public void testAddUp() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        Assert.assertEquals(15, addUp(list.stream()));
    }

    @Test
    public void test1() {
        // peek查看流中的值(可用作记录日志)
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        list.stream().peek(System.out::println).collect(toList());
    }

    // 计算流中所有数之和
    public int addUp(Stream<Integer> numbers) {
        return numbers.reduce(0, (acc, element) -> acc + element);
    }

    /**
     * TODO 暂未理解
     * 只用reduce 和Lambda 表达式写出实现Stream 上的map 操作的代码，如果不想返回Stream，可以返回一个List
     */
    public static <I, O> List<O> map(Stream<I> stream, Function<I, O> mapper) {
        return stream.reduce(new ArrayList<O>(), (acc, x) -> {
            // We are copying data from acc to new list instance. It is very inefficient,
            // but contract of Stream.reduce method requires that accumulator function does
            // not mutate its arguments.
            // Stream.collect method could be used to implement more efficient mutable reduction,
            // but this exercise asks to use reduce method.
            List<O> newAcc = new ArrayList<>(acc);
            newAcc.add(mapper.apply(x));
            return newAcc;
        }, (List<O> left, List<O> right) -> {
            // We are copying left to new list to avoid mutating it.
            List<O> newLeft = new ArrayList<>(left);
            newLeft.addAll(right);
            return newLeft;
        });
    }

    /**
     * TODO 暂未理解
     * 只用reduce 和Lambda 表达式写出实现Stream 上的filter 操作的代码，如果不想返回Stream，可以返回一个List。
     */
    public static <I> List<I> filter(Stream<I> stream, Predicate<I> predicate) {
        List<I> initial = new ArrayList<>();
        return stream.reduce(initial,
            (List<I> acc, I x) -> {
                if (predicate.test(x)) {
                    // We are copying data from acc to new list instance. It is very inefficient,
                    // but contract of Stream.reduce method requires that accumulator function does
                    // not mutate its arguments.
                    // Stream.collect method could be used to implement more efficient mutable reduction,
                    // but this exercise asks to use reduce method explicitly.
                    List<I> newAcc = new ArrayList<>(acc);
                    newAcc.add(x);
                    return newAcc;
                } else {
                    return acc;
                }
            },
            FunctionDemo::combineLists);
    }

    private static <I> List<I> combineLists(List<I> left, List<I> right) {
        // We are copying left to new list to avoid mutating it.
        List<I> newLeft = new ArrayList<>(left);
        newLeft.addAll(right);
        return newLeft;
    }
}
