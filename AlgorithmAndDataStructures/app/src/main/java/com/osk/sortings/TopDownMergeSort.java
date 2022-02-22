package com.osk.sortings;

public class TopDownMergeSort extends MergeSort {

    @Override
    public int[] sort(int[] arr) {
        if(arr.length == 0) {
            return arr;
        } else if (arr.length <= 4) {
            return new InsertedSortBinarySearch().sort(arr);
        }
        int length = arr.length / 2 + arr.length % 2;
        int[] a = new int[length];
        int[] b = new int[arr.length - length];
        System.arraycopy(arr, 0, a, 0, length);
        System.arraycopy(arr, length, b, 0, arr.length - length);
        return mergeMultiple(sort(a), sort(b));
    }
}
