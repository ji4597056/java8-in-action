package com.study.java.concurrent.akka;

import co.paralleluniverse.fibers.FiberUtil;
import co.paralleluniverse.fibers.Suspendable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author Jeffrey
 * @since 2017/08/30 14:55
 */
public class QuasarDemo {

    final CountDownLatch countDownLatch;

    public QuasarDemo(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Suspendable
    public void test(String o) {
        try {
            TimeUnit.SECONDS.sleep(1L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("received" + o + ",thread id:" + Thread.currentThread().getId());
        countDownLatch.countDown();
    }

    public static void main(String[] args) throws InterruptedException {
        final int testNum = 10000;
        final CountDownLatch latch = new CountDownLatch(testNum);
        QuasarDemo quasarDemo = new QuasarDemo(latch);
        long start = System.currentTimeMillis();
        IntStream.range(0, testNum).forEach(i -> {
            try {
                FiberUtil.runInFiber(() -> quasarDemo.test("test" + i));
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        });
        latch.wait();
        System.out.println("cost:" + (System.currentTimeMillis() - start));
    }

}
