package com.osk.search;

import java.util.Random;

public class RandomizedSelect {

    public int findMin(int[] arr) {
        return find(arr, 0);
    }

    public int findMax(int[] arr) {
        return find(arr, arr.length - 1);
    }

    public int findMed(int[] arr) {
        return find(arr, arr.length / 2);
    }

    private int find(int[] arr, int k) {
        // k == 0 -- find minimum
        // k = arr.length - 1 -- find maximum
        // k = arr.length / 2 -- find median
        return find(arr, k, 0, arr.length - 1);
    }

    private int find(int[] arr, int k, int start, int end) {
        if(start == end) {
            return arr[start];
        }
        int pivot = partition(arr, start, end);
        if(k < pivot) {
            return find(arr, k, start, pivot);
        } else if(k > pivot) {
            return find(arr, k, pivot + 1, end);
        } else {
            return arr[pivot];
        }
    }

    private int partition(int[] arr, int start, int end) {
        int idx_s = start, idx_e = end;
        Random r = new Random();
        float rand = r.nextFloat();
        int pivot = (int)(start * (1.0 - rand) + rand * end);
        while(idx_s < idx_e) {
            while (arr[idx_s] < arr[pivot] && idx_s < pivot) {
                idx_s++;
            }

            while (arr[idx_e] >= arr[pivot] && idx_e > pivot) {
                idx_e--;
            }

            if (arr[idx_s] >= arr[pivot] || arr[idx_e] < arr[pivot]) {
                int buf = arr[idx_s];
                arr[idx_s] = arr[idx_e];
                arr[idx_e] = buf;
                if (idx_s == pivot) {
                    pivot = idx_e;
                } else if (idx_e == pivot) {
                    pivot = idx_s;
                }
                if(idx_s < pivot) idx_s++;
                if(idx_e > pivot) idx_e--;
            }
        }
        return pivot;
    }

}
