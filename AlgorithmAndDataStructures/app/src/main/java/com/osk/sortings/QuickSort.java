package com.osk.sortings;

public class QuickSort implements Sorting {

    @Override
    public int[] sort(int[] arr) {
        if (arr.length > 1) {
            sort(arr, 0, arr.length - 1);
        }
        return arr;
    }

    void sort(int[] arr, int begin, int end) {
        if (begin >= end) {
            return;
        }
        int pivot_idx = partition(arr, begin, end);
        sort(arr, begin, pivot_idx);
        sort(arr, pivot_idx + 1, end);
    }

    int partition(int[] arr, int begin, int end) {
        int pivot_idx = begin - (begin - end) / 2;
        int idx_start = begin;
        int idx_end = end;
        while (idx_start < idx_end) {

            while (arr[idx_start] < arr[pivot_idx] && idx_start < pivot_idx)
                idx_start++;

            while (arr[idx_end] >= arr[pivot_idx] && idx_end > pivot_idx)
                idx_end--;

            if (idx_start < idx_end) {
                int buf = arr[idx_start];
                arr[idx_start] = arr[idx_end];
                arr[idx_end] = buf;

                if (idx_start == pivot_idx)
                    pivot_idx = idx_end;
                else if (idx_end == pivot_idx)
                    pivot_idx = idx_start;

                if(idx_start < pivot_idx) idx_start++;
                if(idx_end > pivot_idx) idx_end--;
            }
        }
        return idx_end;
    }
}
