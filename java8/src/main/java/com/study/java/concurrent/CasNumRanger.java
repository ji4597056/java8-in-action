package com.study.java.concurrent;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Jeffrey
 * @since 2017/06/08 17:37
 */
public class CasNumRanger {

    private final AtomicReference<IntPair> values = new AtomicReference<>(new IntPair(0, 0));

    public Integer getLow() {
        return values.get().getLow();
    }

    public Integer getHigh() {
        return values.get().getHigh();
    }

    public void setLow(Integer low) {
        while (true) {
            IntPair oldValue = values.get();
            if (low > oldValue.getHigh()) {
                throw new IllegalArgumentException("low-value should not higher than high-value.");
            }
            IntPair newValue = new IntPair(low, oldValue.getHigh());
            if (values.compareAndSet(oldValue, newValue)) {
                return;
            }
        }
    }

    public void setHigh(Integer high) {
        while (true) {
            IntPair oldValue = values.get();
            if (high < oldValue.getLow()) {
                throw new IllegalArgumentException("high-value should not lower than low-value.");
            }
            IntPair newValue = new IntPair(oldValue.getLow(), high);
            if (values.compareAndSet(oldValue, newValue)) {
                return;
            }
        }
    }

    private static class IntPair {

        private final Integer low;
        private final Integer high;

        private IntPair(Integer low, Integer high) {
            this.low = low;
            this.high = high;
        }

        public int getLow() {
            return low;
        }

        public int getHigh() {
            return high;
        }
    }
}
