package com.study.java.concurrent;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * @author Jeffrey
 * @since 2017/06/06 14:42
 */
public class Memoizer<A, V> implements Computable<A, V> {

    private final ConcurrentHashMap<A, Future<V>> cache = new ConcurrentHashMap<>();

    private final Computable<A, V> computable;

    public Memoizer(Computable<A, V> computable) {
        this.computable = computable;
    }

    @Override
    public V compute(A arg) {
        while (true) {
            Future<V> future = cache.get(arg);
            if (future == null) {
                FutureTask<V> futureTask = new FutureTask<>(() -> computable.compute(arg));
                future = cache
                    .putIfAbsent(arg, futureTask);    // putIfAbsent()返回先前的value,返回null表示第一次设值
                if (future == null) {
                    future = futureTask;
                    futureTask.run();
                }
            }
            try {
                return future.get();
            } catch (CancellationException e) {
                cache.remove(arg, future);
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e.getCause());
            }
        }
    }
}
