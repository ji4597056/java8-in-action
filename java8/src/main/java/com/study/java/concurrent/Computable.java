package com.study.java.concurrent;

/**
 * @author Jeffrey
 * @since 2017/06/06 14:38
 */
public interface Computable<A, V> {

    V compute(A arg);
}
