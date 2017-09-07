package com.study.java.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;
import org.junit.Test;

/**
 * @author Jeffrey
 * @since 2017/06/06 13:34
 */
public class CountDownLatchTest {

    @Test
    public void test() throws Exception {
        CountDownLatchTest countDownLatchTest = new CountDownLatchTest();
        System.out.println(
            countDownLatchTest.timeTask(10, () -> System.out.println("Hello,I'm Jeffrey!")));
    }

    private long timeTask(int threadNum, final Runnable task) throws InterruptedException {
        final CountDownLatch startCount = new CountDownLatch(1);
        final CountDownLatch taskCount = new CountDownLatch(threadNum);
        IntStream.rangeClosed(1, threadNum).forEach(i -> {
            Thread thread = new Thread(() -> {
                try {
                    startCount.await();
                    task.run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    taskCount.countDown();
                }
            });
            thread.start();
        });
        long startTime = System.nanoTime();
        startCount.countDown();
        taskCount.await();
        return System.nanoTime() - startTime;
    }
}
