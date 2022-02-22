package com.osk.trees;

public class Node<T extends Comparable<T>> extends AbstractNode<T> implements Comparable<Node<T>> {

    protected Node(Node<T> parent, T key, Side side) {
        super(key, side);
        this.parent = parent;
    }

    @Override
    public Node<T> getLeft() {
        return (Node<T>) super.getLeft();
    }

    @Override
    public Node<T> getRight() {
        return (Node<T>) super.getRight();
    }

    @Override
    public Node<T> getParent() {
        return (Node<T>) super.getParent();
    }

    @Override
    public int compareTo(Node<T> o) {
        return key.compareTo(o.key);
    }

}
