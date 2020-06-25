package com.osk.trees;

public class SplayTree<T extends Comparable<T>> extends BinaryTree<T> {

    public SplayTree(T key) {
        super(key);
    }

    private SplayTree(Node<T> node) {
        super(node);
    }

    @Override
    public Node<T> find(T key) {
        Node<T> node = super.find(key);
        splay(node);
        return node;
    }

    @Override
    public Node<T> insert(T key) {
        Node<T> node = super.insert(key);
        splay(node);
        return node;
    }

    public void removeWithMerge(T key) {
        Node<T> node = find(key);
        if(node.getLeft() != null && node.getRight() != null) {
            SplayTree<T> t1 = new SplayTree<>(node.getLeft());
            SplayTree<T> t2 = new SplayTree<>(node.getRight());
            setRoot(merge(t1, t2).getRoot());
        } else if(node.getLeft() == null && node.getRight() == null) {
            setRoot(null);
        } else if(node.getLeft() == null) {
            setRoot(node.getRight());
        } else {
            setRoot(node.getLeft());
        }
    }

    public static <T extends Comparable<T>> SplayTree<T> merge(SplayTree<T> t1, SplayTree<T> t2) {
        t1.splay(t1.getMaximum());
        t1.getRoot().setRight(t2.getRoot());
        return t1;
    }

    private void transplant(Node<T> node, Node<T> child) {
        if (node.getParent() == null) setRoot(child);
        else if (node.isLeft()) node.getParent().setLeft(child);
        else if (node.isRight()) node.getParent().setRight(child);
    }

    private void leftRotate(Node<T> node) {
        Node<T> right = node.getRight();
        node.setRight(right.getLeft());
        transplant(node, right);
        right.setLeft(node);
    }

    private void rightRotate(Node<T> node) {
        Node<T> left = node.getLeft();
        node.setLeft(left.getRight());
        transplant(node, left);
        left.setRight(node);
    }

    void splay(Node<T> node) {
        while (node != root) {
            if (node.getParent() == root) {
                if (node.isLeft())
                    rightRotate(node.getParent());
                else if (node.isRight()) {
                    leftRotate(node.getParent());
                }
            } else {
                // Zig-Zig step.
                if (node.isLeft() && node.getParent().isLeft()) {
                    rightRotate(node.getParent().getParent());
                    rightRotate(node.getParent());
                } else if (node.isRight() && node.getParent().isRight()) {
                    leftRotate(node.getParent().getParent());
                    leftRotate(node.getParent());
                }
                // Zig-Zag step.
                else if (node.isRight() && node.getParent().isLeft()) {
                    leftRotate(node.getParent());
                    rightRotate(node.getParent());
                } else if (node.isLeft() && node.getParent().isRight()) {
                    rightRotate(node.getParent());
                    leftRotate(node.getParent());
                }
            }
        }
    }
}

