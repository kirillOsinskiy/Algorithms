package com.osk.codingtasks;

import java.util.Stack;

/**
 * Created by Kirill on 08.07.2020.
 *
 * Write java function to reverse elements in one directional linked list
 */
public class ReverseList {

    public static void main(String[] args) {
        List<Integer> list = new List<>(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
        System.out.println(list);
        System.out.println(reverse1(list));
        list = new List<>(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
        System.out.println(reverse2(list));
    }

    /**
     * O(n) + O(n-1) + O(n-2) + ... + O(1) = O(n) ???
     * @param list
     * @return
     */
    private static List<Integer> reverse2(List<Integer> list) {
        List<Integer>.Node<Integer> node = list.getFirst();
        if(node == null) return null;
        int size = 0;
        List<Integer>.Node<Integer> next = node.getNext();
        while(next != null) {
            size++;
            int buf = node.getKey();
            node.setKey(next.getKey());
            next.setKey(buf);
            node = next;
            next = next.getNext();
        }
        while(size > 0) {
            int curSize = --size;
            node = list.getFirst();
            next = node.getNext();
            while (curSize > 0) {
                int buf = node.getKey();
                node.setKey(next.getKey());
                next.setKey(buf);
                node = next;
                next = next.getNext();
                curSize--;
            }
        }

        return list;
    }

    /**
     * O(n) + stack memory
     * @param list input
     * @return reversed list
     */
    private static List<Integer> reverse1(List<Integer> list) {
        Stack<Integer> stack = new Stack<>();
        List<Integer>.Node<Integer> node = list.getFirst();
        while(node != null) {
            stack.push(node.getKey());
            node = node.getNext();
        }
        node = list.getFirst();
        while(!stack.empty()) {
            node.setKey(stack.pop());
            node = node.getNext();
        }
        return list;
    }

}

class List<T> {

    Node<T> first;

    public List(T[] arr) {
        for(T elem : arr) {
            add(elem);
        }
    }

    public Node<T> getFirst() {
        return first;
    }

    class Node<U> {
        U key;
        Node<U> next;

        public U getKey() {
            return key;
        }

        public void setKey(U key) {
            this.key = key;
        }

        public void setNext(Node<U> next) {
            this.next = next;
        }

        public Node<U> getNext() {
            return next;
        }

        public Node(U key) {
            this.key = key;
        }
    }

    public void add(T key) {
        if(first == null) {
            first = new Node<>(key);
        }
        else {
            Node<T> node = first;
            while(node.getNext() != null) {
                node = node.getNext();
            }
            node.setNext(new Node<>(key));
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<T> node = first;
        while(node != null) {
            sb.append(node.getKey()).append(", ");
            node = node.getNext();
        }
        sb.append("]");
        return sb.toString();
    }
}

