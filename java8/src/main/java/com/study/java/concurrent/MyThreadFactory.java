package com.study.java.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.junit.Test;

/**
 * 自定义线程工厂name
 *
 * @author Jeffrey
 * @since 2017/06/07 15:21
 */
public class MyThreadFactory implements ThreadFactory {

    private final String poolName;

    public MyThreadFactory() {
        poolName = "test";
    }

//    public MyThreadFactory(String poolName) {
//        this.poolName = poolName;
//    }

    @Override
    public Thread newThread(Runnable r) {
        System.out.println("creat:" + Thread.currentThread().getName());
        return new MyAppThread(r, poolName);
    }

    @Test
    public void test() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 10, 1, TimeUnit.MINUTES,
            new ArrayBlockingQueue<>(20), new MyThreadFactory());
        for (int i = 0; i < 100; i++) {
            executor.execute(() -> {
                System.out.println(Thread.currentThread().getName());
            });
        }
    }
}
