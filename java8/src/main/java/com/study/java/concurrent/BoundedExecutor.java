package com.study.java.concurrent;

import java.util.concurrent.Executor;
import java.util.concurrent.Semaphore;

/**
 * @author Jeffrey
 * @since 2017/06/07 15:10
 */
public class BoundedExecutor {

    private final Executor executor;

    // 注入率(等于 线程池大小+期待等待队列大小)
    private final Semaphore semaphore;

    public BoundedExecutor(Executor executor, int bound) {
        this.executor = executor;
        this.semaphore = new Semaphore(bound);
    }

    public void submitTask(final Runnable cmd) {
        try {
            semaphore.acquire();
            executor.execute(() -> {
                try {
                    cmd.run();
                } finally {
                    semaphore.release();
                }
            });
        } catch (InterruptedException e) {
            semaphore.release();
        }
    }
}
