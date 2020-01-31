package com.osk.sortings;

public abstract class MergeSort implements Sorting {

    public int[] merge(int[] a, int[] b) {
        int[] res = new int[a.length + b.length];

        int idx_a = 0;
        int idx_b = 0;
        int idx_res = 0;

        while (idx_a < a.length && idx_b < b.length) {
            if (a[idx_a] < b[idx_b]) {
                res[idx_res] = a[idx_a];
                idx_res++;
                idx_a++;
            } else if (b[idx_b] < a[idx_a]) {
                res[idx_res] = b[idx_b];
                idx_res++;
                idx_b++;
            } else if (a[idx_a] == b[idx_b]) {
                res[idx_res] = a[idx_a];
                idx_res++;
                res[idx_res] = b[idx_b];
                idx_res++;
                idx_a++;
                idx_b++;
            }
        }

        if (idx_a < a.length) {
            for (; idx_a < a.length; idx_a++) {
                res[idx_res] = a[idx_a];
                idx_res++;
            }
        } else if (idx_b < b.length) {
            for (; idx_b < b.length; idx_b++) {
                res[idx_res] = b[idx_b];
                idx_res++;
            }
        }

        return res;
    }

    public int[] mergeMultiple(int[] ... arr) {
        int result_length = 0;
        int[] idx = new int[arr.length];
        int idx_res = 0;
        for (int i = 0; i < arr.length; i++) {
            result_length += arr[i].length;
        }
        int[] result = new int[result_length];
        while(idx_res < result_length) {
            int idx_min_val = 0;
            int min_val = Integer.MAX_VALUE;
            for (int i = 0; i < arr.length; i++) {
                if (arr[i].length > idx[i]
                        && arr[i][idx[i]] < min_val) {
                    min_val = arr[i][idx[i]];
                    idx_min_val = i;
                }
            }
            result[idx_res] = min_val;
            idx_res++;
            idx[idx_min_val]++;
        }
        return result;
    }

    public int[] mergeWithInsSort(int[] a, int[] b) {
        return new InsertedSort().sort(ArrayUtils.union(a, b));
    }

    public int[] mergeWithSelSort(int[] a, int[] b) {
        return new SelectedSort().sort(ArrayUtils.union(a, b));
    }
}
