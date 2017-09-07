package com.study.java.concurrent.thread;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import org.junit.Test;

/**
 * @author Jeffrey
 * @since 2017/06/15 14:51
 */
public class ThreadDemo {

    @Test
    public void test1() throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    Logger.getGlobal().warning("Interrupted");
                    break;
                }
                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException e) {
                    Logger.getGlobal().warning("Interrupted when sleep");
                    Thread.currentThread()
                        .interrupt(); //发生InterruptedException时,会自动清除中断标记,所以需要重新设置中断标记
                }
                Thread.yield();
            }
        });
        thread.start();
        Thread.sleep(2000L);
        thread.interrupt();
        thread.join();
    }

    @Test
    public void test2() throws InterruptedException {
        Object obj = new Object();
        ExecutorService executor = Executors.newFixedThreadPool(10);
        IntStream.range(0, 15).forEach(i -> executor.submit(() -> {
            synchronized (obj) {
                Logger.getGlobal().info("thread[" + i + "] is start");
                try {
                    obj.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Logger.getGlobal().info("thread[" + i + "] is end");
            }
        }));
        executor.shutdown();
        Thread.sleep(1000L);
        synchronized (obj) {
            Logger.getGlobal().info("enter notify");
            obj.notify();
        }
        executor.awaitTermination(1, TimeUnit.MINUTES);
    }

    @Test
    public void test3() throws InterruptedException {
        Object obj = new Object();
        Thread t = new Thread(() -> {
            long start = System.currentTimeMillis();
            Logger.getGlobal().info("Thread start,start:" + start);
            LockSupport.park(obj);
            if (Thread.interrupted()) {
                Logger.getGlobal().info("Thread interrupted");
//                return;
            }
            Logger.getGlobal().info("Thread end,cost:" + (System.currentTimeMillis() - start));
        });
        t.start();
        TimeUnit.SECONDS.sleep(1);
        t.interrupt();
//        LockSupport.unpark(t);
        t.join();
    }

    @Test
    public void test4() throws InterruptedException {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
        Logger.getGlobal().info("start:" + LocalDateTime.now().getSecond());
        scheduledExecutorService.scheduleAtFixedRate(
            () -> Logger.getGlobal().info("doing:" + LocalDateTime.now().getSecond()), 2, 1,
            TimeUnit.SECONDS);
        scheduledExecutorService.awaitTermination(1, TimeUnit.MINUTES);
    }

    @Test
    public void test5() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(() -> System.out.println(1 / 0));
        executorService.submit(() -> System.out.println(1 / 1));
        executorService.awaitTermination(1, TimeUnit.MINUTES);
    }

    @Test
    public void test6() {
        Map<Integer, Integer> skipMap = new ConcurrentSkipListMap<>();
        IntStream.range(0, 10000).forEach(i -> skipMap.put(i, i));
        long start = System.currentTimeMillis();
        skipMap.get(1000);
        Logger.getGlobal().info(1000 + ",cost:" + (System.currentTimeMillis() - start));
        start = System.currentTimeMillis();
        skipMap.get(1000);
        Logger.getGlobal().info(10000 + ",cost:" + (System.currentTimeMillis() - start));
    }

}
