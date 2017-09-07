package com.study.java.design.singleton;

/**
 * 单例模式(懒加载)
 *
 * @author Jeffrey
 * @since 14/05/2017 11:57 PM
 */
public class LazySingleton {

    private static volatile LazySingleton instance = null;

    private LazySingleton() {

    }

    public static LazySingleton getInstance() {
        if (instance == null) {
            synchronized (LazySingleton.class) {
                if (instance == null) {
                    instance = new LazySingleton();
                }
            }
        }
        return instance;
    }
}
