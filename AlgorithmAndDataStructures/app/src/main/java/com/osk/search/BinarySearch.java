package com.osk.search;

public class BinarySearch {

    public int search(int[] arr, int val) {
        return search(arr, val, 0, arr.length);
    }

    private int search(int[] arr, int val, int startPos, int length) {
        if (length == 0) {
            return -1;
        } else if(length == 1) {
            return arr[startPos] == val ? startPos : -1;
        } else {
            int idx = startPos + length / 2;
            length = length / 2 + length % 2;
            if(val < arr[idx]) {
                return search(arr, val, startPos, length);
            } else if (val > arr[idx]) {
                return search(arr, val, idx, length);
            } else {
                return idx;
            }
        }
    }

    public int searchPlaceForInsert(int[] arr, int val) {
        return searchPlaceForInsert(arr, val, 0, arr.length);
    }

    public int searchPlaceForInsert(int[] arr, int val, int startPos, int length) {
        if (length == 1) {
            return arr[startPos] < val ? ++startPos : startPos;
        } else {
            int idx = startPos + length / 2;
            length = length / 2 + length % 2;
            if (val < arr[idx]) {
                return searchPlaceForInsert(arr, val, startPos, length);
            } else if (val > arr[idx]) {
                return searchPlaceForInsert(arr, val, idx, length);
            } else {
                return idx;
            }
        }
    }
}
