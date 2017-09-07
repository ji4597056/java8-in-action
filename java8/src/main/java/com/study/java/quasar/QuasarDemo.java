package com.study.java.quasar;

import co.paralleluniverse.fibers.Fiber;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;
import org.junit.Test;

/**
 * @author Jeffrey
 * @since 2017/07/17 17:17
 */
public class QuasarDemo {

    private static final Integer count = 2000;

    @Test
    public void test() throws InterruptedException {
        IntStream.range(0, 10).forEach(value -> {
            try {
                testThread();
                testFiber();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private static void testThread() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(count);
        long start = System.currentTimeMillis();
        ExecutorService service = Executors.newFixedThreadPool(200);
        for (int i = 0; i < count; i++) {
            service.submit(() -> {
                task();
                latch.countDown();
            });
        }
        latch.await();
        System.out.println("thread:" + (System.currentTimeMillis() - start));
    }

    private static void testFiber() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(count);
        long start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            new Fiber<Void>("Caller", () -> {
                task();
                latch.countDown();
            }).start();
        }
        latch.await();
        System.out.println("fiber:" + (System.currentTimeMillis() - start));
    }

    private static void task() {
        int a = 1;
//        try {
//            TimeUnit.MILLISECONDS.sleep(500L);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        for (; ; a++) {
            if (a > 1000) {
                break;
            }
        }
    }

}
