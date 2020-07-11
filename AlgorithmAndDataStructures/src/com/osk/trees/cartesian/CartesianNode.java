package com.osk.trees.cartesian;

import com.osk.trees.AbstractNode;
import com.osk.trees.Side;

/**
 * Created by Kirill on 30.06.2020.
 */
public class CartesianNode<T extends Comparable<T>, U extends Comparable<U>> extends AbstractNode<T> {

    private U priority;

    public U getPriority() {
        return priority;
    }

    public void setPriority(U priority) {
        this.priority = priority;
    }

    public CartesianNode<T, U> getParent() {
        return (CartesianNode<T, U>) parent;
    }

    public CartesianNode<T, U> getRight() {
        return (CartesianNode<T, U>) right;
    }

    public CartesianNode<T, U> getLeft() {
        return (CartesianNode<T, U>) left;
    }

    public CartesianNode(CartesianNode<T, U> parent, T key, U priority, Side side) {
        super(key, side);
        this.priority = priority;
        this.parent = parent;
    }

}