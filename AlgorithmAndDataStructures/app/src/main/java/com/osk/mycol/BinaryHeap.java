package com.osk.mycol;

import java.util.List;

public class BinaryHeap<T extends Comparable<T>> extends AbstractHeap<T> {

    public BinaryHeap(List<T> elements) {
        super(elements);
    }

    public BinaryHeap(T[] elems) {
        super(elems);
    }

    void siftDown(int idx) {
        if (elements.size() <= (idx * 2) + 1) return;
        T cur = elements.get(idx);
        int left_idx = (idx * 2) + 1;
        int right_idx = (idx * 2) + 2;
        T left = elements.get(left_idx);
        T min = left;
        int min_idx = left_idx;
        if (elements.size() > (idx * 2) + 2) {
            T right = elements.get(right_idx);
            if (left.compareTo(right) > 0) {
                min = right;
                min_idx = right_idx;
            }
        }
        if (cur.compareTo(min) > 0) {
            elements.set(min_idx, cur);
            elements.set(idx, min);
            siftDown(min_idx);
        }
    }

    void siftUp(int idx) {
        if (idx == 0) return;
        T elem = elements.get(idx);
        int parent_idx;
        if (idx % 2 == 1) parent_idx = (idx - 1) / 2;
        else parent_idx = (idx - 2) / 2;
        T parent = elements.get(parent_idx);

        if (parent.compareTo(elem) > 0) {
            elements.set(idx, parent);
            elements.set(parent_idx, elem);
            siftUp(parent_idx);
        }
    }
}
