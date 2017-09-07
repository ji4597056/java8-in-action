package com.study.java.concurrent;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Jeffrey
 * @since 2017/06/12 17:31
 */
public class ConcurrentMapDemo {

    private static ConcurrentMap<String, Integer> cache = new ConcurrentHashMap();

    public Integer get(String key) {
        return cache.get(key);
    }

    public void add(String key) {
        while (true) {
            Integer old = cache.get(key);
            if (old == null) {
                if (cache.putIfAbsent(key, 0) == null) {
                    break;
                }
            } else {
                if (cache.replace(key, old, old + 1)) {
                    break;
                }
            }
        }
    }
}
