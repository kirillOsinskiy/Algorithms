package com.osk.mycol;

public class BinomHeap<T extends Comparable<T>> implements Heap<T>, Comparable<BinomHeap<T>> {

    private Node<T> root;

    public BinomHeap(T[] data) {
        if(data.length != 0) {
            BinomHeap<T> heap = new BinomHeap<>(data[0]);
            for (int i = 1; i < data.length; i++) {
                heap = heap.meldHeap(new BinomHeap<>(data[i]));
            }
            root = heap.root;
        }
    }

    public BinomHeap(T data) {
        root = new Node<>(data);
    }

    public void insert(T data) {
        BinomHeap<T> singleHeap = new BinomHeap<>(data);
        this.meldHeap(singleHeap);
    }

    @Override
    public T remove(T key) {
        Node<T> node = findNodeByKey(key);
        if (node == null) return null;
        remove(node);
        return key;
    }

    private void remove(Node<T> node) {
        // meld children
        Node<T> minChild = findMinChild(node);
        if (minChild != null) {
            Node<T> minLeft = minChild.getLeft();
            Node<T> minRight = minChild.getRight();
            if (minLeft != null && minRight != null) {
                minLeft.setRight(minRight);
                minRight.setLeft(minLeft);
            } else if (minLeft == null && minRight != null) {
                minRight.setLeft(null);
            } else if (minLeft != null && minRight == null) {
                minLeft.setRight(null);
            }

            Node<T> firstChild = node.getFirstChild();
            if (!firstChild.equals(minChild)) {
                minChild.addChild(firstChild);
            } else if (minChild.getRight() != null) {
                minChild.addChild(minChild.getRight());
            }
            refrechChildrenParent(minChild);
            minChild.setParent(null);
            minChild.setRight(null);
            minChild.setLeft(null);
            node.setFirstChild(minChild);
        }

        Node<T> parent = node.getParent();
        if (parent == null) return;
        Node<T> left = node.getLeft();
        if (left == null) {
            parent.setFirstChild(null);
        } else {
            left.setRight(node.getRight());
        }
        if (minChild != null) {
            parent.addChild(minChild);
        }
    }

    private void refrechChildrenParent(Node<T> node) {
        Node<T> child = node.getFirstChild();
        while(child != null) {
            child.setParent(node);
            child = child.getRight();
        }
    }

    private Node<T> findMinChild(Node<T> node) {
        Node<T> minNode = node.getFirstChild();
        if (minNode == null) return null;
        Node<T> right = minNode.getRight();
        if (right == null) return minNode;
        while (right != null) {
            if (minNode.compareTo(right) > 0) minNode = right;
            right = right.getRight();
        }

        return minNode;
    }

    @Override
    public T getMin() {
        return root.getData();
    }

    @Override
    public T extractMin() {
        T result = root.getData();
        remove(root);
        root = root.getFirstChild();
        return result;
    }

    public void decreaseKey(T oldKey, T newKey) {
        if (newKey.compareTo(oldKey) > 0) throw new RuntimeException("New key should be less than old!");
        Node<T> keyNode = findNodeByKey(oldKey);
        if (keyNode != null) {
            keyNode.setData(newKey);
            siftUp(keyNode);
        }
    }

    public BinomHeap<T> meldHeap(BinomHeap<T> other) {
        if (this.compareTo(other) > 0) {
            other.root.addChild(root);
            return other;
        } else {
            root.addChild(other.root);
            return this;
        }
    }

    private void siftDown(Node<T> keyNode) {
        Node<T> node = keyNode.getFirstChild();
        Node<T> minNode = node;
        while (node != null) {
            node = node.getRight();
            if (minNode.compareTo(node) > 0) minNode = node;
        }
        if (keyNode.compareTo(minNode) > 0) {
            T buf = keyNode.getData();
            keyNode.setData(minNode.getData());
            minNode.setData(buf);
            siftDown(minNode);
        }
    }

    private void siftUp(Node<T> keyNode) {
        Node<T> parentNode = keyNode.getParent();
        if (keyNode.compareTo(parentNode) < 0) {
            T buf = parentNode.getData();
            parentNode.setData(keyNode.getData());
            keyNode.setData(buf);
            siftUp(keyNode.getParent());
        }
    }

    private Node<T> findNodeByKey(T key) {
        if (root.getData().equals(key)) {
            return root;
        }
        Node<T> childNode = root.getFirstChild();
        if (childNode.getData().equals(key)) return childNode;
        while (!childNode.getData().equals(key)) {
            Node<T> rightNode = childNode.getRight();
            while (!rightNode.getData().equals(key)) {
                rightNode = rightNode.getRight();
                if (rightNode.getData().equals(key)) return rightNode;
            }
            childNode = childNode.getFirstChild();
            if (childNode.getData().equals(key)) return childNode;
        }
        return null;
    }

    @Override
    public int compareTo(BinomHeap<T> o) {
        return root.compareTo(o.root);
    }
}

class Node<T extends Comparable<T>> implements Comparable<Node<T>> {

    private T data;
    private Node<T> parent;
    private Node<T> left;
    private Node<T> right;
    private Node<T> firstChild;

    Node(T data) {
        this.data = data;
    }

    void addChild(Node<T> child) {
        child.setParent(this);
        if (firstChild == null) {
            firstChild = child;
        } else {
            firstChild.addRight(child);
        }
    }

    private void addRight(Node<T> newNode) {
        if (right == null) {
            right = newNode;
            right.setLeft(this);
        } else {
            right.addRight(newNode);
        }
    }

    @Override
    public int compareTo(Node<T> o) {
        return data.compareTo(o.data);
    }

    T getData() {
        return data;
    }

    Node<T> getRight() {
        return right;
    }

    Node<T> getFirstChild() {
        return firstChild;
    }

    void setRight(Node<T> right) {
        this.right = right;
    }

    void setFirstChild(Node<T> firstChild) {
        this.firstChild = firstChild;
    }

    Node<T> getParent() {
        return parent;
    }

    void setParent(Node<T> parent) {
        this.parent = parent;
    }

    void setData(T data) {
        this.data = data;
    }

    Node<T> getLeft() {
        return left;
    }

    void setLeft(Node<T> left) {
        this.left = left;
    }
}
