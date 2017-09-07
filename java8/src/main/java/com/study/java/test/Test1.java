package com.study.java.test;

import org.junit.Test;

/**
 * @author Jeffrey
 * @since 21/05/2017 8:39 PM
 */
public class Test1 {

    @Test
    public void test1() {
        int a = 1;
        int b = 2;

        a = a ^ b;
        b = a ^ b;
        a = a ^ b;

        System.out.println(String.format("a=%d,b=%d", a, b));
    }

    @Test
    public void test2() {
        System.out.println(1 << 2 + 3 << 4);
    }
}
