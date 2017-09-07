package com.study.java.concurrent;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Jeffrey
 * @since 2017/06/07 15:29
 */
public class MyAppThread extends Thread {

    public static final String DEFAULT_NAME = "MyAppThread";

    private static volatile boolean debugLifeCycle = false;

    private static final AtomicInteger creaded = new AtomicInteger();

    private static final AtomicInteger alived = new AtomicInteger();

    private static final Logger logger = Logger.getAnonymousLogger();

    public MyAppThread(Runnable runnable, String name) {
        super(runnable, DEFAULT_NAME + "-" + name);
        setDefaultUncaughtExceptionHandler((t, e) -> {
            logger.log(Level.SEVERE, "Uncaught error in thread " + t.getName() + e);
        });
    }

    public MyAppThread(Runnable runnable) {
        this(runnable, DEFAULT_NAME);
    }

    @Override
    public void run() {
        boolean debug = debugLifeCycle;
        if (debug) {
            logger.log(Level.FINE, "Create " + getName());
        }
        try {
            alived.incrementAndGet();
            super.run();
        } finally {
            alived.decrementAndGet();
            logger.log(Level.FINE, "Exiting " + getName());
        }
    }

    public static Integer getAlived() {
        return alived.get();
    }

    public static Integer getCreated() {
        return creaded.get();
    }

    public static Boolean getDebug() {
        return debugLifeCycle;
    }

    public static void setDebug() {
        debugLifeCycle = true;
    }
}
