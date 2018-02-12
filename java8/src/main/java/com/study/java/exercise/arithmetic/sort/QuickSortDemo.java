package com.study.java.exercise.arithmetic.sort;

import java.util.Arrays;
import org.junit.Test;

/**
 * 插入排序
 *
 * @author Jeffrey
 * @since 2018/02/08 14:28
 */
public class QuickSortDemo {

    public static void sort(int[] array) {
        sort(array, 0, array.length - 1);
    }

    private static void sort(int[] array, int low, int high) {
        if (low >= high) {
            return;
        }
        int j = partition(array, low, high);
        sort(array, low, j - 1);
        sort(array, j + 1, high);
    }

    private static int partition(int[] array, int low, int high) {
        int i = low, j = high + 1;
        int v = array[low];
        while (true) {
            while (array[++i] < v) {
                if (i == high) {
                    break;
                }
            }
            while (array[--j] > v) {
                if (j == low) {
                    break;
                }
            }
            if (i >= j) {
                break;
            }
            int tmp = array[i];
            array[i] = array[j];
            array[j] = tmp;
        }
        array[low] = array[j];
        array[j] = v;
        return j;
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
