package com.study.java.concurrent;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import org.junit.Test;

/**
 * @author Jeffrey
 * @since 2017/05/17 15:44
 */
public class FutureDemo {

    private static final ThreadLocalRandom random = ThreadLocalRandom.current();

    @Test
    public void test1() {
        Shop shop = new Shop("BestShop");
        long start = System.nanoTime();
        Future<Double> futurePrice = shop.getPriceAsync("my favorite product");
        long invocationTime = ((System.nanoTime() - start) / 1_000_000);
        System.out.println("Invocation returned after " + invocationTime
            + " msecs");
        try {
            double price = futurePrice.get();
            System.out.printf("Price is %.2f%n", price);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        long retrievalTime = ((System.nanoTime() - start) / 1_000_000);
        System.out.println("Price returned after " + retrievalTime + " msecs");
    }

    @Test
    public void test2() {
        List<Shop> shops = Arrays
            .asList(new Shop("A"), new Shop("B"), new Shop("C"), new Shop("D"), new Shop("E"));
        String product = "iphone7";
        // 1.stream
        printAndContFromStream(shops, product);
        // 2. parallel stream
        printAndContFromParallelStream(shops, product);
        // 3. completable future
        printAndContFromCompletableFuture(shops, product);
    }

    private void printAndContFromStream(List<Shop> shops, String product) {
        long start = System.nanoTime();
        System.out.println(findPriceFromStream(shops, product));
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Done in " + duration + " msecs");
    }

    private void printAndContFromParallelStream(List<Shop> shops, String product) {
        long start = System.nanoTime();
        System.out.println(findPriceFromParallelStream(shops, product));
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Done in " + duration + " msecs");
    }

    private void printAndContFromCompletableFuture(List<Shop> shops, String product) {
        long start = System.nanoTime();
        System.out.println(findPriceFromCompletableFuture(shops, product));
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Done in " + duration + " msecs");
    }

    private List<String> findPriceFromStream(List<Shop> shops, String product) {
        return shops.stream()
            .map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))
            .collect(Collectors.toList());
    }

    private List<String> findPriceFromParallelStream(List<Shop> shops, String product) {
        return shops.parallelStream()
            .map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))
            .collect(Collectors.toList());
    }

    private List<String> findPriceFromCompletableFuture(List<Shop> shops, String product) {
        // 使用2个流处理,第一个流返回CompletableFuture集合,遍历流join,返回结果
        List<CompletableFuture<String>> completableFutures = shops.stream()
            .map(shop -> CompletableFuture.supplyAsync(
                () -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product))))
            .collect(Collectors.toList());
        return completableFutures.stream().map(CompletableFuture::join)
            .collect(Collectors.toList());
    }

    public static void delay() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static class Shop {

        private String name;

        public Shop(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getPrice(String product) {
            return calculatePrice(product);
        }

        /**
         * 异步获取price
         */
        public Future<Double> getPriceAsync(String product) {
           /*
            CompletableFuture<Double> futurePrice = new CompletableFuture<>();
            new Thread(() -> {
                try {
                    double price = calculatePrice(product);
                    futurePrice.complete(price);
                } catch (Exception ex) {
                    futurePrice.completeExceptionally(ex);
                }
            }).start();
            return futurePrice;
            */
            return CompletableFuture.supplyAsync(() -> calculatePrice(product));
        }

        private double calculatePrice(String product) {
            delay();
            return random.nextDouble() * product.charAt(0) + product.charAt(1);
        }
    }
}
