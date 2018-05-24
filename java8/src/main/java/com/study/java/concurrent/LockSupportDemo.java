package com.study.java.concurrent;

import java.util.concurrent.locks.LockSupport;
import org.junit.Test;

/**
 * @author Jeffrey
 * @since 2018/04/15 14:47
 */
public class LockSupportDemo {

    @Test
    public void testPark() throws InterruptedException {
        Thread thread = new Thread(() -> {
            long start = System.currentTimeMillis();
            LockSupport.park();
            System.out.println("cost:" + (System.currentTimeMillis() - start));
        });
        thread.start();
        Thread.sleep(2000L);
        thread.interrupt();
        System.out.println(thread.isInterrupted());
    }

    @Test
    public void testSleep() throws InterruptedException {
        Thread thread = new Thread(() -> {
            long start = System.currentTimeMillis();
            try {
                Thread.sleep(10000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("cost:" + (System.currentTimeMillis() - start));
        });
        thread.start();
        Thread.sleep(2000L);
        thread.interrupt();
        System.out.println(thread.isInterrupted());
    }

    @Test
    public void testInterrupt() throws InterruptedException {
        Thread thread = Thread.currentThread();
        System.out.println(thread.isInterrupted());
        thread.interrupt();
        System.out.println(thread.isInterrupted());
        thread.interrupt();
        System.out.println(thread.isInterrupted());
    }
}
