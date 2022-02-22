package com.osk.mycol;

public class Utils {

    public static <T extends Comparable<T>> KHeap<T> makeKHeap(int k, T[] elements) {
        int half_size = elements.length / k;
        for (int i = half_size; i >= 0; i--) {
            T cur_element = elements[i];
            // get children indexes
            int[] idxs = new int[k];
            for (int j = 1, p = 0; j <= k; j++, p++) {
                if (elements.length <= (i * k) + j) break;
                idxs[p] = (i * k) + j;
            }
            if (idxs[0] == 0) continue;
            // find min child
            int min_idx = idxs[0];
            T min_child = elements[min_idx];
            for (int j = 1; j < k; j++) {
                if (idxs[j] == 0) break;
                T elem = elements[idxs[j]];
                if (min_child.compareTo(elem) > 0) {
                    min_idx = idxs[j];
                    min_child = elem;
                }
            }
            // compare min child with parent
            if (cur_element.compareTo(min_child) > 0) {
                T buf = elements[i];
                elements[i] = elements[min_idx];
                elements[min_idx] = buf;
                siftDown(k, elements, min_idx);
            }
        }
        return new KHeap<>(k, elements);
    }

    public static <T extends Comparable<T>> BinaryHeap<T> makeBinaryHeap(T[] elements) {
        int t_size = elements.length / 2;
        for (int i = t_size; i >= 0; i--) {
            int left_idx = (i * 2) + 1;
            int right_idx = (i * 2) + 2;
            T cur = elements[i];
            if(elements.length <= left_idx) continue;
            T min = elements[left_idx];
            int idx_min = left_idx;
            if (elements.length - 1 >= right_idx) {
                T right = elements[right_idx];
                if (right.compareTo(min) < 0) {
                    min = right;
                    idx_min = right_idx;
                }
            }
            if (cur.compareTo(min) > 0) {
                T buf = elements[i];
                elements[i] = elements[idx_min];
                elements[idx_min] = buf;
                siftDown(elements, idx_min);
            }
        }
        return new BinaryHeap<>(elements);
    }

    private static <T extends Comparable<T>> void siftDown(int k, T[] elements, int idx) {
        if (elements.length <= idx * k) return;
        T cur_elem = elements[idx];
        int[] idxs = new int[k];
        for (int i = 1, j = 0; i <= k; i++, j++) {
            if ((idx * k) + i >= elements.length) break;
            idxs[j] = (idx * k) + i;
        }
        if (idxs[0] == 0) return;
        // find min child
        int idx_min = idxs[0];
        T min_child = elements[idx_min];
        for (int i = 1; i < k; i++) {
            if (idxs[i] == 0) break;
            T elem = elements[idxs[i]];
            if (min_child.compareTo(elem) > 0) {
                idx_min = idxs[i];
                min_child = elem;
            }
        }
        // compare min child with parent
        if (cur_elem.compareTo(min_child) > 0) {
            T buf = elements[idx];
            elements[idx] = elements[idx_min];
            elements[idx_min] = buf;
            siftDown(k, elements, idx_min);
        }
    }

    private static <T extends Comparable<T>> void siftDown(T[] elements, int idx) {
        if (elements.length <= (idx * 2) + 1) return;
        T cur = elements[idx];
        int left_idx = (idx * 2) + 1;
        int right_idx = (idx * 2) + 2;
        T left = elements[left_idx];
        T min = left;
        int min_idx = left_idx;
        if (elements.length > (idx * 2) + 2) {
            T right = elements[right_idx];
            if (left.compareTo(right) > 0) {
                min = right;
                min_idx = right_idx;
            }
        }
        if (cur.compareTo(min) > 0) {
            T buf = elements[min_idx];
            elements[min_idx] = elements[idx];
            elements[idx] = buf;
            siftDown(elements, min_idx);
        }
    }

}
