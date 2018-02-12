package com.study.java.test;

import com.alibaba.fastjson.JSON;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.Assert;
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

    @Test
    public void test3() {
        ThreadLocal<SimpleDateFormat> sdf = ThreadLocal
            .withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ssSSS"));
        System.out.println(sdf.get().format(new Date()));
    }

    @Test
    public void test4() {
        Instant instant1 = Instant.now();
        System.out.println(instant1.toString());
        Instant instant2 = Instant.now();
        System.out.println(instant2.toString());
        System.out.println(instant1.isBefore(instant2));//true
        System.out.println(instant1.isAfter(instant2));//false
    }

    @Test
    public void test5() {
        String appId = "/group/heihei";
        Assert.assertEquals("heihei", appId.substring(appId.lastIndexOf('/') + 1));
    }

    @Test
    public void test6() {
        A1 a1 = new A1();
        a1.setKey("a1");
        A2 a2 = JSON.parseObject(JSON.toJSONString(a1), A2.class);
        System.out.println(a2);
    }

    @Test
    public void test7() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str = null;
        System.out.println("Enter your value:");
        str = br.readLine();
        System.out.println("your value is :" + str);
    }

    @Test
    public void test8() {
        int[] arr = new int[64 * 1024 * 1024];
        long start = System.nanoTime();
        for (int i = 0; i < arr.length; i++) {
            arr[i] *= 3;
        }
        System.out.println(System.nanoTime() - start);

        long start2 = System.nanoTime();
        for (int i = 0; i < arr.length; i += 2) {
            arr[i] *= 3;
        }
        System.out.println(System.nanoTime() - start2);

        long start3 = System.nanoTime();
        for (int i = 0; i < arr.length; i += 16) {
            arr[i] *= 3;
        }
        System.out.println(System.nanoTime() - start3);
    }

    @Test
    public void test9() {
        List<Integer> l1 = IntStream.of(1,2,3,4,5).boxed().collect(Collectors.toList());
        List<Integer> l2 = new ArrayList<>();
        l2.addAll(l1);
        l2.add(6);
        System.out.println(l1.size());
        System.out.println(l2.size());
    }

    static class A1 {

        private String key;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("A1{");
            sb.append("key='").append(key).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }

    static class A2 {

        private String key;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("A2{");
            sb.append("key='").append(key).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }
}
