package com.osk.trees;

class Node<T extends Comparable<T>> implements Comparable<Node<T>> {

    private T key;
    private Side side; // == null for root
    private Node<T> childRight;
    private Node<T> childLeft;
    private Node<T> parent; // == null for root

    Node(Node<T> parent, T key, Side side) {
        this.parent = parent;
        this.key = key;
        this.side = side;
    }

    public T getKey() {
        return key;
    }

    Node<T> getChildRight() {
        return childRight;
    }

    private void setChildRight(Node<T> childRight) {
        this.childRight = childRight;
    }

    Node<T> getChildLeft() {
        return childLeft;
    }

    private void setChildLeft(Node<T> childLeft) {
        this.childLeft = childLeft;
    }

    void insert(T newKey) {
        if (newKey.compareTo(key) < 0) {
            if (childLeft != null) {
                childLeft.insert(newKey);
            } else {
                childLeft = new Node<>(this, newKey, Side.LEFT);
            }
        } else {
            if (childRight != null) {
                childRight.insert(newKey);
            } else {
                childRight = new Node<>(this, newKey, Side.RIGHT);
            }
        }
    }

    Node<T> remove(T rmKey) {
        if (rmKey.equals(key)) {
            if (childLeft == null && childRight == null) {
                if (side == null) {
                    throw new RuntimeException("Root remove");
                } else if (side.equals(Side.LEFT)) {
                    parent.setChildLeft(null);
                } else {
                    parent.setChildRight(null);
                }
            } else if (childLeft == null || childRight == null) {
                Node<T> child = childLeft == null ? childRight : childLeft;
                if (side == null) {
                    throw new RuntimeException("Root remove");
                } else if (side.equals(Side.LEFT)) {
                    parent.setChildLeft(child);
                } else {
                    parent.setChildRight(child);
                }
            } else {
                Node<T> child = childLeft;
                child.insert(childRight);
                if (side == null) {
                    throw new RuntimeException("Root remove");
                } else if (side.equals(Side.LEFT)) {
                    parent.setChildLeft(childLeft);
                } else {
                    parent.setChildRight(child);
                }
            }
            return this;
        } else if (rmKey.compareTo(key) < 0) {
            return childLeft.remove(rmKey);
        } else {
            return childRight.remove(rmKey);
        }
    }

    private void insert(Node<T> newNode) {
        if (newNode.compareTo(this) < 0) {
            if (childLeft == null) {
                childLeft = newNode;
            } else {
                childLeft.insert(newNode);
            }
        } else if (newNode.compareTo(this) > 0) {
            if (childRight == null) {
                childRight = newNode;
            } else {
                childRight.insert(newNode);
            }
        }
    }

    void visit() {
        // pre-order
        if (childLeft != null) childLeft.visit();
        // in-order
        if (childRight != null) childRight.visit();
        // post-order
    }

    private String getStringView(StringBuilder sb) {
        if (childLeft != null) childLeft.getStringView(sb);
        sb.append(key).append(" ");
        if (childRight != null) childRight.getStringView(sb);
        return sb.toString();
    }

    @Override
    public String toString() {
        return getStringView(new StringBuilder());
    }

    @Override
    public int compareTo(Node<T> o) {
        return key.compareTo(o.key);
    }

    Node<T> successor() {
        if(childRight != null) {
            return findLastLeftChild(childRight);
        } else {
            if(side.equals(Side.LEFT)) {
                return parent;
            } else {
                return findNearestParent(parent, Side.LEFT);
            }
        }
    }

    Node<T> predecessor() {
        if(childLeft != null) {
            return findLastRightChild(childLeft);
        } else {
            if(side.equals(Side.RIGHT)) {
                return parent;
            } else {
                return findNearestParent(parent, Side.RIGHT);
            }
        }
    }

    private static<T extends Comparable<T>> Node<T> findNearestParent(Node<T> node, Side side) {
        Node<T> curNode = node;
        Node<T> par = curNode.parent;
        while(curNode.side != null && !curNode.side.equals(side)) {
            if(curNode.side == null) return null;
            curNode = par;
            par = curNode.parent;
        }
        return par;
    }

    private static<T extends Comparable<T>> Node<T> findLastLeftChild(Node<T> node) {
        Node<T> parent = node;
        Node<T> child = parent.childLeft;
        while(child != null) {
            parent = child;
            child = parent.childLeft;
        }
        return parent;
    }

    private Node<T> findLastRightChild(Node<T> node) {
        Node<T> parent = node;
        Node<T> child = parent.childRight;
        while(child != null) {
            parent = child;
            child = parent.childRight;
        }
        return parent;
    }

    Node<T> findChildByKey(T sKey) {
        if (key.compareTo(sKey) > 0) {
            return childLeft.findChildByKey(sKey);
        } else if(key.compareTo(sKey) < 0) {
            return childRight.findChildByKey(sKey);
        } else {
            return this;
        }
    }
}
