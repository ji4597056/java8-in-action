package com.study.java.common;

import static java.util.stream.Collectors.toList;

import java.time.ZoneId;
import java.util.stream.Stream;
import org.junit.Test;

/**
 * @author Jeffrey
 * @since 2017/03/24 20:14
 */
public class StringDemo {

    @Test
    public void test() {
        String joined = String.join(",", "Hi", "大家", "你们好");
        System.out.println(joined);
        String ids = String.join(", ", ZoneId.getAvailableZoneIds());
        System.out.println(ids);
        String lists = String.join(", ", Stream.of("你", "们", "好").collect(toList()));
        System.out.println(lists);
    }
}
