package com.study.java.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.junit.Test;

/**
 * Created by zoufeng on 2017/7/18.
 */
public class MultFutrueDemo {

    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(100,
        120,
        1000 * 60 * 30,
        TimeUnit.MILLISECONDS,
        new ArrayBlockingQueue<Runnable>(3000));

    public String doMethod1() {
        try {
//            System.out.println("start method1....");
            Thread.sleep(1000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "method1 done!";
    }

    public String doMethod2() {
        try {
//            System.out.println("start method2...");
            Thread.sleep(5000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "method2 done!";
    }

    public String doMethod3() {
        try {
//            System.out.println("start method3...");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "method3 done!";
    }

    public String handleRequest() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(3);
        long l = System.currentTimeMillis();
        threadPoolExecutor.execute(() -> {
            String s = doMethod1();
            countDownLatch.countDown();
//            System.out.println(s);
        });
        threadPoolExecutor.execute(() -> {
            String s = doMethod2();
            countDownLatch.countDown();
//            System.out.println(s);
        });
        threadPoolExecutor.execute(() -> {
            String s = doMethod3();
            countDownLatch.countDown();
//            System.out.println(s);
        });
        countDownLatch.await();
        long ll = System.currentTimeMillis() - l;
        return "all method is done!!" + ll + "ms";
    }

    //模拟servlet并发
    public static void main(String[] args) throws InterruptedException {
        MultFutrueDemo multFutrueDemo = new MultFutrueDemo();
        List<Thread> list = new ArrayList<>(100);
        System.out.println("初始化线程...");
        long l = System.currentTimeMillis();
        CyclicBarrier cyclicBarrier = new CyclicBarrier(100, () -> {
            System.out.println("线程并发准备花费" + (System.currentTimeMillis() - l) + "ms,开始并发请求-->");
        });
        for (int i = 0; i < 100; i++) {
            final int t = i;
            list.add(new Thread(() -> {
                try {
                    cyclicBarrier.await();
                    String s = multFutrueDemo.handleRequest();
                    System.out.println(s + "---" + t);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }));
        }
        System.out.println("线程并发....");
        for (Thread thread : list) {
            thread.start();
        }
    }

    @Test
    public void test() throws ExecutionException, InterruptedException {
        //doMethod1:1000l doMethod2:50001 doMethod3:3000
        long start = System.currentTimeMillis();
        CompletableFuture<String> future = CompletableFuture
            .supplyAsync(this::doMethod1, threadPoolExecutor)
            .thenCombine(CompletableFuture.supplyAsync(this::doMethod2, threadPoolExecutor),
                String::concat)
            .thenCombine(CompletableFuture.supplyAsync(this::doMethod3, threadPoolExecutor),
                String::concat)
            .thenCombine(CompletableFuture.supplyAsync(this::doMethod4, threadPoolExecutor),
                String::concat);
        System.out.println(future.exceptionally(throwable -> "error").get());
        System.out
            .println(String.format("cost: %s", String.valueOf(System.currentTimeMillis() - start)));
    }

    public String doMethod4() {
        return String.valueOf(1 / 0);
    }
}
