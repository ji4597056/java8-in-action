package com.study.java.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @author Jeffrey
 * @since 2017/06/19 13:41
 */
public class AtomicIntegerFieldUpdaterDemo {

    public static class Candidate {

        int id;

        volatile int score;
    }

    public static AtomicInteger allScore = new AtomicInteger(0);

    //1.对于scoreUpdater,score必须可见,不能是private 2.底层是CAS,score必须是volatile 3.不支持static
    public final static AtomicIntegerFieldUpdater<Candidate> scoreUpdater = AtomicIntegerFieldUpdater
        .newUpdater(Candidate.class, "score");

    public static void main(String[] args) throws InterruptedException {
        final Candidate stu = new Candidate();
        final CountDownLatch latch = new CountDownLatch(1);
        Thread[] t = new Thread[10000];
        for (int i = 0; i < 10000; i++) {
            t[i] = new Thread(() -> {
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (Math.random() > 0.4) {
                    scoreUpdater.incrementAndGet(stu);
//                    stu.score++;
                    allScore.incrementAndGet();
                }
            });
            t[i].start();
        }
        latch.countDown();
        for (int i = 0; i < 10000; i++) {
            t[i].join();
        }
        System.out.println("score=" + stu.score);
        System.out.println("allScore=" + allScore);
    }
}
