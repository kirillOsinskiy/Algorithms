package com.osk.sortings;

import com.osk.mycol.Heap;
import com.osk.mycol.Utils;

public class HeapSort implements Sorting {

    private final int k;

    public HeapSort(int k) {
        this.k = k;
    }

    @Override
    public int[] sort(int[] arr) {
        if(arr.length == 0) return new int[0];
        Integer[] buf = new Integer[arr.length];
        for (int i = 0; i < arr.length; i++) {
            buf[i] = arr[i];
        }
        Heap<Integer> heap = k == 0 ? Utils.makeBinaryHeap(buf) : Utils.makeKHeap(k, buf);
        int[] res = new int[arr.length];
        int i = 0;
        while (i < arr.length) {
            res[i++] = heap.extractMin();
        }
        return res;
    }
}
