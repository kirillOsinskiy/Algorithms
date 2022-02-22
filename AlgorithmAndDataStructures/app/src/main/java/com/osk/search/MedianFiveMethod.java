package com.osk.search;

import com.osk.sortings.InsertedSortBinarySearch;
import com.osk.sortings.Sorting;

import java.util.Random;

public class MedianFiveMethod {

    final static int NUMBER = 7;
    final static int MED_NUM = 3;

    Sorting srt = new InsertedSortBinarySearch();
    RandomizedSelect ranS = new RandomizedSelect();

    public static void main(String[] args) {
        Random r = new Random();
        int[] arr = new int[280];
        for (int i = 0; i < 280; i++) {
            arr[i] = r.nextInt(300);
        }

        MedianFiveMethod fiveMethod = new MedianFiveMethod();
        fiveMethod.findOrderStat(arr, 0, 0, arr.length);
    }

    void findOrderStat(int[] arr, int k, int start, int end) {
        // divide on 5th
        int[][] divArr = divideArr(arr);
        // sort every 5th
        for (int i = 0; i < divArr.length; i++) {
            srt.sort(divArr[i]);
        }
        // find median in third row
        int[] buf = new int[divArr.length];
        for (int i = 0; i < divArr.length; i++) {
            buf[i] = divArr[i][MED_NUM];
        }
        int med = ranS.findMed(buf);
        System.out.println("Median is " + med);

        partitionDoubleArr(divArr, med, MED_NUM);
        printArr(divArr);
//        int med_idx = 0;
//        for (int i = 0; i < divArr.length; i++) {
//            if(divArr[i][MED_NUM] == med) {
//                med_idx = i;
//                break;
//            }
//        }

//        int[] bufArr = toOneDimArr(divArr);
//        if(k < med_idx) {
//            findOrderStat(bufArr, k, 0, med_idx);
//        } else {
//            findOrderStat(bufArr, k, med_idx, bufArr.length);
//        }
    }

    private int[] toOneDimArr(int[][] divArr) {
        return new int[0];
    }

    private void partitionDoubleArr(int[][] arr, int med, int mainRow) {
        int start = 0;
        int end = arr.length - 1;
        int pivot = 0;
        for (int i = 0; i < arr.length; i++) {
            if(arr[i][mainRow] == med) pivot = i;
        }

        while(start < end) {
            while(arr[start][mainRow] < arr[pivot][mainRow] && start < pivot) {
                start++;
            }

            while(arr[end][mainRow] >= arr[pivot][mainRow] && end > pivot) {
                end--;
            }

            if(start < end) {
                int[] buf = arr[start];
                arr[start] = arr[end];
                arr[end] = buf;

                if(start == pivot) pivot = end;
                else if(end == pivot) pivot = start;

                if(start < pivot) start++;
                if(end > pivot) end--;
            }
        }
    }

    int[][] divideArr(int[] arr) {
        int subArraysCount = (arr.length / NUMBER) + (arr.length % NUMBER == 0 ? 0 : 1);

        int[][] arrWork = new int[subArraysCount][NUMBER];
        int m = 0;
        for (int i = 0; i < subArraysCount; i++) {
            for (int j = 0; j < NUMBER; j++) {
                arrWork[i][j] = arr[m];
                m++;
            }
        }

        return arrWork;
    }

    void printArr(int[][] arr) {
        for (int i = 0; i < NUMBER; i++) {
            for (int j = 0; j < arr.length; j++) {
                if(String.valueOf(arr[j][i]).length() == 1) System.out.print("  ");
                if(String.valueOf(arr[j][i]).length() == 2) System.out.print(" ");
                System.out.print(arr[j][i]);
                System.out.print("  ");
            }
            System.out.println();
        }
    }
}
