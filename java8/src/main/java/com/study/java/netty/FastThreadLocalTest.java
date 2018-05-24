package com.study.java.netty;

import io.netty.util.concurrent.FastThreadLocal;
import io.netty.util.concurrent.FastThreadLocalThread;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import org.junit.Test;

/**
 * @author Jeffrey
 * @since 2018/04/24 16:54
 */
public class FastThreadLocalTest {

    @Test
    public void testFast() {
        final int threadLocalCount = 1000;
        final FastThreadLocal<String>[] caches = new FastThreadLocal[threadLocalCount];
        final Thread mainThread = Thread.currentThread();
        for (int i = 0; i < threadLocalCount; i++) {
            caches[i] = new FastThreadLocal();
        }
        Thread t = new FastThreadLocalThread(() -> {
            for (int i = 0; i < threadLocalCount; i++) {
                caches[i].set("float.lu");
            }
            long start = System.nanoTime();
            for (int i = 0; i < threadLocalCount; i++) {
                for (int j = 0; j < 1000; j++) {
                    caches[i].get();
                }
            }
            long end = System.nanoTime();
            System.out.println("take[" + TimeUnit.NANOSECONDS.toMillis(end - start) +
                "]ms");
            for (int i = 0; i < threadLocalCount/2; i++) {
                caches[i].remove();
            }
            for (int i = 0; i < threadLocalCount/2; i++) {
                new FastThreadLocal().set("float.lu");
            }
            LockSupport.unpark(mainThread);
        });
        t.start();
        LockSupport.park(mainThread);
    }
}
