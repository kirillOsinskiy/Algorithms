package com.osk.trees.redblack;

import com.osk.trees.Node;
import com.osk.trees.Side;

import java.util.Collection;

public class RBNode<T extends Comparable<T>> extends Node<T> {

    private Color color;

    RBNode(T key, RBNode<T> parent, Color color, Side side) {
        super(parent, key, side);
        this.color = color;
    }

    void addDummyBlackLeaves() {
        left = new RBNode<>(null, this, Color.BLACK, Side.LEFT);
        right = new RBNode<>(null, this, Color.BLACK, Side.RIGHT);
    }

    boolean isDummy() {
        return isBlack() && key == null;
    }

    public boolean isRed() {
        return color.equals(Color.RED);
    }

    public boolean isBlack() {
        return color.equals(Color.BLACK);
    }

    @Override
    public RBNode<T> getLeft() {
        return (RBNode<T>) left;
    }

    @Override
    public RBNode<T> getRight() {
        return (RBNode<T>) right;
    }

    @Override
    public RBNode<T> getParent() {
        return (RBNode<T>) parent;
    }

    void setChildLeft(RBNode<T> childLeft) {
        this.left = childLeft;
        childLeft.setSide(Side.LEFT);
        childLeft.setParent(this);
    }

    void setChildRight(RBNode<T> childRight) {
        this.right = childRight;
        childRight.setSide(Side.RIGHT);
        childRight.setParent(this);
    }

    Color getColor() {
        return color;
    }

    void setColor(Color color) {
        this.color = color;
    }

    RBNode<T> findNodeByKey(T targetKey) {
        if (key == null) {
            return null;
        }
        if (targetKey.compareTo(key) == 0) {
            return this;
        } else if (targetKey.compareTo(key) < 0) {
            return getLeft().findNodeByKey(targetKey);
        } else {
            return getRight().findNodeByKey(targetKey);
        }
    }

    RBNode<T> getSibling() {
        return isLeft() ? getParent().getRight() : getParent().getLeft();
    }

    Collection<T> getElements(Collection<T> elements) {
        if (!getLeft().isDummy()) getLeft().getElements(elements);
        elements.add(key);
        if (!getRight().isDummy()) getRight().getElements(elements);
        return elements;
    }

    RBNode<T> findNodeForInsertKey(T targetKey) {
        if (isDummy()) return this;
        if (targetKey.compareTo(key) == 0) {
//            System.err.println("Key already exists " + targetKey);
            return null;
        } else if (targetKey.compareTo(key) < 0) {
            return getLeft().findNodeForInsertKey(targetKey);
        } else {
            return getRight().findNodeForInsertKey(targetKey);
        }
    }
}
