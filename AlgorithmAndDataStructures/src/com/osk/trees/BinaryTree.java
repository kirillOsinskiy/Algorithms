package com.osk.trees;

public class BinaryTree<T extends Comparable<T>> {

    private Node<T> root;

    public BinaryTree(T key) {
        this.root = new Node<>(null, key, null);
    }

    public void insert(T key) {
        root.insert(key);
    }

    public void remove(T key) {
        root.remove(key);
    }

    public void visit() {
        root.visit();
    }

    public T successor(Node<T> node) {
        return node.successor().getKey();
    }

    public T predecessor(Node<T> node) {
        return node.predecessor().getKey();
    }

    public T successor(T key) {
        Node<T> successor = findNodeByKey(key).successor();
        return successor != null ? successor.getKey() : null;
    }

    public T predecessor(T key) {
        Node<T> predecessor = findNodeByKey(key).predecessor();
        return predecessor != null ? predecessor.getKey() : null;
    }

    private Node<T> findNodeByKey(T key) {
        if(root.getKey().equals(key)) return root;
        else return root.findChildByKey(key);
    }

    @Override
    public String toString() {
        return "[" + root + "]";
    }
}

