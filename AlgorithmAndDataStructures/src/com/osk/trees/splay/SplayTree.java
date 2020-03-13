package com.osk.trees.splay;

import com.osk.trees.Node;
import com.osk.trees.Side;

public class SplayTree<T extends Comparable<T>> {

    private Node<T> root;

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

        if(x.isLeft() && p.isLeft()) {
            Node<T> pRight = p.getRight();
            p.setRight(g);
            g.setLeft(pRight);

            Node<T> xRight = x.getRight();
            p.setLeft(xRight);
            x.setRight(p);
        } else if(x.isRight() && p.isRight()) {
            Node<T> pLeft = p.getLeft();
            p.setLeft(g);
            g.setRight(pLeft);

            Node<T> xLeft= x.getLeft();
            p.setRight(xLeft);
            x.setLeft(p);
        }

        if(gParent != null) {
            if(gSide.equals(Side.LEFT)) {
                gParent.setLeft(x);
            } else if(gSide.equals(Side.RIGHT)) {
                gParent.setRight(x);
            }
        } else {
            root = x;
        }
    }

    private void zigzag(Node<T> x) {
        Node<T> p = x.getParent();
        if(p == null) return;
        Node<T> g = p.getParent();
        if(g == null) return;

        Node<T> gParent = g.getParent();
        Side gSide = g.getSide();

        if(x.isRight() && p.isLeft()) {
            Node<T> xLeft = x.getLeft();
            Node<T> xRight = x.getRight();

            x.setLeft(p);
            x.setRight(g);
            g.setLeft(xRight);
            p.setRight(xLeft);
        } else if(x.isLeft() && p.isRight()) {
            Node<T> xLeft = x.getLeft();
            Node<T> xRight = x.getRight();

            x.setLeft(g);
            x.setRight(p);
            g.setRight(xLeft);
            p.setLeft(xRight);
        }

        if(gParent != null) {
            if(gSide.equals(Side.LEFT)) {
                gParent.setLeft(x);
            } else if(gSide.equals(Side.RIGHT)) {
                gParent.setRight(x);
            }
        } else {
            root = x;
        }
    }
}

