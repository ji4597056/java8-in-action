package com.study.java.design.singleton;

/**
 * 单例模式(内部类方式)
 *
 * @author Jeffrey
 * @since 14/05/2017 11:58 PM
 */
public class Singleton {

    private Singleton() {

    }

    public static Singleton getInstance() {
        return HolderClass.instance;
    }

    private static class HolderClass {

        public static final Singleton instance = new Singleton();
    }
}
