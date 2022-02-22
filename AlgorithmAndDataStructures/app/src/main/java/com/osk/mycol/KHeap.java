package com.osk.mycol;

import java.util.List;

public class KHeap<T extends Comparable<T>> extends AbstractHeap<T> {

    private final int k;

    public KHeap(int k, T[] elems) {
        super(elems);
        this.k = k;
    }

    public KHeap(int k, List<T> elements) {
        super(elements);
        this.k = k;
    }

    @Override
    void siftUp(int idx){
        if(idx == 0) return;
        T cur = elements.get(idx);
        int parent_idx = (idx / k) - (idx % k == 0 ? 1 : 0);
        T parent = elements.get(parent_idx);
        if(parent.compareTo(cur) > 0) {
            elements.set(idx, parent);
            elements.set(parent_idx, cur);
            siftUp(parent_idx);
        }
    }

    @Override
    void siftDown(int idx) {
        if(elements.size() <= idx * k) return;
        T cur = elements.get(idx);
        // get indexes of all children
        int[] idxs = new int[k];
        for (int i = 1, j =0; i <= k; i++, j++) {
            if(elements.size() <= (idx * k) + i) break;
            idxs[j] = (idx * k) + i;
        }
        if(idxs[0] == 0) return;
        // find min child
        int idx_min = idxs[0];
        T child_min = elements.get(idx_min);
        for (int i = 1; i < k; i++) {
            if(idxs[i] == 0) break;
            T elem = elements.get(idxs[i]);
            if(child_min.compareTo(elem) > 0) {
                idx_min = idxs[i];
                child_min = elem;
            }
        }
        // compare min child with parent
        if(cur.compareTo(child_min) > 0) {
            elements.set(idx_min, cur);
            elements.set(idx, child_min);
            siftDown(idx_min);
        }
    }
}
