package com.study.java.exercise.arithmetic;

import java.util.Iterator;

/**
 * @author Jeffrey
 * @since 2017/06/21 16:41
 */
public class ResizingArrayStack<T> implements Iterable<T> {

    private static final int DEFAULT_SIZE = 16;

    private T[] array;

    private int size = 0;

    public ResizingArrayStack() {
        array = (T[]) new Object[DEFAULT_SIZE];
    }

    public ResizingArrayStack(int max) {
        if (max <= 1) {
            throw new IllegalArgumentException("stack size should be greater than 1");
        }
        array = (T[]) new Object[max];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void push(T element) {
        if (size == array.length) {
            resize(2 * array.length);
        }
        array[size++] = element;
    }

    public T pop() {
        T element = array[--size];
        array[size] = null; //  防止对象游离
        if (size > 0 && size < array.length / 4) {
            resize(array.length / 2);
        }
        return element;
    }

    public int size() {
        return size;
    }

    private void resize(int max) {
        T[] tmp = (T[]) new Object[max];
        for (int i = 0; i < size; i++) {
            tmp[i] = array[i];
        }
        array = tmp;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    private class ReverseArrayIterator implements Iterator<T> {

        @Override
        public boolean hasNext() {
            return size > 0;
        }

        @Override
        public T next() {
            return array[--size];
        }
    }
}
