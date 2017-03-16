package com.study.java.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Test;

/**
 * @author Jeffrey
 * @since 2017/03/10 17:22
 */
public class ThreadlocalDemo {

    public static final ThreadLocal<Integer> localInteger = ThreadLocal
        .withInitial(() -> new Integer(0));

    public static final ThreadLocal<DateFormat> localSdf = ThreadLocal
        .withInitial(() -> new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"));

    @Test
    public void testLocalInteger() throws Exception {
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                System.out
                    .println("线程" + Thread.currentThread() + "的初始value:" + localInteger.get());
                for (int k = 0; k < 10000; k++) {
                    localInteger.set(localInteger.get() + k);
                }
                System.out
                    .println("线程" + Thread.currentThread() + "的累加value:" + localInteger.get());
            }).start();
        }
        System.in.read();
    }

    @Test
    public void testLocalDateFormat() throws Exception {
        AtomicInteger n = new AtomicInteger(-1);
        Set<String> dateSet = new HashSet<>();
        Set<Integer> numberSet = new HashSet<>();

        Date[] dates = new Date[1000];
        for (int i = 0; i < 1000; i++) {
            dates[i] = localSdf.get().parse(i + "-1-1 1:1:1");
        }

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 1000; i++) {
            executorService.execute(() -> {
                int number = n.incrementAndGet();
                String date = localSdf.get().format(dates[number]);
                numberSet.add(number);
                dateSet.add(date);
                System.out.println(number + " " + date);
            });
        }
        executorService.shutdown();
        Thread.sleep(5000);
        System.out.println("date:" + dateSet.size());
        System.out.println("number:" + numberSet.size());
        System.in.read();
    }
}
