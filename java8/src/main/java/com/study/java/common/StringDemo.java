package com.study.java.common;

import static java.util.stream.Collectors.toList;

import com.alibaba.fastjson.JSON;
import java.io.Serializable;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;
import org.junit.Test;

/**
 * @author Jeffrey
 * @since 2017/03/24 20:14
 */
public class StringDemo {

    @Test
    public void test() {
        String joined = String.join(",", "Hi", "大家", "你们好");
        System.out.println(joined);
        String ids = String.join(", ", ZoneId.getAvailableZoneIds());
        System.out.println(ids);
        String lists = String.join(", ", Stream.of("你", "们", "好").collect(toList()));
        System.out.println(lists);
    }

    @Test
    public void test1() {
        Queue<String> queue = new ConcurrentLinkedQueue<>();
        queue.poll();
    }

    @Test
    public void test2() {
        Map<String, Object> map = new HashMap<>();
        map.put("test", "true");
        String json = JSON.toJSONString(map);
        System.out.println(json);
        Boolean test = JSON.parseObject(json).getBoolean("test");
        System.out.println(test);
        json += "123";
        String notJson = JSON.parseObject(json).getString("test");
        System.out.println(notJson);
    }

    @Test
    public void test3() {
        String test = "123";
        System.out.println(test instanceof String);
        System.out.println(test instanceof Object);
        System.out.println(test instanceof Serializable);
        System.out.println(test.getClass() == String.class);

        Number num = new Integer(1);
        System.out.println(num instanceof Number);
        System.out.println(num instanceof Integer);
        System.out.println(num.getClass() == Integer.class);
        System.out.println(num.getClass() == Number.class);
    }

    @Test
    public void test4() {
        HashMap<String, String> map = new HashMap<>(6);
        map.put("k1", "v1");
        map.put("k2", "v2");
        map.put("k3", "v3");
        map.put("k4", "v4");
        System.out.println(map);
        float f = 2.3f;
        System.out.println((int) f);
    }

    @Test
    public void test5() {
        String s1 = "Hello";
        String s2 = "Hello";
        String s3 = "Hel" + "lo";
        String s4 = "Hel" + new String("lo");
        String s5 = new String("Hello");
        String s6 = s5.intern();
        String s7 = "H";
        String s8 = "ello";
        String s9 = s7 + s8;

        System.out.println(s1 == s2);  // true
        System.out.println(s1 == s3);  // true
        System.out.println(s1 == s4);  // false
        System.out.println(s1 == s9);  // false
        System.out.println(s4 == s5);  // false
        System.out.println(s1 == s6);  // true
    }

    @Test
    public void test6() {
        Map<String, String> map = new HashMap<>(4);
        map.put("s1", "s1");
        map.put("s2", "s2");
        System.out.println(map);
    }

    @Test
    public void test7() {
        Tester tester = new Tester();
        tester.print();
    }

    @Test
    public void test8() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -29);
        System.out.println(c.get(Calendar.DATE));
        List<String> list = new ArrayList<>();
        list.stream().forEach(System.out::println);
    }

    class Tester {

        private int a;

        public Tester(){
            System.out.println("start");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("end");
        }

        public void print() {
            System.out.println("print start");
            System.out.println(a);
            System.out.println("print end");
        }
    }

}
