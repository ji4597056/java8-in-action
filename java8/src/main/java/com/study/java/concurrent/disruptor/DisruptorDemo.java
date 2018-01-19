package com.study.java.concurrent.disruptor;

import org.junit.Test;

/**
 * @author Jeffrey
 * @since 2017/06/19 15:09
 */
public class DisruptorDemo {

    @Test
    public void test1() {
        //  获取int最大值
        System.out.println((1 << 31) - 1);
        System.out.println(~(-1 << 31));
        System.out.println(1 << 31);
        System.out.println(2 & 1);
        System.out.println(11 & 1);
        System.out.println(-11 & 1);
        int a = 1, b = 2;
        System.out.println("a:" + a + ",b:" + b);
        a ^= b;
        b ^= a;
        a ^= b;
        System.out.println("a:" + a + ",b:" + b);
        System.out.println((1 ^ 1) >= 0);
        System.out.println((1 ^ -1) >= 0);
        System.out.println(2 << 1);
        int c = 64;
        System.out.println(c > 0 ? (c & (c - 1)) == 0 : false);
        System.out.println(3 & 7);
        System.out.println(15 & 7);
    }
}
