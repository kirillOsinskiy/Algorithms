package com.osk.sortings;

import com.osk.mycol.BinomialHeap;

public class BinomHeapSort implements Sorting {

    @Override
    public int[] sort(int[] arr) {
        if (arr.length == 0) return new int[0];
        Integer[] integers = new Integer[arr.length];
        for (int i = 0; i < arr.length; i++) {
            integers[i] = arr[i];
        }
        BinomialHeap<Integer> heap = new BinomialHeap<>(integers);

        int[] res = new int[arr.length];
        int i = 0;
        while (i < arr.length) {
            res[i++] = heap.extractMin();
        }

        return res;
    }
}
