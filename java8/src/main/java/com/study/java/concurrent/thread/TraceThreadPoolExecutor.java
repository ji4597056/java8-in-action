package com.study.java.concurrent.thread;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * @author Jeffrey
 * @since 2017/06/16 12:37
 */
public class TraceThreadPoolExecutor extends ThreadPoolExecutor {

    public TraceThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime,
        TimeUnit unit,
        BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        Logger.getGlobal().info("after execute");
        super.afterExecute(r, t);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        Logger.getGlobal().info("before execute");
        super.beforeExecute(t, r);
    }

    @Override
    protected void terminated() {
        Logger.getGlobal().info("terminated");
        super.terminated();
    }

    @Override
    public Future<?> submit(Runnable task) {
        // 打印提交任务的线程池堆栈信息(能够打印日志的方法:1.exectue,2.future.get())
        return super.submit(wrap(task, clientTrace(), Thread.currentThread().getName()));
    }

    private Exception clientTrace() {
        return new Exception("Client stack trace");
    }

    private Runnable wrap(final Runnable task, final Exception clientStace,
        final String treadName) {
        return () -> {
            try {
                task.run();
            } catch (Exception e) {
                clientStace.printStackTrace();
                throw e;
            }
        };
    }

    public static void main(String[] args) throws IOException {
        ExecutorService executorService = new TraceThreadPoolExecutor(0, Integer.MAX_VALUE, 0L,
            TimeUnit.SECONDS, new SynchronousQueue<>());
        executorService.submit(() -> System.out.println(1/1));
        executorService.submit(() -> System.out.println(1/0));
        System.in.read();
    }
}
