package com.study.java.lambda.map;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jeffrey
 * @since 2017/03/10 13:26
 */
public class MapTest {

    public static void main(String[] args) throws NoSuchFieldException {
        String url = args[0];
        StringBuilder sb = new StringBuilder("" + url);
        sb.append("13");
        Map<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");
        map.forEach((Key, value) -> {
            sb.append(value);
        });

    }
}
