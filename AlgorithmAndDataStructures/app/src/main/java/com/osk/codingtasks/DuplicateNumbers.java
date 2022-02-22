package com.osk.codingtasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by Kirill on 08.07.2020.
 * <p>
 * An array contains numbers. Exactly one number is duplicated in the array.
 * Write java function to find this duplicate
 */
public class DuplicateNumbers {

    public static void main(String[] args) {
        int[] arr = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 4};
        System.out.println(method1(arr));

        int[] arr2 = {0, 1, 2, 1, 4, 5, 6, 7, 5, 1, 4, 0}; // 0 1 4 5
        System.out.println(method2(arr2));
    }

    /**
     * Find one duplicate
     * O(n)
     * @param arr input
     * @return duplicate number
     */
    private static Integer method1(int[] arr) {
        HashSet<Integer> set = new HashSet<>();
        for (int i : arr) {
            if (set.contains(i)) return i;
            set.add(i);
        }
        return null;
    }

    /**
     * Find all duplicate numbers
     * O(n)
     * @param arr input
     * @return list of duplicates
     */
    private static ArrayList<Integer> method2(int[] arr) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i : arr) {
            Integer value = map.get(i);
            if (value == null) {
                map.put(i, 1);
            } else {
                map.put(i, ++value);
            }
        }
        ArrayList<Integer> res = new ArrayList<>();
        for(Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if(entry.getValue() > 1) res.add(entry.getKey());
        }
        return res;
    }

}
