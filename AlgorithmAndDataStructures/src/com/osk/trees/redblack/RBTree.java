package com.osk.trees.redblack;

import java.util.*;

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

    private void replaceWithNode(RBNode<T> node, RBNode<T> child) {
        RBNode<T> parent = node.getParent();
        if(parent != null) {
            if (node.isLeft()) parent.setChildLeft(child);
            else parent.setChildRight(child);
        } else {
            child.setParent(null);
            child.setSide(null);
            root = child;
        }
    }

    private void deleteOneChild(RBNode<T> node) {
        if (!node.getChildLeft().isDummy() && !node.getChildRight().isDummy()) return;

        RBNode<T> child = node.getChildLeft().isDummy() ? node.getChildRight() : node.getChildLeft();
        replaceWithNode(node, child);
        if (node.isBlack()) {
            if (child.isRed()) {
                child.setColor(Color.BLACK);
            } else {
                deleteCase1(child);
            }
        }
    }

    private void deleteCase1(RBNode<T> node) {
        if (node.getParent() != null) deleteCase2(node);
    }

    private void deleteCase2(RBNode<T> node) {
        RBNode<T> sibling = node.getSibling();
        if (sibling.isRed()) {
            node.getParent().setColor(Color.RED);
            sibling.setColor(Color.BLACK);
            if (node.isLeft()) {
                rotateLeft(node.getParent());
            } else {
                rotateRight(node.getParent());
            }
        }
        deleteCase3(node);
    }

    private void deleteCase3(RBNode<T> node) {
        RBNode<T> sibling = node.getSibling();
        if (node.getParent().isBlack()
                && sibling.isBlack()
                && sibling.getChildLeft().isBlack()
                && sibling.getChildRight().isBlack()) {
            sibling.setColor(Color.RED);
            deleteCase1(node.getParent());
        } else {
            deleteCase4(node);
        }
    }

    private void deleteCase4(RBNode<T> node) {
        RBNode<T> sibling = node.getSibling();

        if (node.getParent().isRed() &&
                sibling.isBlack() &&
                sibling.getChildLeft().isBlack() &&
                sibling.getChildRight().isBlack()) {
            sibling.setColor(Color.RED);
            node.getParent().setColor(Color.BLACK);
        } else {
            deleteCase5(node);
        }
    }

    private void deleteCase5(RBNode<T> node) {

        RBNode<T> sibling = node.getSibling();

        if (sibling.isBlack()) {
            if (node.isLeft()
                    && sibling.getChildRight().isBlack()
                    && sibling.getChildLeft().isRed()) {/* this last test is trivial too due to cases 2-4. */
                sibling.setColor(Color.RED);
                sibling.getChildLeft().setColor(Color.BLACK);
                rotateRight(sibling);
            } else if (node.isRight()
                    && sibling.getChildLeft().isBlack()
                    && sibling.getChildRight().isRed()) {/* this last test is trivial too due to cases 2-4. */
                sibling.setColor(Color.RED);
                sibling.getChildRight().setColor(Color.BLACK);
                rotateLeft(sibling);
            }
        }
        deleteCase6(node);
    }

    private void deleteCase6(RBNode<T> node) {
        RBNode<T> sibling = node.getSibling();

        sibling.setColor(node.getParent().getColor());
        node.getParent().setColor(Color.BLACK);

        if (node.isLeft()) {
            sibling.getChildRight().setColor(Color.BLACK);
            rotateLeft(node.getParent());
        } else {
            sibling.getChildLeft().setColor(Color.BLACK);
            rotateRight(node.getParent());
        }
    }

    public RBNode<T> getRoot() {
        return root;
    }

    public void remove(T key) {
        RBNode<T> target = root.findNodeByKey(key);
        if(target == null) return;
        if(!target.getChildLeft().isDummy() && !target.getChildRight().isDummy()) {
            RBNode<T> maxNode = findMaxNode(target.getChildLeft());
            T buf = target.getKey();
            target.setKey(maxNode.getKey());
            maxNode.setKey(buf);
            target = maxNode;
        }
        deleteOneChild(target);
    }

    private RBNode<T> findMaxNode(RBNode<T> node) {
        while(!node.getChildRight().isDummy()) {
            node = node.getChildRight();
        }
        return node;
    }

    public Collection<T> getAllElements() {
        Set<T> elements = new TreeSet<>();
        return root.isDummy() ? elements : root.getElements(elements);
    }
}
