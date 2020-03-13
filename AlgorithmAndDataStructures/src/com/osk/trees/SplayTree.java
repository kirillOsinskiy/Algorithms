package com.osk.trees;

import com.osk.trees.BinaryTree;
import com.osk.trees.Node;
import com.osk.trees.Side;

public class SplayTree<T extends Comparable<T>> extends BinaryTree<T> {

    public SplayTree(T key) {
        super(key);
    }

    private void zig(Node<T> x) {
        Node<T> p = x.getParent();
        if (root != p) return;
        p.setLeft(x.getRight());
        x.setRight(p);
        x.setParent(null);
        root = x;
    }

    private void zigzig(Node<T> x) {
        Node<T> p = x.getParent();
        if (p == null) return;
        Node<T> g = p.getParent();
        if (g == null) return;

        Node<T> gParent = g.getParent();
        Side gSide = g.getSide();

        if (x.isLeft() && p.isLeft()) {
            Node<T> pRight = p.getRight();
            p.setRight(g);
            g.setLeft(pRight);

            Node<T> xRight = x.getRight();
            p.setLeft(xRight);
            x.setRight(p);
        } else if (x.isRight() && p.isRight()) {
            Node<T> pLeft = p.getLeft();
            p.setLeft(g);
            g.setRight(pLeft);

            Node<T> xLeft = x.getLeft();
            p.setRight(xLeft);
            x.setLeft(p);
        }

        if (gParent != null) {
            if (gSide.equals(Side.LEFT)) {
                gParent.setLeft(x);
            } else if (gSide.equals(Side.RIGHT)) {
                gParent.setRight(x);
            }
        } else {
            root = x;
        }
    }

    private void zigzag(Node<T> x) {
        Node<T> p = x.getParent();
        if (p == null) return;
        Node<T> g = p.getParent();
        if (g == null) return;

        Node<T> gParent = g.getParent();
        Side gSide = g.getSide();

        if (x.isRight() && p.isLeft()) {
            Node<T> xLeft = x.getLeft();
            Node<T> xRight = x.getRight();

            x.setLeft(p);
            x.setRight(g);
            g.setLeft(xRight);
            p.setRight(xLeft);
        } else if (x.isLeft() && p.isRight()) {
            Node<T> xLeft = x.getLeft();
            Node<T> xRight = x.getRight();

            x.setLeft(g);
            x.setRight(p);
            g.setRight(xLeft);
            p.setLeft(xRight);
        }

        if (gParent != null) {
            if (gSide.equals(Side.LEFT)) {
                gParent.setLeft(x);
            } else if (gSide.equals(Side.RIGHT)) {
                gParent.setRight(x);
            }
        } else {
            root = x;
        }
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
        if (node.getParent() == null) root = node;
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
                    rightRotate(node);
                else if (node.isRight()) {
                    leftRotate(node);
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

