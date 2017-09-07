package com.study.java.cache.ehcache;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * @author Jeffrey
 * @since 2017/05/31 10:02
 */
public class EhcacheTest {

    private static final String DEFAULT_CACHE_NAME = "myCache";

    private static final String DEFAULT_CACHE_CONFIG_PATH = "ehcache.xml";

    private static final Integer MAP_INITIAL_SIZE = 10;

    private CacheManager manager;

    private Cache cache;

    private ConcurrentHashMap<String, String> map;

    public static void main(String[] args) throws UnsupportedEncodingException {
        EhcacheTest ehcacheTest = new EhcacheTest();
        ehcacheTest.initial();
        IntStream.rangeClosed(1, 1000)
            .forEach(value -> ehcacheTest.setValue(String.valueOf(value), String.valueOf(value)));
        ehcacheTest.useEhcache(ehcacheTest.cache);
        ehcacheTest.useMap(ehcacheTest.map);
    }

    private void useEhcache(Cache cache) {
        long startTime = System.currentTimeMillis();
        IntStream.rangeClosed(1, 1000).forEach(
            value -> System.out.println(cache.get(String.valueOf(value)).getObjectValue()));
        System.out.println("========================");
        System.out.println("cost time:" + (System.currentTimeMillis() - startTime));
    }

    private void useMap(Map map) {
        long startTime = System.currentTimeMillis();
        IntStream.rangeClosed(1, 1000)
            .forEach(value -> System.out.println(map.get(String.valueOf(value))));
        System.out.println("========================");
        System.out.println("cost time:" + (System.currentTimeMillis() - startTime));
    }

    private void setValue(String key, String value) {
        cache.put(new Element(key, value));
        map.put(key, value);
    }

    private void initial() {
        // 初始化 ehcache
        try {
            manager = CacheManager.newInstance(URLDecoder.decode(
                EhcacheTest.class.getClassLoader().getResource(DEFAULT_CACHE_CONFIG_PATH).getPath(),
                "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        cache = manager.getCache(DEFAULT_CACHE_NAME);
        // 初始化 map
        map = new ConcurrentHashMap<>(MAP_INITIAL_SIZE);
    }
}
