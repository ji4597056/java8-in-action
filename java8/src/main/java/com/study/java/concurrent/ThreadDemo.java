package com.study.java.concurrent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;
import org.junit.Test;

/**
 * @author Jeffrey
 * @since 2017/05/18 17:16
 */
public class ThreadDemo implements Runnable {

    private static volatile LongAdder number = new LongAdder();

    @Override
    public void run() {
        IntStream.rangeClosed(1, 10_000).forEach(value -> increase());
    }

    public void increase() {
        number.increment();
    }

    public static void main(String[] args) throws InterruptedException {
        IntStream.rangeClosed(1, 10).forEach(value -> {
            try {
                test();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private static void test() throws InterruptedException {
        Thread thread1 = new Thread(new ThreadDemo());
        Thread thread2 = new Thread(new ThreadDemo());
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println(number.sum());
    }

    @Test
    public void test1() throws InterruptedException {
        LongAccumulator accumulator = new LongAccumulator(Long::sum, 0);
        ExecutorService executorService = Executors.newCachedThreadPool();
        IntStream.range(1, 10000)
            .forEach(i -> executorService.submit(() -> accumulator.accumulate(i)));
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);
        System.out.println(accumulator.longValue());
    }

    @Test
    public void test2() throws InterruptedException {
        final String key = "test";
        Map<String, Count> map = new ConcurrentHashMap(1);
        map.putIfAbsent(key, new Count(0));
        CountDownLatch latch = new CountDownLatch(1);
        ExecutorService executorService = Executors.newFixedThreadPool(1000);
        IntStream.range(0, 1000).forEach(i -> executorService.submit(() -> {
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            map.get(key).add();
        }));
        latch.countDown();
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);
        System.out.println(map.get(key).getCount());
    }

    class Count {

        private int count;

        private ReentrantLock lock = new ReentrantLock();

        Count(int count) {
            this.count = count;
        }

        public void add() {
            lock.lock();
            try {
                this.count++;
            } finally {
                lock.unlock();
            }
        }

        int getCount() {
            return count;
        }
    }
}
