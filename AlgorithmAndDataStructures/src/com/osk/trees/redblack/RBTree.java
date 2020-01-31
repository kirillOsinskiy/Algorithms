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

    public void rotateLeft(RBNode<T> node) {
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

    public void rotateRight(RBNode<T> node) {
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
        if (node.getParent().isBlack()) {
            return;
        } else {
            insertCase3(node);
        }
    }

    private void insertCase3(RBNode<T> node) {
        RBNode<T> uncle = getUncle(node);
        if (uncle != null && uncle.isRed()) {
            RBNode<T> granpa = getGrandparent(node);
            node.getParent().setColor(Color.BLACK);
            uncle.setColor(Color.BLACK);
            granpa.setColor(Color.RED);
            insertCase1(granpa);
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
        RBNode<T> granpa = getGrandparent(node);
        node.getParent().setColor(Color.BLACK);
        granpa.setColor(Color.RED);
        if (node.isLeft() && node.getParent().isLeft()) {
            rotateRight(granpa);
        } else {
            rotateLeft(granpa);
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

    void setRoot(RBNode<T> root) {
        this.root = root;
    }

    public RBNode<T> getRoot() {
        return root;
    }

    public void remove(T key) {
        RBNode<T> target = root.findNodeByKey(key);
        target.remove();
    }

    public Collection<T> getAllElements() {
        List<T> elements = new ArrayList<>();
        return root.getElements(elements);
    }
}
