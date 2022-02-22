package com.osk.codingtasks;

import java.util.HashSet;

/**
 * Created by Kirill on 08.07.2020.
 * <p>
 * Write java function which calculates number of pairs in array.
 * Pair is when two numbers added together, result is zero.
 */
public class NumberOfPairs {

    public static void main(String[] args) {

        int[] arr = {0, 1, 2, -1, -3, 1, 3, 0, 5};
        System.out.println(method1(arr));
        System.out.println(method2(arr));

    }

    public static int method2(int[] arr) {
        int count = 0;
        HashSet<Integer> set = new HashSet<>();
        for (int num : arr) {
            if (set.contains(-1 * num)) {
                count++;
            }
            set.add(num);
        }
        return count;
    }

    public static int method1(int[] arr) {
        int count = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] + arr[j] == 0) count++;
            }
        }
        return count;
    }
}
