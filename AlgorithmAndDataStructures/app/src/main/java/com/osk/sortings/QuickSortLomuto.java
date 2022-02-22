package com.osk.sortings;

public class QuickSortLomuto extends QuickSort {

    @Override
    int partition(int[] arr, int begin, int end) {
        if(end <= begin) return begin;
        int i = begin;
        int cur = begin + 1;
        while (cur <= end) {
            if (arr[cur] <= arr[i]) {
                int buf = arr[cur];
                for (int j = cur; j > i; j--) {
                    arr[j] = arr[j - 1];
                }
                arr[i] = buf;
                i++;
            }
            cur++;
        }
        return end - begin == 1 ? begin : i;
    }
}
