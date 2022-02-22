package com.osk.sortings;

public class BubbleSort implements Sorting {
    @Override
    public int[] sort(int[] arr) {
        int i = arr.length - 1;
        while (i > 0) {
            if (arr[i] < arr[i - 1]) {
                int buf = arr[i];
                arr[i] = arr[i - 1];
                arr[i - 1] = buf;
                i = arr.length - 1;
            } else {
                i--;
            }
        }
        return arr;
    }
}
