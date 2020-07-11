package com.osk.trees;

public class BinaryTree<T extends Comparable<T>> extends AbstractTree<T, Node<T>> {

    public BinaryTree(Node<T> node) {
        super(node);
    }

    public BinaryTree(T key) {
        super(key);
        this.root = new Node<>(null, key, null);
    }

    public Node<T> insert(T key) {
        return insert(key, root);
    }

    private Node<T> insert(T key, Node<T> node) {
        if (node.getKey().compareTo(key) != 0) {
            if (node.getKey().compareTo(key) > 0) {
                if (node.getLeft() == null) {
                    Node<T> res = new Node<>(node, key, Side.LEFT);
                    node.setLeft(res);
                    return res;
                } else {
                    return insert(key, node.getLeft());
                }
            } else if (node.getKey().compareTo(key) < 0) {
                if (node.getRight() == null) {
                    Node<T> res = new Node<>(node, key, Side.RIGHT);
                    node.setRight(res);
                    return res;
                } else {
                    return insert(key, node.getRight());
                }
            }
        }
        return null;
    }

    public void remove(T key) {
        Node<T> rmNode = find(key);
        if (rmNode != null) remove(rmNode);
    }

    void remove(Node<T> node) {
        Node<T> p = node.getParent();
        if (node.getLeft() == null && node.getRight() == null) {
            if (node.isLeft()) p.setLeft(null);
            else if (node.isRight()) p.setRight(null);
        } else if (node.getLeft() == null || node.getRight() == null) {
            Node<T> child = node.getRight() == null ? node.getLeft() : node.getRight();
            if (node.isLeft()) p.setLeft(child);
            else if (node.isRight()) p.setRight(child);
        } else {
            Node<T> r = node.getRight();
            if (r.getLeft() == null) {
                Node<T> l = node.getLeft();
                r.setLeft(l);
                if (node.isRight()) p.setRight(r);
                else if (node.isLeft()) p.setLeft(r);
                else setRoot(r);
            } else {
                Node<T> mlRight = getMaximumLeft(node.getRight());
                node.setKey(mlRight.getKey());
                remove(mlRight);
            }
        }
    }

    private Node<T> getMaximumLeft(Node<T> node) {
        if (node.getLeft() == null) return node;
        else return getMaximumLeft(node.getLeft());
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
        Node<T> successor = (Node<T>) find(key).successor();
        return successor != null ? successor.getKey() : null;
    }

    public T predecessor(T key) {
        Node<T> predecessor = (Node<T>) find(key).predecessor();
        return predecessor != null ? predecessor.getKey() : null;
    }
}

