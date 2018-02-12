package com.study.java.exercise.arithmetic.sort;

import java.util.Arrays;
import org.junit.Test;

/**
 * 归并排序(先递归,再合并)
 *
 * @author Jeffrey
 * @since 2018/02/02 13:55
 */
public class MergeSortDemo {

    private static int[] tmpArray;

    public static void sort(int[] array) {
        tmpArray = new int[array.length];
        sort(array, 0, array.length - 1);
    }

    public static void sort(int[] array, int left, int right) {
        if (left >= right) {
            return;
        }
        int mid = (left + right) / 2;
        sort(array, 0, mid);
        sort(array, mid + 1, right);
        // 判断有序,则无序merge
        if (array[mid] > array[mid + 1]) {
            merge(array, left, mid, right);
        }
    }

    private static void merge(int[] array, int left, int mid, int right) {
        int i = left;
        int j = mid + 1;
        for (int k = left; k <= right; k++) {
            tmpArray[k] = array[k];
        }
        for (int k = left; k <= right; k++) {
            if (i > mid) {
                array[k] = tmpArray[j++];
            } else if (j > right) {
                array[k] = tmpArray[i++];
            } else if (tmpArray[i] < tmpArray[j]) {
                array[k] = tmpArray[i++];
            } else {
                array[k] = tmpArray[j++];
            }
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
