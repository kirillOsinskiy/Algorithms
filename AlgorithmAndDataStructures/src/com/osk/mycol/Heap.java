package com.osk.mycol;

public interface Heap<T extends Comparable<T>> {
    void insert(T key);

    T remove(T key);

    T getMin();

    T extractMin();
}