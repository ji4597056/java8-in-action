package com.study.java.concurrent;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Jeffrey
 * @since 2017/05/19 13:21
 */
public class ReentrantLockDemo implements Runnable {

    public static final ReentrantLock lock = new ReentrantLock(true);

    @Override
    public void run() {
        while (true) {
            try {
                lock.lock();
                System.out.println(Thread.currentThread().getName() + "获得了锁!");
            }finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        ReentrantLockDemo reentrantLockDemo = new ReentrantLockDemo();
        Thread thread1 = new Thread(reentrantLockDemo);
        Thread thread2 = new Thread(reentrantLockDemo);
        thread1.start();
        thread2.start();
    }
}
