package com.study.java.exercise.arithmetic.sort;

import java.util.Arrays;
import org.junit.Test;

/**
 * 堆排序
 *
 * @author Jeffrey
 * @since 2018/05/17 9:42
 */
public class HeapSortDemo {

    public int[] sort(int[] sourceArray) {
        // 对 arr 进行拷贝，不改变参数内容
        int[] arr = Arrays.copyOf(sourceArray, sourceArray.length);

        int len = arr.length;

        buildMaxHeap(arr, len);

        for (int i = len - 1; i > 0; i--) {
            // 交换头尾,重新heapify头
            swap(arr, 0, i);
            heapify(arr, 0, --len);
        }
        return arr;
    }

    private void buildMaxHeap(int[] arr, int len) {
        for (int i = (int) Math.floor(len / 2); i >= 0; i--) {
            heapify(arr, i, len);
        }
    }

    private void heapify(int[] arr, int i, int len) {
        int left = i * 2 + 1;
        int right = i * 2 + 2;
        int largest = i;
        if (left < len && arr[left] > arr[largest]) {
            largest = left;
        }
        if (right < len && arr[right] > arr[largest]) {
            largest = right;
        }
        if (largest != i) {
            swap(arr, largest, i);
            // 交换后可能导致子节点不满足性质
            heapify(arr, largest, len);
        }
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    @Test
    public void test() {
        int[] array = new int[]{5, 4, 3, 2, 1};
        array = sort(array);
        Arrays.stream(array).forEach(i -> System.out.print(i + " "));
        System.out.println();
        System.out.println("===============");
        array = new int[]{5, 9, 4, 7, 2, 8, 1, 3, 6};
        array = sort(array);
        Arrays.stream(array).forEach(i -> System.out.print(i + " "));
    }
}
