package com.osk.trees.redblack;

import com.osk.trees.Side;

import java.util.Collection;

public class RBNode<T extends Comparable<T>> {

    private T key;
    private Side side;
    private Color color;
    private RBNode<T> parent;
    private RBNode<T> childLeft;
    private RBNode<T> childRight;

    RBNode(T key, RBNode<T> parent, Color color, Side side) {
        this.key = key;
        this.parent = parent;
        this.color = color;
        this.side = side;
    }

    void addDummyBlackLeaves() {
        childLeft = new RBNode<>(null, this, Color.BLACK, Side.LEFT);
        childRight = new RBNode<>(null, this, Color.BLACK, Side.RIGHT);
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

    public RBNode<T> getChildLeft() {
        return childLeft;
    }

    public RBNode<T> getChildRight() {
        return childRight;
    }

    void setChildLeft(RBNode<T> childLeft) {
        this.childLeft = childLeft;
        childLeft.setSide(Side.LEFT);
        childLeft.setParent(this);
    }

    void setChildRight(RBNode<T> childRight) {
        this.childRight = childRight;
        childRight.setSide(Side.RIGHT);
        childRight.setParent(this);
    }

    public RBNode<T> getParent() {
        return parent;
    }

    void setParent(RBNode<T> parent) {
        this.parent = parent;
    }

    public T getKey() {
        return key;
    }

    void setKey(T key) {
        this.key = key;
    }

    public Side getSide() {
        return side;
    }

    void setSide(Side side) {
        this.side = side;
    }

    Color getColor() {
        return color;
    }

    void setColor(Color color) {
        this.color = color;
    }

    boolean isRight() {
        return Side.RIGHT.equals(side);
    }

    boolean isLeft() {
        return Side.LEFT.equals(side);
    }

    RBNode<T> findNodeByKey(T targetKey) {
        if(key == null) {
            return null;
        }
        if (targetKey.compareTo(key) == 0) {
            return this;
        } else if (targetKey.compareTo(key) < 0) {
            return childLeft.findNodeByKey(targetKey);
        } else {
            return childRight.findNodeByKey(targetKey);
        }
    }

    RBNode<T> getSibling() {
        return isLeft() ? parent.getChildRight() : parent.getChildLeft();
    }

    Collection<T> getElements(Collection<T> elements) {
        if (!childLeft.isDummy()) childLeft.getElements(elements);
        elements.add(key);
        if (!childRight.isDummy()) childRight.getElements(elements);
        return elements;
    }

    RBNode<T> findNodeForInsertKey(T targetKey) {
        if (isDummy()) return this;
        if (targetKey.compareTo(key) == 0) {
//            System.err.println("Key already exists " + targetKey);
            return null;
        } else if (targetKey.compareTo(key) < 0) {
            return childLeft.findNodeForInsertKey(targetKey);
        } else {
            return childRight.findNodeForInsertKey(targetKey);
        }
    }
}
