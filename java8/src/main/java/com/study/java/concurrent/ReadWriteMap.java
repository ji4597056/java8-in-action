package com.study.java.concurrent;

import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Jeffrey
 * @since 2017/06/08 14:57
 */
public class ReadWriteMap<K, V> {

    private final Map<K, V> map;

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    private final Lock rlock = lock.readLock();

    private final Lock wlock = lock.writeLock();

    public ReadWriteMap(Map<K, V> map) {
        this.map = map;
    }

    public V put(K key, V value) {
        wlock.lock();
        try {
            return map.put(key, value);
        } finally {
            wlock.unlock();
        }
    }

    public V get(K key) {
        rlock.lock();
        try {
            return map.get(key);
        } finally {
            rlock.unlock();
        }
    }
}
