package com.study.java.design.singleton;

/**
 * 单例模式(饿加载)
 *
 * @author Jeffrey
 * @since 14/05/2017 11:57 PM
 */
public class EagerSingleton {

    private static final EagerSingleton instance = new EagerSingleton();

    private EagerSingleton() {

    }

    public static EagerSingleton getInstance() {
        return instance;
    }
}
