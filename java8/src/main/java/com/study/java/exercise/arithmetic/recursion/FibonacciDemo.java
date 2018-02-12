package com.study.java.exercise.arithmetic.recursion;

import org.junit.Assert;
import org.junit.Test;

/**
 * 斐波拉切数列
 *
 * @author Jeffrey
 * @since 2018/02/02 13:43
 */
public class FibonacciDemo {

    public static long fibonacci(int n) throws Exception {
        if (n < 0L) {
            throw new RuntimeException("n不能小于0!");
        }
        if (n == 0L || n == 1L) {
            return n;
        }
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    @Test
    public void test() throws Exception {
        Assert.assertEquals(0L, fibonacci(0));
        Assert.assertEquals(1L, fibonacci(1));
        Assert.assertEquals(1L, fibonacci(2));
        Assert.assertEquals(2L, fibonacci(3));
        Assert.assertEquals(3L, fibonacci(4));
        System.out.println(fibonacci(10));
    }
}
