package com.study.java.concurrent;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.Test;

import static java.lang.System.*;

/**
 * @author Jeffrey
 * @since 2017/03/23 14:01
 */
public class ConcurrentDemo {

    public static AtomicLong number = new AtomicLong(1L);

    public static LongAdder adder = new LongAdder();

    public static LongAccumulator accumulator = new LongAccumulator(Long::sum, 1);

    public static ConcurrentHashMap<String, Long> map = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<String, LongAdder> adderMap = new ConcurrentHashMap<>();

    @Test
    public void test() {
        out.println(number.incrementAndGet());
        out.println(number.updateAndGet(n -> Math.max(n, 1)));
        out.println(number.accumulateAndGet(100, Math::max));
        adder.add(100L);
        adder.increment();
        out.println(adder.intValue());
        accumulator.accumulate(100L);
        out.println(accumulator.longValue());
    }

    @Test
    public void test1() {
        String key = "key";
        Long oldValue;
        Long newValue;
        do {
            oldValue = map.get(key);
            newValue = (oldValue == null) ? 1 : (oldValue + 1);
        } while (!map.replace(key, oldValue, newValue));
        out.println(map.get(key));
    }

    @Test
    public void test2() {
        out.println(adderMap.putIfAbsent("key", new LongAdder()));
        adderMap.putIfAbsent("key", new LongAdder()).increment();
        out.println(adderMap.putIfAbsent("key", new LongAdder()));
        out.println(adderMap.putIfAbsent("key", new LongAdder()));
        out.println(adderMap.putIfAbsent("key", new LongAdder()));
        adderMap.forEach((s, longAdder) -> out.println(s + "," + longAdder.longValue()));
        out.println(adderMap.searchKeys(Long.MAX_VALUE, String::hashCode));
        adderMap.putIfAbsent("key1", new LongAdder());
        out.println("====================");
        adderMap.forEach((s, longAdder) -> out.println(s + "," + longAdder.longValue()));
    }

    @Test
    public void test3() {
        adderMap.compute("key", (s, longAdder) -> longAdder == null ? new LongAdder() : longAdder);
        adderMap.forEach((s, longAdder) -> out.println(s + "," + longAdder.longValue()));
    }

    @Test
    public void test4() {
        IntStream.range(1, 100).mapToObj(Integer::valueOf)
            .collect(Collectors.toMap(String::valueOf, Integer::valueOf));
    }
}
