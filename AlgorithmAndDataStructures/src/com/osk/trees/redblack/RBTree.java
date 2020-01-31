package com.osk.trees.redblack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RBTree<T extends Comparable<T>> {

    private RBNode<T> root = new RBNode<>(null, null, Color.BLACK, null);

    public RBTree(T key) {
        insert(key);
    }

    public RBTree(Collection<T> data) {
        for (T key : data) {
            insert(key);
        }
    }

    private void rotateLeft(RBNode<T> node) {
        if (node.getChildRight().isDummy()) return;
        RBNode<T> pivot = node.getChildRight();
        RBNode<T> parent = node.getParent();

        if (parent != null) {
            if (node.isLeft()) parent.setChildLeft(pivot);
            else parent.setChildRight(pivot);
        } else {
            pivot.setParent(null);
            pivot.setSide(null);
            root = pivot;
        }
        node.setChildRight(pivot.getChildLeft());
        pivot.setChildLeft(node);
    }

    private void rotateRight(RBNode<T> node) {
        if (node.getChildLeft().isDummy()) return;
        RBNode<T> pivot = node.getChildLeft();
        RBNode<T> parent = node.getParent();

        if (parent != null) {
            if (node.isLeft()) parent.setChildLeft(pivot);
            else parent.setChildRight(pivot);
        } else {
            pivot.setParent(null);
            pivot.setSide(null);
            root = pivot;
        }

        node.setChildLeft(pivot.getChildRight());
        pivot.setChildRight(node);
    }

    public void insert(T key) {
        RBNode<T> nodeForInsert = root.findNodeForInsertKey(key);
        if (nodeForInsert == null) return;
        nodeForInsert.setKey(key);
        nodeForInsert.addDummyBlackLeaves();
        nodeForInsert.setColor(Color.RED);
        insertCase1(nodeForInsert);
    }

    private void insertCase1(RBNode<T> node) {
        if (node.getParent() == null) {
            node.setColor(Color.BLACK);
        } else {
            insertCase2(node);
        }
    }

    private void insertCase2(RBNode<T> node) {
        if (!node.getParent().isBlack()) {
            insertCase3(node);
        }
    }

    private void insertCase3(RBNode<T> node) {
        RBNode<T> uncle = getUncle(node);
        if (uncle != null && uncle.isRed()) {
            RBNode<T> grandparent = getGrandparent(node);
            node.getParent().setColor(Color.BLACK);
            uncle.setColor(Color.BLACK);
            grandparent.setColor(Color.RED);
            insertCase1(grandparent);
        } else {
            insertCase4(node);
        }
    }

    private void insertCase4(RBNode<T> node) {
        if (node.isRight() && node.getParent().isLeft()) {
            rotateLeft(node.getParent());
            node = node.getChildLeft();
        } else if (node.isLeft() && node.getParent().isRight()) {
            rotateRight(node.getParent());
            node = node.getChildRight();
        }
        insertCase5(node);
    }

    private void insertCase5(RBNode<T> node) {
        RBNode<T> grandparent = getGrandparent(node);
        node.getParent().setColor(Color.BLACK);
        grandparent.setColor(Color.RED);
        if (node.isLeft() && node.getParent().isLeft()) {
            rotateRight(grandparent);
        } else {
            rotateLeft(grandparent);
        }
    }

    private RBNode<T> getUncle(RBNode<T> node) {
        RBNode<T> parent = node.getParent();
        if (parent == null || parent.getParent() == null) {
            return null;
        }
        return parent.isLeft() ? parent.getParent().getChildRight() : parent.getParent().getChildLeft();
    }

    private RBNode<T> getGrandparent(RBNode<T> node) {
        RBNode<T> parent = node.getParent();
        if (parent == null || parent.getParent() == null) {
            return null;
        }
        return parent.getParent();
    }

    public RBNode<T> getRoot() {
        return root;
    }

    public void remove(T key) {
        // todo implement this
        RBNode<T> target = root.findNodeByKey(key);
        target.remove();
    }

    public Collection<T> getAllElements() {
        List<T> elements = new ArrayList<>();
        return root.getElements(elements);
    }
}
