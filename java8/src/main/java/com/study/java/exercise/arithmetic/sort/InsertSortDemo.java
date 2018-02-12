package com.study.java.exercise.arithmetic.sort;

import java.util.Arrays;
import org.junit.Test;

/**
 * 插入排序
 *
 * @author Jeffrey
 * @since 2018/02/02 17:25
 */
public class InsertSortDemo {

    public static void sort(int[] array) {
        if (array == null) {
            return;
        }
        for (int i = 1; i < array.length; i++) {
            int tmp = array[i];
            int j = i - 1;
            for (; j >= 0 && array[j] > tmp; j--) {
                array[j + 1] = array[j];
            }
            array[j + 1] = tmp;
        }
    }

    @Test
    public void test() {
        int[] array = new int[]{5, 4, 3, 2, 1};
        sort(array);
        Arrays.stream(array).forEach(i -> System.out.print(i + " "));
        System.out.println();
        System.out.println("===============");
        array = new int[]{5, 9, 4, 7, 2, 8, 1, 3, 6};
        sort(array);
        Arrays.stream(array).forEach(i -> System.out.print(i + " "));
    }
}
