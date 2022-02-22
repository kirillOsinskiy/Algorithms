package com.osk.sortings;

import com.osk.search.BinarySearch;

public class InsertedSortBinarySearch implements Sorting {

    BinarySearch bs = new BinarySearch();

    @Override
    public int[] sort(int[] arr) {
        int iter = 1;
        while(iter < arr.length) {
            int ins_pos = bs.searchPlaceForInsert(arr, arr[iter], 0, iter);
            int buf = arr[iter];
            for (int i = iter; i > ins_pos ; i--) {
                arr[i] = arr[i - 1];
            }
            arr[ins_pos] = buf;
            iter++;
        }
        return arr;
    }
}
