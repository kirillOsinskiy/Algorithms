package com.osk.mycol;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractHeap<T extends Comparable<T>> implements Heap<T> {

    final List<T> elements;

    AbstractHeap(List<T> elements) {
        this.elements = elements;
    }

    AbstractHeap(T[] elems) {
        elements = new LinkedList<>(Arrays.asList(elems));
    }

    public List<T> getElements() {
        return elements;
    }

    @Override
    public void insert(T key) {
        elements.add(key);
        siftUp(elements.size() - 1);
    }

    @Override
    public T remove(T key) {
        int idx = elements.indexOf(key);
        T last_el = elements.get(elements.size() - 1);
        elements.set(idx, last_el);
        elements.set(elements.size() - 1, key);
        T rm_el = elements.remove(elements.size() - 1);
        siftDown(idx);
        return rm_el;
    }

    @Override
    public T getMin() {
        return elements.get(0);
    }

    @Override
    public T extractMin() {
        return remove(getMin());
    }

    public void decreaseKey(int idx, T newKey) {
        T oldKey = elements.get(idx);
        if (newKey.compareTo(oldKey) > 0) throw new RuntimeException("New key could not be greater than Old!");
        elements.set(idx, newKey);
        siftUp(idx);
    }

    abstract void siftUp(int idx);

    abstract void siftDown(int idx);
}
