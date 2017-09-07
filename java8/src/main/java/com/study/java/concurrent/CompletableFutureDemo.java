package com.study.java.concurrent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.junit.Test;

/**
 * @author Jeffrey
 * @since 2017/06/19 17:06
 */
public class CompletableFutureDemo {

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
            .thenAccept(System.out::println);
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
}
