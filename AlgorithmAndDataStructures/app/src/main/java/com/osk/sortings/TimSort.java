package com.osk.sortings;

public class TimSort implements Sorting {

    private InsertedSort srt = new InsertedSort();

    private final static int MIN_RUN = 4;

    @Override
    public int[] sort(int[] arr) {
        createSortedSubArrays(arr);
        return arr;
    }

    private void createSortedSubArrays(int[] arr) {
        int iter = 0;
        while(iter < arr.length) {
            int start_cr = iter;
            iter++;
            int cur_run = 0;
            if (arr[iter] < arr[iter - 1]) {
                int buf = arr[iter];
                arr[iter] = arr[iter - 1];
                arr[iter - 1] = buf;
            }
            cur_run += 2;
            if (cur_run < MIN_RUN) {
                if(iter + 2 * MIN_RUN > arr.length) {
                    cur_run += arr.length - iter - 1;
                } else {
                    cur_run += MIN_RUN - cur_run;
                }
            }
            iter = start_cr + cur_run;
            srt.sort(arr, start_cr, iter);
        }
    }
}
