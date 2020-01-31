package com.osk.tests;

import com.osk.hashing.HashTable;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class HashTableTest {

    private static final int DATA_MAXIMUM_NUM = 1000;

    @Test
    public void hashTableTest() {
        Integer[] data = new Integer[0];
        try {
            new HashTable(data);
        } catch (Exception ex) {
            assertTrue(ex instanceof IllegalArgumentException);
        }

        data = new Integer[]{-1};
        HashTable hashTable = new HashTable(data);
        assertTrue(hashTable.contains(-1));
        assertFalse(hashTable.contains(0));

        data = prepareData(DATA_MAXIMUM_NUM);
        for (Integer aData : data) {
            assertFalse(hashTable.contains(aData));
        }

        hashTable = new HashTable(data);

        for (Integer aData : data) {
            assertTrue(hashTable.contains(aData));
        }

        assertFalse(hashTable.contains(-199));

//        data = prepareRandomData(DATA_MAXIMUM_NUM);
//        hashTable = new HashTable(data);
//
//        for (Integer aData : data) {
//            assertTrue(hashTable.contains(aData));
//        }

        System.out.println(hashTable);
    }

    private static Integer[] prepareData(int max) {
        Integer[] res = new Integer[max + 1];
        for (int i = 0; i <= max; i++) {
            res[i] = i * i;
        }
        return res;
    }

    private static Integer[] prepareRandomData(int max) {
        Random r = new Random();
        Integer[] res = new Integer[max + 1];
        for (int i = 0; i <= max; i++) {
            res[i] = r.nextInt();
        }
        return res;
    }
}