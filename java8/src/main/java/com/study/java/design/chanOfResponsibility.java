package com.study.java.design;

import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * @author Jeffrey
 * @since 2017/05/17 11:08
 */
public class chanOfResponsibility {

    public static void main(String[] args) {
        UnaryOperator<String> first = s -> s.toUpperCase();
        UnaryOperator<String> second = s -> s.concat("-append");
        Function<String, Integer> third = String::length;

        Function<String, Integer> pipeline = first.andThen(second).andThen(third);

        String testStr = "123";
        System.out.println(pipeline.apply(testStr));
    }
}
