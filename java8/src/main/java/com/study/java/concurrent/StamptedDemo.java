package com.study.java.concurrent;

import java.util.concurrent.locks.StampedLock;

/**
 * @author Jeffrey
 * @since 2017/06/19 17:45
 */
public class StamptedDemo {

    private double x, y;

    private final StampedLock s1 = new StampedLock();

    public void move(double newX, double newY) {
        long stamp = s1.writeLock();
        try {
            x = newX;
            y = newY;
        } finally {
            s1.unlockWrite(stamp);
        }
    }

    public double distanceFromOrigin() {
        long stamp = s1.tryOptimisticRead();    // 先尝试乐观锁
        double currX = x, currY = y;
        if (!s1.validate(stamp)) {  // 验证
            stamp = s1.readLock();  // 读所
            try {
                currX = x;
                currY = y;
            } finally {
                s1.unlockRead(stamp);
            }
        }
        return Math.sqrt(currX * currX + currY * currY);
    }
}
