package com.osk.sortings;

public class InsertedSort implements Sorting{

    @Override
    public int[] sort(int[] arr) {
        return sort(arr, 0, arr.length);
    }

    public int[] sort(int[] arr, int begin, int end) {
        int iter = begin;
        while (iter < end) {
            int cur_el = arr[iter];
            for (int i = begin; i < iter; i++) {
                if (cur_el < arr[i]) {
                    for (int j = iter; j > i; j--) {
                        arr[j] = arr[j-1];
                    }
                    arr[i] = cur_el;
                    break;
                }
            }
            iter++;
        }
        return arr;
    }
}
