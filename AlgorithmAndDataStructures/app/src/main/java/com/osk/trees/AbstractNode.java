package com.osk.trees;

/**
 * Created by Kirill on 30.06.2020.
 */
public abstract class AbstractNode<T extends Comparable<T>> {

    protected T key;
    protected Side side;
    protected AbstractNode<T> parent; // == null for root
    protected AbstractNode<T> right;
    protected AbstractNode<T> left;

    public AbstractNode(T key, Side side) {
        this.key = key;
        this.side = side;
    }

    protected void visit() {
        // pre-order
        if (left != null) left.visit();
        // in-order
        if (right != null) right.visit();
        // post-order
    }

    AbstractNode<T> successor() {
        if (right != null) {
            return findLastLeftChild(getRight());
        } else {
            if (Side.LEFT.equals(side)) {
                return getParent();
            } else {
                return findNearestParent(getParent(), Side.LEFT);
            }
        }
    }

    AbstractNode<T> predecessor() {
        if (left != null) {
            return findLastRightChild(getLeft());
        } else {
            if (Side.RIGHT.equals(side)) {
                return getParent();
            } else {
                return findNearestParent(getParent(), Side.RIGHT);
            }
        }
    }

    private static <T extends Comparable<T>> AbstractNode<T> findNearestParent(AbstractNode<T> node, Side side) {
        if (node == null || node.side == null) return null;
        AbstractNode<T> curNode = node;
        AbstractNode<T> par = curNode.getParent();
        while (curNode.side != null && !curNode.side.equals(side)) {
            curNode = par;
            par = curNode.getParent();
        }
        return par;
    }

    private static <T extends Comparable<T>> AbstractNode<T> findLastLeftChild(AbstractNode<T> node) {
        AbstractNode<T> parent = node;
        AbstractNode<T> child = parent.getLeft();
        while (child != null) {
            parent = child;
            child = parent.getLeft();
        }
        return parent;
    }

    private AbstractNode<T> findLastRightChild(AbstractNode<T> node) {
        AbstractNode<T> parent = node;
        AbstractNode<T> child = parent.getRight();
        while (child != null) {
            parent = child;
            child = parent.getRight();
        }
        return parent;
    }

    public AbstractNode<T> getRight() {
        return right;
    }

    public void setRight(AbstractNode<T> right) {
        this.right = right;
        if (right != null) {
            right.setParent(this);
            right.setSide(Side.RIGHT);
        }
    }

    public AbstractNode<T> getLeft() {
        return left;
    }

    public void setLeft(AbstractNode<T> left) {
        this.left = left;
        if (left != null) {
            left.setParent(this);
            left.setSide(Side.LEFT);
        }
    }

    public AbstractNode<T> getParent() {
        return (AbstractNode<T>) parent;
    }

    public void setParent(AbstractNode<T> parent) {
        this.parent = parent;
    }


    public void setKey(T key) {
        this.key = key;
    }

    public T getKey() {
        return key;
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
}
