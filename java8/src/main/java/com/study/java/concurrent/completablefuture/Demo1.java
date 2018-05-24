package com.study.java.concurrent.completablefuture;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sun.plugin.com.event.COMEventHandler;

/**
 * @author Jeffrey
 * @since 2017/12/12 14:56
 */
public class Demo1 {

    private static String text;

    private static long start;

    @Before
    public void before() {
        text = "A B C D F";
        start = System.currentTimeMillis();
    }

    @After
    public void after() {
        System.out.println("cost time:" + (System.currentTimeMillis() - start) + "(ms)");
    }

    public String init(String text) {
        System.out.println("'init' method thread:" + Thread.currentThread().getName());
        try {
            TimeUnit.SECONDS.sleep(1L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return text;
    }

    public String getFirst(String text) {
        System.out.println("'getFirst' method thread:" + Thread.currentThread().getName());
        try {
            TimeUnit.SECONDS.sleep(1L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return text.substring(0, text.indexOf(" "));
    }

    public String getLast(String text) {
        System.out.println("'getLast' method thread:" + Thread.currentThread().getName());
        try {
            TimeUnit.SECONDS.sleep(2L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return text.substring(text.lastIndexOf(" ") + 1);
    }

    public String concat(String first, String last) {
        System.out.println("'concat' method thread:" + Thread.currentThread().getName());
        return first + "/" + last;
    }

    public String concatForSeconds(String first, String last) {
        System.out.println("'concatForSeconds' method thread:" + Thread.currentThread().getName());
        try {
            TimeUnit.SECONDS.sleep(1L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return first + "/" + last;
    }

    @Test
    public void test1() {
        CompletableFuture<String> initFuture = CompletableFuture.supplyAsync(() -> init(text));
        CompletableFuture<String> firstFuture = initFuture.thenApply(this::getFirst);
        CompletableFuture<String> lastFuture = initFuture.thenApply(this::getLast);
        firstFuture.thenCombine(lastFuture, this::concat).thenAccept(System.out::println).join();
    }

    /*
        'init' method thread:ForkJoinPool.commonPool-worker-1
        'getLast' method thread:ForkJoinPool.commonPool-worker-1
        'getFirst' method thread:ForkJoinPool.commonPool-worker-1
        'concat' method thread:ForkJoinPool.commonPool-worker-1
        A/F
        cost time:4130(ms)
     */

    @Test
    public void test2() {
        CompletableFuture<String> initFuture = CompletableFuture.supplyAsync(() -> init(text));
        CompletableFuture<String> firstFuture = initFuture.thenApplyAsync(this::getFirst);
        CompletableFuture<String> lastFuture = initFuture.thenApplyAsync(this::getLast);
        firstFuture.thenCombine(lastFuture, this::concat).thenAccept(System.out::println).join();
    }

    /*
        'init' method thread:ForkJoinPool.commonPool-worker-1
        'getLast' method thread:ForkJoinPool.commonPool-worker-1
        'getFirst' method thread:ForkJoinPool.commonPool-worker-2
        'concat' method thread:ForkJoinPool.commonPool-worker-1
        A/F
        cost time:3155(ms)
     */

    @Test
    public void test3() {
        CompletableFuture<String> initFuture = CompletableFuture.supplyAsync(() -> init(text));
        CompletableFuture<String> firstFuture = initFuture
            .thenCompose(s -> CompletableFuture.supplyAsync(() -> this.getFirst(s)));
        CompletableFuture<String> lastFuture = initFuture
            .thenCompose(s -> CompletableFuture.supplyAsync(() -> this.getLast(s)));
        firstFuture.thenCombine(lastFuture, this::concat).thenAccept(System.out::println).join();
    }
    /*
        'init' method thread:ForkJoinPool.commonPool-worker-1
        'getLast' method thread:ForkJoinPool.commonPool-worker-1
        'getFirst' method thread:ForkJoinPool.commonPool-worker-2
        'concat' method thread:ForkJoinPool.commonPool-worker-1
        A/F
        cost time:3238(ms)
     */

    @Test
    public void test4() {
        CompletableFuture<String> initFuture = CompletableFuture.supplyAsync(() -> init(text));
        CompletableFuture<String> firstFuture = initFuture.thenApplyAsync(this::getFirst);
        CompletableFuture<String> lastFuture = initFuture.thenApplyAsync(this::getLast);
        CompletableFuture<String> combineFuture1 = firstFuture
            .thenCombineAsync(lastFuture, this::concatForSeconds);
        CompletableFuture<String> combineFuture2 = lastFuture
            .thenCombineAsync(firstFuture, this::concatForSeconds);
        combineFuture1.thenCombine(combineFuture2, (s1, s2) -> {
            System.out.println("s1:" + s1 + ",s2:" + s2);
            return null;
        }).join();
    }
    /*
        'init' method thread:ForkJoinPool.commonPool-worker-1
        'getLast' method thread:ForkJoinPool.commonPool-worker-1
        'getFirst' method thread:ForkJoinPool.commonPool-worker-2
        'concatForSeconds' method thread:ForkJoinPool.commonPool-worker-1
        'concatForSeconds' method thread:ForkJoinPool.commonPool-worker-2
        s1:A/F,s2:F/A
        cost time:4137(ms)
     */

    @Test
    public void test13() {
        CompletableFuture<String> initFuture = CompletableFuture.supplyAsync(() -> init(text));
        CompletableFuture<String> firstFuture = initFuture.thenApplyAsync(this::getFirst);
        CompletableFuture<String> lastFuture = initFuture.thenApplyAsync(this::getLast);
        CompletableFuture<String> combineFuture1 = firstFuture
            .thenCombine(lastFuture, this::concatForSeconds);
        CompletableFuture<String> combineFuture2 = lastFuture
            .thenCombine(firstFuture, this::concatForSeconds);
        combineFuture1.thenCombine(combineFuture2, (s1, s2) -> {
            System.out.println("s1:" + s1 + ",s2:" + s2);
            return null;
        }).join();
    }
    /*
        'init' method thread:ForkJoinPool.commonPool-worker-1
        'getLast' method thread:ForkJoinPool.commonPool-worker-1
        'getFirst' method thread:ForkJoinPool.commonPool-worker-2
        'concatForSeconds' method thread:ForkJoinPool.commonPool-worker-1
        'concatForSeconds' method thread:ForkJoinPool.commonPool-worker-1
        s1:A/F,s2:F/A
        cost time:5154(ms)
     */

    @Test
    public void test5() {
        CompletableFuture.supplyAsync(() -> init(text)).thenApplyAsync(this::getFirst)
            .thenApplyAsync(this::getLast).thenAccept(System.out::println).join();
    }

    @Test
    public void test6() {
        CompletableFuture.supplyAsync(() -> init(text))
            .thenCompose(s -> CompletableFuture.supplyAsync(() -> concat(getFirst(s), getLast(s))))
            .thenAccept(System.out::println).join();
    }
    /*
        'init' method thread:ForkJoinPool.commonPool-worker-1
        'getFirst' method thread:ForkJoinPool.commonPool-worker-1
        'getLast' method thread:ForkJoinPool.commonPool-worker-1
        'concat' method thread:ForkJoinPool.commonPool-worker-1
        A/F
        cost time:4190(ms)
     */

    @Test
    public void test7() {
        CompletableFuture.supplyAsync(() -> init(text))
            .thenCompose(s -> CompletableFuture.supplyAsync(() -> getFirst(s))
                .thenCombine(CompletableFuture.supplyAsync(() -> getLast(s)), this::concat))
            .thenAccept(System.out::println).join();
    }
    /*
        'init' method thread:ForkJoinPool.commonPool-worker-1
        'getFirst' method thread:ForkJoinPool.commonPool-worker-1
        'getLast' method thread:ForkJoinPool.commonPool-worker-2
        'concat' method thread:ForkJoinPool.commonPool-worker-2
        A/F
        cost time:3155(ms)
     */

    @Test
    public void test8() {
        CompletableFuture.supplyAsync(() -> init(text)).thenCompose(
            s -> CompletableFuture.supplyAsync(() -> getFirst(s))
                .acceptEither(CompletableFuture.supplyAsync(() -> getLast(s)), System.out::println))
            .join();
    }

    @Test
    public void test9() {
        CompletableFuture.supplyAsync(() -> init(text)).thenCompose(
            s -> CompletableFuture.supplyAsync(() -> getFirst(s))
                .applyToEither(CompletableFuture.supplyAsync(() -> getLast(s)), String::toString))
            .thenAccept(System.out::println)
            .join();
    }

    @Test
    public void test10() {
        List<String> texts = getTexts(10);
        texts.stream().map(
            text -> CompletableFuture.supplyAsync(() -> getFirst(text), threadPool)
                .thenCombine(CompletableFuture.supplyAsync(() -> getLast(text), threadPool),
                    this::concat).thenAccept(System.out::println)).map(CompletableFuture::join)
            .collect(Collectors.toList());
    }
    /*
        cost time:20213(ms)
     */

    @Test
    public void test11() {
        List<String> texts = getTexts(10);
        CompletableFuture<Void>[] futures = texts.stream()
            .map(text -> CompletableFuture.supplyAsync(() -> getFirst(text), threadPool)
                .thenCombine(CompletableFuture.supplyAsync(() -> getLast(text), threadPool),
                    this::concat)
                .thenAccept(System.out::println)).toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(futures).exceptionally(throwable -> {
            System.out.println("throwable message:" + throwable.getMessage());
            return null;
        }).join();
    }
    /*
        cost time:2206(ms)
     */

    @Test
    public void test12() {
        List<String> texts = getTexts(10);
        List<CompletableFuture<String>> futures = texts.stream().map(
            text -> CompletableFuture.supplyAsync(() -> getFirst(text), threadPool)
                .thenCombine(CompletableFuture.supplyAsync(() -> getLast(text), threadPool),
                    this::concat)).collect(Collectors.toList());
        futures.stream().map(CompletableFuture::join).collect(Collectors.toList())
            .forEach(System.out::println);
    }
    /*
        cost time:2221(ms)
     */

    private List<String> getTexts(int size) {
        List<String> origin = Lists.newArrayList("A", "B", "C", "D", "E", "F");
        List<String> texts = Lists.newArrayList();
        for (int i = 0; i < size; i++) {
            texts.add(origin.stream().collect(Collectors.joining(" ")));
            Collections.shuffle(texts);
        }
        return texts;
    }

    private static Executor threadPool = Executors.newFixedThreadPool(20);

    @Test
    public void test14() {
        long start = System.currentTimeMillis();
        Obj obj = new Obj();
        CompletableFuture f1 = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(1/0);
            obj.setK1("k1");
        }).exceptionally(throwable -> {
            System.out.println("========1========");
            return null;
        });
        CompletableFuture f2 = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(1/0);
            obj.setK2("k2");
        });
        CompletableFuture f3 = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(1500L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            obj.setK3("k3");
        }).exceptionally(throwable -> {
            System.out.println("============3=========");
            return null;
        });
        CompletableFuture.allOf(f1,f2,f3).exceptionally(throwable -> {
            System.out.println(throwable.getMessage());
            return null;
        }).join();
        System.out.println("cost:" + (System.currentTimeMillis() - start));
        System.out.println(obj);
    }

    static class Obj {

        private String k1;

        private String k2;

        private String k3;

        public String getK1() {
            return k1;
        }

        public void setK1(String k1) {
            this.k1 = k1;
        }

        public String getK2() {
            return k2;
        }

        public void setK2(String k2) {
            this.k2 = k2;
        }

        public String getK3() {
            return k3;
        }

        public void setK3(String k3) {
            this.k3 = k3;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Obj{");
            sb.append("k1='").append(k1).append('\'');
            sb.append(", k2='").append(k2).append('\'');
            sb.append(", k3='").append(k3).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }

}
