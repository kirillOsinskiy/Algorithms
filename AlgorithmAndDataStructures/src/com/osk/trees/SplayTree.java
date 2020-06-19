package com.osk.trees;

public class SplayTree<T extends Comparable<T>> extends BinaryTree<T> {

    public SplayTree(T key) {
        super(key);
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

    void transplant(Node<T> node, Node<T> child) {
        if (node.getParent() == null) setRoot(child);
        else if (node.isLeft()) node.getParent().setLeft(child);
        else if (node.isRight()) node.getParent().setRight(child);
    }

    void leftRotate(Node<T> node) {
        Node<T> right = node.getRight();
        node.setRight(right.getLeft());
        transplant(node, right);
        right.setLeft(node);
    }

    void rightRotate(Node<T> node) {
        Node<T> left = node.getLeft();
        node.setLeft(left.getRight());
        transplant(node, left);
        left.setRight(node);
    }

    void splay(Node<T> node) {
        while (node != root) {
            if (node.getParent() == root) {
                if (node.isLeft())
                    rightRotate(root);
                else if (node.isRight()) {
                    leftRotate(root);
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

