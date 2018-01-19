package com.study.java.stream;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Test;

/**
 * @author Jeffrey
 * @since 2017/12/22 13:19
 */
public class StreamTest {

    @Test
    public void test() {
        List<Integer> list = Lists.newArrayList();
        System.out.println(
            Stream.of(1, 2, 3, 4, 5).peek(list::add).skip(2).limit(3).collect(Collectors.toList()));
        System.out.println(list.size());
        boolean success = false;
    }

    public void test1() throws NullPointerException {
        System.out.println("123");
    }

    public void test2() throws IOException {
        System.out.println("123");
    }

    public void test3() throws RuntimeException {
        try {
            test2();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        StreamTest test = new StreamTest();
        test.test1();
        try {
            test.test2();
        } catch (IOException e) {
            e.printStackTrace();
        }
        test.test3();
    }

}
