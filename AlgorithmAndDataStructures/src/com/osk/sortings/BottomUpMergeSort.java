package com.osk.sortings;

public class BottomUpMergeSort extends MergeSort {

    @Override
    public int[] sort(int[] arr) {
        int k = 0;
        int[][] res_arr = new int[arr.length][1];
        for (int i = 0; i < arr.length; i++) {
            res_arr[i][0] = arr[i];
        }

        while (Math.pow(2, k) < arr.length) {
            k++;
            int step = (int) Math.pow(2, k);
            int length = (arr.length / step);
            if (arr.length % step != 0) length++;
            int[][] tmp_arr = new int[length][];
            int j = 0;
            for (int i = 0; i < res_arr.length; i += 2) {
                if (res_arr.length == i + 1) {
                    tmp_arr[j] = res_arr[i];
                } else {
                    tmp_arr[j] = merge(res_arr[i], res_arr[i + 1]);
                    j++;
                }
            }
            res_arr = tmp_arr;
        }
        return arr.length == 0 ? arr : res_arr[0];
    }
}
