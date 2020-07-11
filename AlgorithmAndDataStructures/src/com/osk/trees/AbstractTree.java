package com.osk.trees;

/**
 * Created by Kirill on 02.07.2020.
 */
public abstract class AbstractTree<T extends Comparable<T>, U extends AbstractNode<T>> {

    protected U root;

    public AbstractTree(T key) {}

    public AbstractTree(U node) {
        setRoot(node);
    }

    public U getRoot() {
        return root;
    }

    public void setRoot(U root) {
        this.root = root;
        root.setParent(null);
        root.setSide(null);
    }

    public U find(T key) {
        return find(key, root);
    }

    private U find(T key, U node) {
        if(node == null) return null;
        if(node.getKey().equals(key)) return node;
        if(node.getKey().compareTo(key) > 0) {
            return find(key, (U) node.getLeft());
        } else {
            return find(key, (U) node.getRight());
        }
    }

    public U getMaximum() {
        U node = root;
        while (node.getRight() != null) node = (U) node.getRight();
        return node;
    }

    public U getMinimum() {
        U node = root;
        while (node.getLeft() != null) node = (U) node.getLeft();
        return node;
    }

    @Override
    public String toString() {
        return "[" + root + "]";
    }

}


