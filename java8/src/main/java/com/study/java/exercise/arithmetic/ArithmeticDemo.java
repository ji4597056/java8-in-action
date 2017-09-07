package com.study.java.exercise.arithmetic;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.stream.IntStream;

/**
 * @author Jeffrey
 * @since 2017/06/21 13:25
 */
public class ArithmeticDemo {

    /**
     * 求两个数的最大公约数
     *
     * @param p 除数
     * @param q 被除数
     */
    public static int gcd(int p, int q) {
        if (q == 0) {
            return p;
        }
        int r = p % q;
        return gcd(q, r);
    }

    /**
     * 二分查找法
     *
     * @param key 查询数字
     * @param a 排序好的数组
     */
    public static int rank(int key, int[] a) {
        int low = 0, high = a.length - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (key > a[mid]) {
                low = mid + 1;
            } else if (key < a[mid]) {
                high = mid - 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    /**
     * 约瑟夫环问题,n个人围在一起,报数m打印
     *
     * @param n 人数
     * @param m 间隔数
     */
    public static void exe_1_3_37(int n, int m) {
        Queue<Integer> queue = new ArrayDeque<>(n);
        IntStream.range(0, n).forEach(queue::offer);
        int k = 0;
        while (!queue.isEmpty()) {
            int val = queue.poll();
            if (++k % m != 0) {
                queue.offer(val);
            } else {
                System.out.print(val + " ");
            }
        }
    }

    /**
     * return number of distinct triples (i, j, k) such that a[i] + a[j] + a[k] = 0
     */
    public static int count(int[] a) {
        int len = a.length;
        int sum = 0;
        Arrays.sort(a);
        for (int i = 0; i < len - 2; i++) {
            for (int j = i + 1; j < len - 1; j++) {
                int k = Arrays.binarySearch(a, j + 1, len, -(a[i] + a[j]));
                if (k > j) {
                    sum++;
                }
            }
        }
        return sum;
    }

    /**
     * 快速排序
     */
    public static void selectSort(int[] a) {
        int len = a.length;
        for (int i = 0; i < len - 1; i++) {
            for (int j = i + 1; j < len; j++) {
                if (a[i] > a[j]) {
                    int tmp = a[i];
                    a[i] = a[j];
                    a[j] = tmp;
                }
            }
        }
        printArray(a);
    }

    /**
     * 插入排序
     */
    public static void insertSort(int[] a) {
        int len = a.length; //  保证局部有序
        for (int i = 1; i < len; i++) {
            for (int j = i; j > 0 && a[j] > a[j - 1]; j--) {
                if (a[j] < a[j - 1]) {
                    int tmp = a[j];
                    a[j - 1] = a[j];
                    a[j] = tmp;
                }
            }
        }
        printArray(a);
    }

    /**
     * 插入排序(改进插入排序)
     */
    public static void insertSort1(int[] a) {
        int len = a.length;
        for (int i = 1; i < len; i++) {
            int tmp = a[i];
            int j;
            for (j = i; j > 0 && less(a[j], a[j - 1]); j--) {
                a[j] = a[j - 1];
            }
            a[j] = tmp;
        }
        printArray(a);
    }

    /**
     * 希尔排序(改进插入排序)
     */
    public static void shellSort(int[] a) {
        int len = a.length;
        int h = 1;
        while (h < len / 3) {
            h = h * 3 + 1;
        }
        while (h >= 1) {
            for (int i = h; i < len; i++) {
                int tmp = a[i];
                int j;
                //  将a[i]插入到a[i-h],a[i-2h]...中
                for (j = i; j >= h && less(a[j], a[j - h]); j--) {
                    a[j] = a[j - h];
                }
                a[j] = tmp;
            }
            h /= 3;
        }
        printArray(a);
    }

    /**
     * 归并排序
     */
    public static void mergeSort(int[] a, int left, int right, int[] tmp) {
        int mid = (left + right) / 2;
        if (left < right) {
            mergeSort(a, left, mid, tmp);
            mergeSort(a, mid + 1, right, tmp);
            merge(a, left, mid, right, tmp);
        }
    }

    public static void merge(int[] a, int left, int mid, int right, int[] tmp) {
        int i = left, j = mid + 1, k = 0;
        while (i <= mid && j <= right) {
            if (less(a[i], a[j])) {
                tmp[k++] = a[i++];
            } else {
                tmp[k++] = a[j++];
            }
        }
        while (i <= mid) {
            tmp[k++] = a[i++];
        }
        while (j <= right) {
            tmp[k++] = a[j++];
        }
        // 覆盖
        for (i = 0; i < k; i++) {
            a[left + i] = tmp[i];
        }
    }

    private static boolean less(int a, int b) {
        return a < b;
    }

    private static void printArray(int[] a) {
        Arrays.stream(a).forEach(i -> System.out.print(i + " "));
        System.out.println();
    }

    /**
     * 验证
     */
    public static void main(String[] args) {
        int[] a = new int[]{1, 8, 3, 2, 4, 1, 5, 7, 6, 10, 9};
        int left = 0, right = a.length - 1;
        int[] tmp = new int[a.length];
        mergeSort(a, left, right, tmp);
        printArray(a);
    }
}
