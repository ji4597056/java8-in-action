package com.study.java.test;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
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
        List<Integer> l1 = IntStream.of(1, 2, 3, 4, 5).boxed().collect(Collectors.toList());
        List<Integer> l2 = new ArrayList<>();
        l2.addAll(l1);
        l2.add(6);
        System.out.println(l1.size());
        System.out.println(l2.size());
    }

    @Test
    public void test10() {
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now.get(ChronoField.DAY_OF_MONTH));
        LocalDateTime past = now.minus(1, ChronoUnit.DAYS);
        System.out.println(past.get(ChronoField.DAY_OF_MONTH));
    }

    @Test
    public void test11() {
        Assert.assertEquals(f1(0.3), f2(0.3), 0.0001); //true
        Assert.assertEquals(f1(0.4), f2(0.4), 0.0001); //true
        Assert.assertEquals(f1(0.8), f2(0.8), 0.0001); //true
        Assert.assertEquals(f1(0.9), f2(0.9), 0.0001); //true
        Assert.assertEquals(f1(0.25), f2(0.25), 0.0001); //true
    }

    @Test
    public void test12() {
        int oldCapacity = 9;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        System.out.println(newCapacity);
        System.out.println((oldCapacity * 3) / 2 + 1);
        List<String> tmp = Lists.newArrayList("1", "2", "3");
        List<String> list = new LinkedList<>(tmp);
        System.out.println(list);
        Map<String, Object> map = new HashMap<>();
        map.put(null, null);
        System.out.println(map.size());
    }

    @Test
    public void test13() {
        int len = 31;
        int t1 = 33;
        int t2 = 34;
        int t3 = 35;
        int t4 = -33;
        System.out.println(t1 & len);
        System.out.println(t2 & len);
        System.out.println(t3 & len);
        System.out.println(t4 & len);
        HashMap hashMap = new HashMap(3, 1);
        System.out.println(hashMap);
        System.out.println(tableSizeFor(32));
        System.out.println(tableSizeFor(31));
        System.out.println(tableSizeFor(112));
        System.out.println(tableSizeFor(4));
    }

    @Test
    public void test14() {
        Map<String, Object> m1 = new HashMap<>(4, 0.5f);
        m1.put("1", 1);
        m1.put("3", 3);
        m1.put("2", 2);
        System.out.println("HashMap:");
        m1.forEach((k, v) -> System.out.println("key:" + k));
        Map<String, Object> m2 = new LinkedHashMap<>();
        m2.put("1", 1);
        m2.put("3", 3);
        m2.put("2", 2);
        System.out.println("LinkedHashMap:");
        m2.forEach((k, v) -> System.out.println("key:" + k));
    }

    @Test
    public void test15() {
        Map<String, Object> m1 = new HashMap<>(4);
        m1.put("1", 1);
        m1.put("2", 1);
        m1.put("3", 1);
    }

    @Test
    public void test16() {
//        System.out.println(Math.ceil(4 / 2.0));
//        System.out.println(Math.ceil(5 / 2.0));
//        System.out.println((int) Math.min(Math.floor(4 / 2.0), Math.floor(5 / 2.0)));
        Map<String, Object> m1 = new HashMap<>();
        m1.put("key", 1);
        Map<String, Object> m2 = new HashMap<>(m1);
        System.out.println(m2.get("key"));
        m2.put("key", 2);
        System.out.println(m2.get("key"));
        Map<String, Object> m3 = new HashMap<>(m1);
        System.out.println(m3.get("key"));
        m3.put("key", 3);
        System.out.println(m3.get("key"));
    }

    @Test
    public void test17() {
        int i = Integer.MAX_VALUE;
        System.out.println(i);
        System.out.println(i >> 1);
        System.out.println((i >> 1) + (i >> 1) + 2);
        System.out.println(i * 2);
    }

    @Test
    public void test18() {
        System.out.println(Integer.MAX_VALUE);
        System.out.println(1 << 30);
        System.out.println(1 << 31);
//        A1 a = new A1();
//        a.setKey("a");
//        A1 b = a;
//        a = new A1();
//        a.setKey("b");
//        System.out.println(a.getKey());
//        System.out.println(b.getKey());
    }

    @Test
    public void test19() {
        Map<Integer, Integer> map = new HashMap<>(2, 2);
        IntStream.range(0, 6).forEach(i -> map.put(i, i));
        System.out.println(2 << 31 - 1);
    }

    @Test
    public void test21() {
        TreeMap<A1, String> treeMap = new TreeMap<>();
        A1 a1 = new A1();
        treeMap.put(a1, "1");
        System.out.println(treeMap.get(a1));
    }

    private int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return n + 1;
    }

    @Test
    public void test20() {
        StackVars stackVars = new StackVars();
        int val = Integer.MAX_VALUE;
        for (int i = 0; i < 3; i++) {
            long start = System.currentTimeMillis();
            stackVars.stackAccess(val);
            System.out.print("局部变量:");
            System.out.println(System.currentTimeMillis() - start);
            start = System.currentTimeMillis();
            stackVars.instanceAccess(val);
            System.out.println("成员变量:");
            System.out.println(System.currentTimeMillis() - start);
            start = System.currentTimeMillis();
            stackVars.stackAccess(val);
            System.out.println("静态变量:");
            System.out.println(System.currentTimeMillis() - start);
        }
    }

    @Test
    public void test22() {
//        System.out.println(Integer.MAX_VALUE ^ (Integer.MAX_VALUE >>> 16));
//        System.out.println(Integer.MIN_VALUE ^ (Integer.MIN_VALUE >>> 16));
//        System.out.println(Integer.MAX_VALUE);
//        System.out.println(Integer.MIN_VALUE);
//        System.out.println(spread(Integer.MAX_VALUE));
//        System.out.println(spread(Integer.MIN_VALUE));
//        System.out.println(Integer.toBinaryString(spread1(Integer.MAX_VALUE)));
//        System.out.println(Integer.toBinaryString(spread1(Integer.MIN_VALUE)));
        System.out.println(spread(Integer.MAX_VALUE) & (1 << 29 - 1));
        System.out.println(spread(Integer.MIN_VALUE) & (1 << 29 - 1));
        System.out.println(spread1(Integer.MAX_VALUE) & (1 << 29 - 1));
        System.out.println(spread1(Integer.MIN_VALUE) & (1 << 29 - 1));
    }

    @Test
    public void test23() {
        Cell2[] arr2 = new Cell2[1024 * 1024];
        for (int i = 0; i < arr2.length; i++) {
            arr2[i] = new Cell2(1);
        }
        long start2 = System.nanoTime();
        for (int i = 0; i < arr2.length; i++) {
            arr2[i].value = arr2[i].value * 3;
        }
        System.out.println(System.nanoTime() - start2);

        Cell1[] arr1 = new Cell1[1024 * 1024];
        for (int i = 0; i < arr1.length; i++) {
            arr1[i] = new Cell1(1);
        }
        long start = System.nanoTime();
        for (int i = 0; i < arr1.length; i++) {
            arr1[i].value = arr1[i].value * 3;
        }
        System.out.println(System.nanoTime() - start);
    }

    @Test
    public void test24() {
//        System.out.println(numberOfLeadingZeros(1));
//        System.out.println(numberOfLeadingZeros(2));
//        System.out.println(numberOfLeadingZeros(16));
//        System.out.println(numberOfLeadingZeros(1 << 20));
//        System.out.println((1 << 30) >>> 31);
//        System.out.println((16 << 30) >>> 31);
        for (int i = 0; i <= 30; i++) {
            System.out.println(numberOfLeadingZeros(1 << i));
        }
        System.out.println("==================");
        for (int i = 0; i <= 30; i++) {
            System.out.println(Integer.toBinaryString(numberOfLeadingZeros(1 << i)));
        }
    }

    @Test
    public void test25() throws InterruptedException {
//        System.out.println((numberOfLeadingZeros(2) << 16) + 2);
//        System.out.println((numberOfLeadingZeros(16) << 16) + 2);
//        System.out.println((numberOfLeadingZeros(16) << 16) + 2 - 1);
        ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>();
        ExecutorService service = Executors.newFixedThreadPool(100);
        service.submit(() -> {
            for (int i = 1; i <= 1000_000_000; i++) {
                map.put(i, i);
            }
        });
        service.awaitTermination(1, TimeUnit.MINUTES);
    }

    @Test
    public void test26() {
        System.out.println(-1 << 20);
        System.out.println(Integer.toBinaryString(-1 << 20));
        System.out.println((-1 << 20) + 1);
        System.out.println(Integer.toBinaryString((-1 << 20) + 1));
    }

    @Test
    public void test27() {
        A1 a = null;
        changeA(a);
        System.out.println(a);
    }

    @Test
    public void test28() {
        PriorityQueue<Integer> queue = new PriorityQueue<>((o1, o2) -> o2 - o1);
        queue.offer(2);
        queue.offer(1);
        queue.offer(4);
        queue.offer(3);
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
    }

    @Test
    public void test29() {
        ThreadLocal local = ThreadLocal.withInitial(() -> 1);
        System.out.println(local);
        local.remove();
        System.out.println(local);
        local.set(2);
        System.out.println(local);
    }

    @Test
    public void test30() {
        System.out.println(this.getClass().getClassLoader().getClass().getSimpleName());
    }

    @Test
    public void test31() {
        ThreadLocal local = ThreadLocal.withInitial(() -> 1);
        local.remove();
        local.set(1);
        local.remove();
        local.set(1);
    }

    @Test
    public void test32() {
        int i = Integer.MAX_VALUE;
        System.out.println(i * 5);
    }

    @Test
    public void test33() {
        int[] array = new int[]{1, 2, 3};
        array(array);
        for (int i : array) {
            System.out.println(i);
        }
    }

    @Test
    public void test34(){
        int i = 1;
        testInt(i);
        System.out.println(i);
        testInteger(i);
        System.out.println(i);
        System.out.println("============");
        Integer integer = Integer.valueOf(1);
        testInt(integer);
        System.out.println(integer);
        testInteger(integer);
        System.out.println(integer);}

    private void testInt(int i) {
        i++;
    }

    private void testInteger(Integer i) {
        i++;
    }

    private void array(int[] arrays) {
        for (int i = 0; i < arrays.length; i++) {
            arrays[i] = 0;
        }
    }

    private void changeA(A1 a) {
        a = new A1();
        a.setKey("1");
    }

    public static int numberOfLeadingZeros(int i) {
        // HD, Figure 5-6
        if (i == 0) {
            return 32;
        }
        int n = 1;
        if (i >>> 16 == 0) {
            n += 16;
            i <<= 16;
        }
        if (i >>> 24 == 0) {
            n += 8;
            i <<= 8;
        }
        if (i >>> 28 == 0) {
            n += 4;
            i <<= 4;
        }
        if (i >>> 30 == 0) {
            n += 2;
            i <<= 2;
        }
        n -= i >>> 31;
        return n | (1 << 15);
    }

    static final class Cell1 {

        volatile long value;

        Cell1(long x) {
            value = x;
        }
    }

    static final class Cell2 {

        volatile long value;
        public long p1, p2, p3, p4, p5, p6;

        Cell2(long x) {
            value = x;
        }
    }

    static final int spread(int h) {
        return (h ^ (h >>> 16)) & Integer.MAX_VALUE;
    }

    static final int spread1(int h) {
        return (h ^ (h >>> 16));
    }

    public static class StackVars {

        private int x;    // instance变量
        private static int staticX; //static 变量

        public void stackAccess(int val) { //访问和操作stack变量j
            int j = 0;
            for (int i = 0; i < val; i++) {
                j += 1;
            }
        }

        public void instanceAccess(int val) {//访问和操作instance变量x
            for (int i = 0; i < val; i++) {
                x += 1;
            }
        }

        public void staticAccess(int val) {//访问和操作static变量staticX
            for (int i = 0; i < val; i++) {
                staticX += 1;
            }
        }
    }

    private double f1(double a) {
        return a + (1 - a) * a + (1 - a) * (1 - a) * a;
    }

    private double f2(double a) {
        return 1 - (1 - a) * (1 - a) * (1 - a);
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
