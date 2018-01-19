package com.study.java.lambda.test;

import com.study.java.lombok.Student;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import lombok.NonNull;
import org.junit.Test;

/**
 * @author Jeffrey
 * @since 2017/05/18 13:23
 */
public class Demo {

    @Test
    public void test1() {
        String test = null;
        @NonNull String name = test;
        System.out.println(name);
    }

    @Test
    public void test2() throws InterruptedException {
        final int threadNum = 200;
        LongAdder adder = new LongAdder();
        CountDownLatch latch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(threadNum);
        ExecutorService pool = Executors.newFixedThreadPool(threadNum);
        IntStream.range(0, threadNum).forEach(value -> pool.execute(() -> {
            try {
                latch.await();
                adder.add(1);
                endLatch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));
        latch.countDown();
        pool.shutdown();
        Logger.getGlobal().info(String.valueOf(adder.sum()));
        endLatch.await();
        Logger.getGlobal().info(String.valueOf(adder.sum()));
    }

    @Test
    public void test3() {
        int odd = 20;
        Logger.getGlobal().info(String.valueOf(odd % 2 == 1));
        Logger.getGlobal().info(String.valueOf((odd & 1) != 0));
    }

    @Test
    public void test4() throws BrokenBarrierException, InterruptedException {
        CyclicBarrier barrier = new CyclicBarrier(200);
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        IntStream.range(0, 100).forEach(i -> executorService.execute(() -> {
            try {
                System.out.println(barrier.getNumberWaiting());
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("=============end:" + barrier.getNumberWaiting());
        }));
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);
    }

    @Test
    public void test5() {
        List<String> list = new ArrayList<>();
        list.add("A");
        List<String> copy = Collections.unmodifiableList(list);
        List<String> copy1 = new ArrayList<>(list);
        Logger.getGlobal().info("list:" + list + ", copy:" + copy + ", copy1:" + copy1);
        list.remove(0);
        Logger.getGlobal().info("list:" + list + ", copy:" + copy + ", copy1:" + copy1);
    }

    @Test
    public void test6() {
        Set<String> set = new HashSet<>();
        Logger.getGlobal().info(String.valueOf(set.remove("123")));
    }

    @Test
    public void test7() {
        int size = 5;
        IntStream.range(0, 10).forEach(i -> System.out.println(i % size));
    }

    @Test
    public void test8() {
        Student student = new Student(null, 1);
        System.out.println(Optional.of(student).map(Student::getName).orElse("null"));
    }
}
