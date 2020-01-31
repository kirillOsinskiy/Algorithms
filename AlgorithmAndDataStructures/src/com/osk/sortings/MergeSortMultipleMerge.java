package com.osk.sortings;

public class MergeSortMultipleMerge extends MergeSort {

    private final Sorting srt = new InsertedSortBinarySearch();

    private final static int CHUNK_SIZE = 32;

    @Override
    public int[] sort(int[] arr) {
        if(arr.length == 0) return arr;

        int chunk_count = CHUNK_SIZE < arr.length ? arr.length / CHUNK_SIZE : 1;
        if(CHUNK_SIZE < arr.length && arr.length % CHUNK_SIZE != 0) chunk_count++;
        int[][] res = new int[chunk_count][];
        res[0] = new int[CHUNK_SIZE < arr.length ? CHUNK_SIZE : arr.length];
        int chunk_cnt = 0;
        for (int i = 0, j = 0; i < arr.length; i++, j++) {
            if (i % CHUNK_SIZE == 0 && i != 0) {
                chunk_cnt++;
                int last_chunk_size = arr.length - i;
                j = 0;
                if (last_chunk_size < CHUNK_SIZE) {
                    res[chunk_cnt] = new int[last_chunk_size];
                } else {
                    res[chunk_cnt] = new int[CHUNK_SIZE];
                }
            }
            res[chunk_cnt][j] = arr[i];
        }
        for (int i = 0; i < chunk_count; i++) {
            if(res[i].length > 1) srt.sort(res[i]);
        }
        return mergeMultiple(res);
    }
}
