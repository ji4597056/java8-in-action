package com.study.java.concurrent;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.Test;

/**
 * @author Jeffrey
 * @since 2017/06/19 17:06
 */
public class CompletableFutureDemo {

    Executor threadPool = Executors.newFixedThreadPool(100);

    @Test
    public void test1() throws ExecutionException, InterruptedException {
        int key = 100;
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> 1 / 0)
            .exceptionally(ex -> {
                System.out.println(ex.getMessage());
                return 0;
            })
            .thenApply(String::valueOf)
            .thenApply(str -> "{" + str + "}")
            .thenAcceptAsync(System.out::println);
        future.get();
    }

    @Test
    public void test2() throws ExecutionException, InterruptedException {
        int key = 100;
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> Math.pow(key, 2))
            .thenCompose(
                d -> CompletableFuture.supplyAsync(() -> Math.pow(d, 2)).thenApply(String::valueOf)
                    .thenApply(str -> "{" + str + "}")).thenAccept(System.out::println);
        future.get();
    }

    @Test
    public void test3() throws ExecutionException, InterruptedException {
        CompletableFuture<Double> f1 = CompletableFuture.supplyAsync(() -> Math.pow(100, 2));
        CompletableFuture<Double> f2 = CompletableFuture.supplyAsync(() -> Math.pow(100, 2));
        CompletableFuture<Void> f = f1.thenCombine(f2, Double::sum).thenAccept(System.out::println);
        f.get();
    }

    @Test
    public void test4() {
        System.out.println("process:" + Runtime.getRuntime().availableProcessors());
        long start = System.currentTimeMillis();
        List<Integer> ids = IntStream.range(0, 100).mapToObj(Integer::new)
            .collect(Collectors.toList());
        CompletableFuture[] futures = ids.stream().map(id -> CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (id > 30 && id < 70) {
                throw new RuntimeException(id + "> 4");
            }
            System.out.println(id);
        }, threadPool)).toArray(CompletableFuture[]::new);
        CompletableFuture all = CompletableFuture.allOf(futures).exceptionally(throwable -> {
            System.out.println("exception!");
            return null;
        });
        all.join();
        System.out.println("cost:" + (System.currentTimeMillis() - start));
        if (all.isCompletedExceptionally()) {
            System.out.println("!!!");
        }
    }

    @Test
    public void test5() {
        System.out.println("process:" + Runtime.getRuntime().availableProcessors());
        long start = System.currentTimeMillis();
        List<Integer> ids = IntStream.range(0, 100).mapToObj(Integer::new)
            .collect(Collectors.toList());
        List<CompletableFuture<Integer>> futures = ids.stream()
            .map(id -> CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (id > 30 && id < 70) {
                    throw new RuntimeException(id + "> 50");
                }
                return id;
            }, threadPool)).collect(Collectors.toList());
        List<Integer> result = futures.stream().map(future -> future.exceptionally(throwable -> -1))
            .map(CompletableFuture::join).collect(Collectors.toList());
        System.out.println(result);
        System.out.println("cost:" + (System.currentTimeMillis() - start));
    }

    @Test
    public void test6() throws ExecutionException, InterruptedException {
        Future future = CompletableFuture
            .supplyAsync(() -> {
                try {
                    TimeUnit.SECONDS.sleep(1L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("supplyAsync:" + Thread.currentThread().getName());
                return 1;
            })
            .thenApply(i -> {
                System.out.println("thenApply:" + Thread.currentThread().getName());
                return i;
            });
        future.get();
    }

    @Test
    public void test7() throws ExecutionException, InterruptedException {
        List<CompletableFuture<Integer>> futures = Stream.of(1,2,3,4,5,6,7,8,9,10)
            .map(key -> CompletableFuture
                .supplyAsync(() -> {
                    try {
                        TimeUnit.SECONDS.sleep(1L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out
                        .println("key:" + key + ",supplyAsync:" + Thread.currentThread().getName());
                    return key;
                }))
            .collect(Collectors.toList());
        futures.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }
}
