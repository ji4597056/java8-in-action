package com.study.java.design.singleton;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Jeffrey
 * @since 2017/04/13 15:35
 */
public class Demo {

    // 使用volatile保证instanceChm被初始化后能被其他线程立刻看见
    private volatile ConcurrentHashMap instanceChm;

    public void doOperation(){
        ConcurrentHashMap chm = instanceChm;
        if (chm == null) {
            synchronized (this) {
                chm = instanceChm;
                if (chm == null) {
                    chm = new ConcurrentHashMap();
                    // 初始化chm
                    instanceChm = chm;
                }
            }
        }
        // dosomething
    }
}
