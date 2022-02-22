package com.osk.sortings;

public class SelectedSort implements Sorting {

    @Override
    public int[] sort(int[] arr) {
        int iter = 0;
        while (iter < arr.length) {
            //find minimum
            int min = Integer.MAX_VALUE;
            int min_idx = 0;
            for (int i = iter; i < arr.length; i++) {
                if (arr[i] < min) {
                    min = arr[i];
                    min_idx = i;
                }
            }
            // replace
            int buf = arr[min_idx];
            arr[min_idx] = arr[iter];
            arr[iter] = buf;
            iter++;
        }
        return arr;
    }
}
