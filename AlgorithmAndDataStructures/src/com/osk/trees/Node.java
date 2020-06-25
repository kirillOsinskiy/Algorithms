package com.osk.trees;

public class Node<T extends Comparable<T>> implements Comparable<Node<T>> {

    protected T key;
    private Side side; // == null for root
    protected Node<T> right;
    protected Node<T> left;
    protected Node<T> parent; // == null for root

    protected Node(Node<T> parent, T key, Side side) {
        this.parent = parent;
        this.key = key;
        this.side = side;
    }

    public void setKey(T key) {
        this.key = key;
    }

    public T getKey() {
        return key;
    }

    public Node<T> getRight() {
        return right;
    }

    public void setRight(Node<T> right) {
        this.right = right;
        if (right != null) {
            right.setParent(this);
            right.setSide(Side.RIGHT);
        }
    }

    public Node<T> getLeft() {
        return left;
    }

    public void setLeft(Node<T> left) {
        this.left = left;
        if (left != null) {
            left.setParent(this);
            left.setSide(Side.LEFT);
        }
    }

    public Node<T> getParent() {
        return parent;
    }

    public void setParent(Node<T> parent) {
        this.parent = parent;
    }

    public Side getSide() {
        return side;
    }

    public void setSide(Side side) {
        this.side = side;
    }

    public boolean isLeft() {
        return Side.LEFT.equals(side);
    }

    public boolean isRight() {
        return Side.RIGHT.equals(side);
    }

    void visit() {
        // pre-order
        if (left != null) left.visit();
        // in-order
        if (right != null) right.visit();
        // post-order
    }

    private String getStringView(StringBuilder sb) {
        if (left != null) left.getStringView(sb);
        sb.append(key).append(" ");
        if (right != null) right.getStringView(sb);
        return sb.toString();
    }

    @Override
    public String toString() {
        return getStringView(new StringBuilder());
    }

    @Override
    public int compareTo(Node<T> o) {
        return key.compareTo(o.key);
    }

    Node<T> successor() {
        if (right != null) {
            return findLastLeftChild(right);
        } else {
            if (Side.LEFT.equals(side)) {
                return parent;
            } else {
                return findNearestParent(parent, Side.LEFT);
            }
        }
    }

    Node<T> predecessor() {
        if (left != null) {
            return findLastRightChild(left);
        } else {
            if (Side.RIGHT.equals(side)) {
                return parent;
            } else {
                return findNearestParent(parent, Side.RIGHT);
            }
        }
    }

    private static <T extends Comparable<T>> Node<T> findNearestParent(Node<T> node, Side side) {
        if (node == null || node.side == null) return null;
        Node<T> curNode = node;
        Node<T> par = curNode.parent;
        while (curNode.side != null && !curNode.side.equals(side)) {
            curNode = par;
            par = curNode.parent;
        }
        return par;
    }

    private static <T extends Comparable<T>> Node<T> findLastLeftChild(Node<T> node) {
        Node<T> parent = node;
        Node<T> child = parent.left;
        while (child != null) {
            parent = child;
            child = parent.left;
        }
        return parent;
    }

    private Node<T> findLastRightChild(Node<T> node) {
        Node<T> parent = node;
        Node<T> child = parent.right;
        while (child != null) {
            parent = child;
            child = parent.right;
        }
        return parent;
    }
}
