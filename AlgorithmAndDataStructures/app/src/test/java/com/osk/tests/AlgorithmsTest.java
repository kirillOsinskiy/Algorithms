package com.osk.tests;


import com.osk.mycol.BinaryHeap;
import com.osk.mycol.KHeap;
import com.osk.mycol.Utils;
import com.osk.search.BinarySearch;
import com.osk.search.RandomizedSelect;
import com.osk.sortings.ArrayUtils;
import com.osk.sortings.BinomHeapSort;
import com.osk.sortings.BottomUpMergeSort;
import com.osk.sortings.BubbleSort;
import com.osk.sortings.HeapSort;
import com.osk.sortings.InsertedSort;
import com.osk.sortings.InsertedSortBinarySearch;
import com.osk.sortings.MergeSort;
import com.osk.sortings.MergeSortMultipleMerge;
import com.osk.sortings.QuickSort;
import com.osk.sortings.QuickSortLomuto;
import com.osk.sortings.QuickSortNotMY;
import com.osk.sortings.QuickSortTest;
import com.osk.sortings.SelectedSort;
import com.osk.sortings.Sorting;
import com.osk.sortings.TopDownMergeSort;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class AlgorithmsTest {

    private final List<Sorting> SORTING_LIST = Arrays.asList(
            new BottomUpMergeSort()
            , new TopDownMergeSort()
            , new InsertedSort()
            , new SelectedSort()
            , new BubbleSort()
            , new InsertedSortBinarySearch()
            , new QuickSort()
            , new QuickSortNotMY()
            , new QuickSortLomuto()
            , new MergeSortMultipleMerge()
            , new QuickSortTest()
            , new HeapSort(0)
            , new HeapSort(2)
            , new HeapSort(4)
            , new HeapSort(8)
            , new BinomHeapSort()
    );

    @Test
    public void testBinarySearch() {
        BinarySearch bs = new BinarySearch();
        int[] arr = {0, 1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 11};

        assertEquals(-1, bs.search(arr, -1));
        assertEquals(0, bs.search(arr, 0));
        assertEquals(1, bs.search(arr, 1));
        assertEquals(3, bs.search(arr, 2));
        assertEquals(4, bs.search(arr, 3));
        assertEquals(5, bs.search(arr, 4));
        assertEquals(6, bs.search(arr, 5));
        assertEquals(7, bs.search(arr, 6));
        assertEquals(8, bs.search(arr, 7));
        assertEquals(9, bs.search(arr, 8));
        assertEquals(10, bs.search(arr, 9));
        assertEquals(-1, bs.search(arr, 10));
        assertEquals(11, bs.search(arr, 11));
        assertEquals(-1, bs.search(arr, 12));

        arr = new int[]{0, 0, 0};
        assertEquals(1, bs.search(arr, 0));

        arr = new int[0];
        assertEquals(-1, bs.search(arr, 0));
    }

    @Test
    public void testBinarySearchPlaceForInsert() {
        BinarySearch bs = new BinarySearch();
        int[] arr = {0, 2, 4, 7, 8, 10, 12, 13, 15, 17, 21};

        assertEquals(0, bs.searchPlaceForInsert(arr, -1));
        assertEquals(1, bs.searchPlaceForInsert(arr, 1));
        assertEquals(2, bs.searchPlaceForInsert(arr, 3));
        assertEquals(3, bs.searchPlaceForInsert(arr, 5));
        assertEquals(3, bs.searchPlaceForInsert(arr, 6));
        assertEquals(5, bs.searchPlaceForInsert(arr, 9));
        assertEquals(6, bs.searchPlaceForInsert(arr, 11));
        assertEquals(8, bs.searchPlaceForInsert(arr, 14));
        assertEquals(9, bs.searchPlaceForInsert(arr, 16));
        assertEquals(10, bs.searchPlaceForInsert(arr, 18));
        assertEquals(10, bs.searchPlaceForInsert(arr, 19));
        assertEquals(10, bs.searchPlaceForInsert(arr, 20));
        assertEquals(11, bs.searchPlaceForInsert(arr, 22));

        assertEquals(0, bs.searchPlaceForInsert(arr, 0));
        assertEquals(1, bs.searchPlaceForInsert(arr, 2));
        assertEquals(2, bs.searchPlaceForInsert(arr, 4));
        assertEquals(3, bs.searchPlaceForInsert(arr, 7));
        assertEquals(4, bs.searchPlaceForInsert(arr, 8));
        assertEquals(5, bs.searchPlaceForInsert(arr, 10));
        assertEquals(6, bs.searchPlaceForInsert(arr, 12));
        assertEquals(7, bs.searchPlaceForInsert(arr, 13));
        assertEquals(8, bs.searchPlaceForInsert(arr, 15));
        assertEquals(9, bs.searchPlaceForInsert(arr, 17));
        assertEquals(10, bs.searchPlaceForInsert(arr, 21));

        arr = new int[]{0};
        assertEquals(0, bs.searchPlaceForInsert(arr, 0));

        arr = new int[]{1, 1, 1};
        assertEquals(1, bs.searchPlaceForInsert(arr, 1));
    }

    @Test
    public void makeHeapTest() {
        Random r = new Random();
        Integer[] arr = new Integer[1000];
        for (int i = 0; i < 1000; i++) {
            arr[i] = r.nextInt(999);
        }
        BinaryHeap<Integer> bh = Utils.makeBinaryHeap(arr);
        Integer[] res = bh.getElements().toArray(new Integer[100]);
        for (int i = 0; i < 50; i++) {
            int idx_left = (i * 2) + 1;
            int idx_right = (i * 2) + 2;
            assertTrue(res[i] <= res[idx_left]);
            if (res.length > idx_right) {
                assertTrue(res[i] <= res[idx_right]);
            }
        }
    }

    @Test
    public void makeKHeapTest() {
        testKHeapForK(2);
        testKHeapForK(4);
        testKHeapForK(8);
    }

    private void testKHeapForK(int k) {
        int arr_size = 1000;
        Random r = new Random();
        Integer[] arr = new Integer[arr_size];
        for (int i = 0; i < arr_size; i++) {
            arr[i] = r.nextInt(999);
        }
        KHeap<Integer> bh = Utils.makeKHeap(k, arr);
        Integer[] res = bh.getElements().toArray(new Integer[arr_size]);
        for (int i = 0; i < arr_size / k; i++) {
            for (int j = 1; j <= k; j++) {
                int child_idx = (i * k) + j;
                if (res.length > child_idx) {
                    assertTrue(res[i] <= res[child_idx]);
                }
            }
        }
    }

    @Test
    public void testSorting() {
        int[] arr = {0, 8, 3, 5, 1, 2, 4, -1, 9, 0, 7, 6, 1};
        int[] expected = {-1, 0, 0, 1, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        testSorting(arr, expected);

        arr = new int[]{1, 8, 3, 5, 1, 2, 4, 9, 0, 7, 6};
        expected = new int[]{0, 1, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        testSorting(arr, expected);

        arr = new int[]{0, 8, 3, 5, 1, 2, 4, 9, 0, 7, 6, 1};
        expected = new int[]{0, 0, 1, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        testSorting(arr, expected);

        arr = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        expected = arr;
        testSorting(arr, expected);

        arr = new int[0];
        expected = arr;
        testSorting(arr, expected);

        arr = new int[]{0, 2, 4, 5, 7, 9, 11, 11, 10, 8, 6, 3, 1, 0};
        expected = new int[]{0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 11};
        testSorting(arr, expected);

        arr = generateRandomArray();
        expected = Arrays.copyOf(arr, arr.length);
        Arrays.sort(expected);
        testSorting(arr, expected);
    }

    @Test
    public void testMerge() {
        int[] a = {0, 1, 2, 3, 4};
        int[] b = {9, 12, 15, 20};

        int[] exp1 = {0, 0, 1, 1, 2, 2, 3, 3, 4, 4};
        int[] exp2 = {0, 1, 2, 3, 4, 9, 12, 15, 20};

        MergeSort srt = new BottomUpMergeSort();

        assertArrayEquals(exp1, srt.merge(a, a));
        assertArrayEquals(exp2, srt.merge(a, b));
        assertArrayEquals(exp2, srt.merge(b, a));

        assertArrayEquals(exp1, srt.mergeWithInsSort(a, a));
        assertArrayEquals(exp2, srt.mergeWithInsSort(a, b));
        assertArrayEquals(exp2, srt.mergeWithInsSort(b, a));

        assertArrayEquals(exp1, srt.mergeWithSelSort(a, a));
        assertArrayEquals(exp2, srt.mergeWithSelSort(a, b));
        assertArrayEquals(exp2, srt.mergeWithSelSort(b, a));

        assertArrayEquals(exp1, srt.mergeMultiple(a, a));
        assertArrayEquals(exp2, srt.mergeMultiple(a, b));
        assertArrayEquals(exp2, srt.mergeMultiple(b, a));

        int[] c = {10, 11, 13, 14, 16};
        int[] d = {5, 17, 18, 19};

        int[] exp3 = {0, 1, 2, 3, 4, 5, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
        assertArrayEquals(exp3, srt.mergeMultiple(a, b, c, d));

        int[] exp4 = {0, 0, 0, 1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4};
        assertArrayEquals(exp4, srt.mergeMultiple(a, a, a));

        int[] e = {0, 1, 2, 3, 4, 15, 20, 20, 25, 30, 40};
        int[] f = {25, 26};
        int[] exp5 = {0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 15, 20, 20, 25, 25, 26, 30, 40};
        assertArrayEquals(exp5, srt.mergeMultiple(a, e, f));
        assertArrayEquals(exp5, srt.mergeMultiple(e, a, f));
        assertArrayEquals(exp5, srt.mergeMultiple(f, e, a));
    }

    @Test
    public void testUnion() {
        int[] a = {0, 1, 2, 3, 4};
        int[] b = {9, 12, 15, 20};

        int[] exp1 = {0, 1, 2, 3, 4, 9, 12, 15, 20}; // a+b
        int[] exp2 = {9, 12, 15, 20, 0, 1, 2, 3, 4}; // b+a
        int[] exp3 = {0, 1, 2, 3, 4, 0, 1, 2, 3, 4}; // a+a

        assertArrayEquals(exp1, ArrayUtils.union(a, b));
        assertArrayEquals(exp2, ArrayUtils.union(b, a));
        assertArrayEquals(exp3, ArrayUtils.union(a, a));
    }

    @Test
    public void testRandomizedSelect() {
        RandomizedSelect rs = new RandomizedSelect();
        int[] arr = {3, 1, 4, 0, 2};
        assertEquals(0, rs.findMin(Arrays.copyOf(arr, arr.length)));
        assertEquals(4, rs.findMax(Arrays.copyOf(arr, arr.length)));
        assertEquals(2, rs.findMed(Arrays.copyOf(arr, arr.length)));

        arr = new int[]{0, -10, 0, 10, 1, -1};
        assertEquals(-10, rs.findMin(Arrays.copyOf(arr, arr.length)));
        assertEquals(10, rs.findMax(Arrays.copyOf(arr, arr.length)));
        assertEquals(0, rs.findMed(Arrays.copyOf(arr, arr.length)));

        arr = new int[]{0, 8, 3, 5, 1, 2, 4, -1, 9, 0, 7, 6, 1};
        assertEquals(-1, rs.findMin(Arrays.copyOf(arr, arr.length)));
        assertEquals(9, rs.findMax(Arrays.copyOf(arr, arr.length)));
        assertEquals(3, rs.findMed(Arrays.copyOf(arr, arr.length)));

        arr = new int[]{0};
        assertEquals(0, rs.findMin(Arrays.copyOf(arr, arr.length)));
        assertEquals(0, rs.findMax(Arrays.copyOf(arr, arr.length)));
        assertEquals(0, rs.findMed(Arrays.copyOf(arr, arr.length)));
    }

    private void testSorting(int[] arr, int[] expected) {
        for (Sorting srt : SORTING_LIST) {
            testSortingOne(srt, Arrays.copyOf(arr, arr.length), expected);
        }
    }

    private void testSortingOne(Sorting srt, int[] arr, int[] expected) {
        arr = srt.sort(arr);
        System.out.println("Expected sorted array: " + Arrays.toString(expected));
        System.out.println("Sorted array: " + Arrays.toString(arr));
        assertArrayEquals(expected, arr);
    }

    private int[] generateRandomArray() {
        Random r = new Random();
        int[] res = new int[100];
        for (int i = 0; i < 100; i++) {
            res[i] = r.nextInt(1000);
        }
        return res;
    }
}
