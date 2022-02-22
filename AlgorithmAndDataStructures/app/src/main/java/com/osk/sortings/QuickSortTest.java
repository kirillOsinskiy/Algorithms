package com.osk.sortings;

public class QuickSortTest implements Sorting {
    @Override
    public int[] sort(int[] arr) {
        sort(arr, 0, arr.length - 1);
        return arr;
    }

    void sort(int[] arr, int begin, int end) {
        if (begin >= end) return;
        int i = begin, j = end;
        int cur = (end + begin) / 2;

        while (i < j) {
            while (arr[i] < arr[cur] && i < cur) i++;
            while (arr[j] >= arr[cur] && j > cur) j--;

            if (i < j) {
                int buf = arr[i];
                arr[i] = arr[j];
                arr[j] = buf;

                if (i == cur) cur = j;
                else if (j == cur) cur = i;
            }
        }

        sort(arr, begin, j);
        sort(arr, j + 1, end);
    }
}
