package com.osk.trees.cartesian;

import com.osk.trees.AbstractTree;

/**
 * Created by Kirill on 30.06.2020.
 */
public class CartesianTree<T extends Comparable<T>, U extends Comparable<U>>
        extends AbstractTree<T, CartesianNode<T, U>> {

    public CartesianTree(CartesianNode<T, U> node) {
        super(node);
    }

    public CartesianTree(T key, U priority) {
        super(key);
        root = new CartesianNode<>(null, key, priority, null);
    }

    public void remove(T key) {
        SplitResult splitResult = split(key);
        merge(splitResult.getLesserTree(), splitResult.getGreaterTree());
    }

    public CartesianNode<T, U> insert(T key, U prior) {
        CartesianNode<T, U> res = find(key);
        if (res != null) return res;

        SplitResult splitResult = split(key);
        CartesianNode<T, U> insertedNode = new CartesianNode<>(null, key, prior, null);
        CartesianTree<T, U> insertedTree = new CartesianTree<>(insertedNode);
        merge(
                merge(splitResult.getLesserTree(), insertedTree)
                , splitResult.getGreaterTree());
        return insertedNode;
    }

    /**
     * Merge trees. All keys in tree1 =< all keys in tree2
     *
     * @param tree1 tree with smaller keys
     * @param tree2 tree with greater keys
     * @return merged tree
     */
    private static <T extends Comparable<T>, U extends Comparable<U>> CartesianTree<T, U> merge(
            CartesianTree<T, U> tree1, CartesianTree<T, U> tree2) {
        if (tree1 == null && tree2 == null) return null;
        else if (tree1 == null) return tree2;
        else if (tree2 == null) return tree1;

        CartesianNode<T, U> root2 = tree2.getRoot();
        CartesianNode<T, U> node = tree1.getMaximum();
        while (node.getPriority().compareTo(root2.getPriority()) > 0) {
            node = node.getParent();
        }
        CartesianNode<T, U> tempTree = node.getRight();
        node.setRight(root2);
        if(tempTree != null) {
            node = tree2.getMinimum();
            while (node.getPriority().compareTo(tempTree.getPriority()) > 0) {
                node = node.getParent();
            }
            CartesianNode<T, U> left = node.getLeft();
            node.setLeft(tempTree);
            while (tempTree.getRight() != null) {
                tempTree = tempTree.getRight();
            }
            tempTree.setRight(left);
        }

        return tree1;
    }

    /**
     * Split tree by key. If tree contains the key, then result will have two tree and node with that key.
     * First tree will have keys lesser than split key and second tree will have keys greater than split key.
     * Otherwise if tree does not contains key, then node in result will be null.
     *
     * @param key split key
     * @return lesserTree, greaterTree, node
     */
    private SplitResult split(T key) {
        CartesianNode<T, U> node = find(key);
        if (node == null) {
            // try to find node where parent key is less and current key is greater
            node = this.getRoot();
            while (node != null && key.compareTo(node.getKey()) > 0) {
                node = node.getRight();
            }
            if (node == null) {
                return new SplitResult(this, null, null);
            }
            CartesianNode<T, U> parent = node.getParent();
            if (parent == null) {
                return new SplitResult(null, this, null);
            }
            if (node.isLeft()) parent.setLeft(null);
            else parent.setRight(null);
            node.setParent(null);
            node.setSide(null);
            return new SplitResult(this, new CartesianTree<>(node), null);
        } else {
            CartesianNode<T, U> parent = node.getParent();
            if(parent != null) {
                if (node.isLeft()) parent.setLeft(null);
                else parent.setRight(null);
                node.setParent(null);
                node.setSide(null);
            }

            CartesianTree<T, U> nodeTreeLeft = node.getLeft() != null ? new CartesianTree<>(node.getLeft()) : null;
            CartesianTree<T, U> nodeTreeRight = node.getRight() != null ? new CartesianTree<>(node.getRight()) : null;
            return new SplitResult(this, merge(nodeTreeLeft, nodeTreeRight), node);
        }
    }

    class SplitResult {
        /**
         * All keys in lesserTree is smaller than key in node.
         * If all keys in original tree is greater than split key, this field is null.
         */
        private CartesianTree<T, U> lesserTree;

        /**
         * All keys in greaterTree is bigger than key in node.
         * If all keys in original tree is less than split key, this filed value is null.
         */
        private CartesianTree<T, U> greaterTree;

        /**
         * Node that contains key for split. Not null if tree contains that key, otherwise null.
         */
        private CartesianNode<T, U> node;

        public SplitResult(CartesianTree<T, U> lesserTree, CartesianTree<T, U> greaterTree, CartesianNode<T, U> node) {
            this.lesserTree = lesserTree;
            this.greaterTree = greaterTree;
            this.node = node;
        }

        public CartesianTree<T, U> getLesserTree() {
            return lesserTree;
        }

        public CartesianTree<T, U> getGreaterTree() {
            return greaterTree;
        }

        public CartesianNode<T, U> getNode() {
            return node;
        }
    }
}
