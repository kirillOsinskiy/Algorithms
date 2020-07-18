package com.osk.trees.cartesian;

import com.osk.trees.AbstractTree;
import com.osk.trees.Side;

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
        SplitResult<T, U> splitResult = split(key);
        CartesianNode<T, U> res = merge(splitResult.getLesser(), splitResult.getGreater());
        setRoot(res);
    }

    public CartesianNode<T, U> insert(T key, U prior) {
        CartesianNode<T, U> res = findForInsert(key, prior);
        CartesianNode<T, U> parent = res.getParent();
        while (parent != null && parent.getPriority().compareTo(res.getPriority()) > 0) {
            if (res.isLeft()) rotateRight(res);
            else rotateLeft(res);
            parent = res.getParent();
        }
        return res;
    }

    private void rotateRight(CartesianNode<T, U> node) {
        CartesianNode<T, U> right = node.getRight();
        CartesianNode<T, U> parent = node.getParent();
        CartesianNode<T, U> gp = parent.getParent();
        if (gp != null) {
            if (parent.isLeft()) gp.setLeft(node);
            else gp.setRight(node);
        } else {
            setRoot(node);
        }
        node.setRight(parent);
        parent.setLeft(right);
    }

    private void rotateLeft(CartesianNode<T, U> node) {
        CartesianNode<T, U> left = node.getLeft();
        CartesianNode<T, U> parent = node.getParent();
        CartesianNode<T, U> gp = parent.getParent();
        if (gp != null) {
            if (parent.isLeft()) gp.setLeft(node);
            else gp.setRight(node);
        } else {
            setRoot(node);
        }
        node.setLeft(parent);
        parent.setRight(left);
    }

    public CartesianNode<T, U> findForInsert(T key, U prior) {
        return findForInsert(key, prior, root);
    }

    public CartesianNode<T, U> findForInsert(T key, U prior, CartesianNode<T, U> node) {
        if (node == null) return new CartesianNode<>(null, key, prior, null);
        if (node.getKey().equals(key)) return node;
        if (node.getKey().compareTo(key) > 0) {
            if (node.getLeft() == null) {
                CartesianNode<T, U> result = new CartesianNode<>(node, key, prior, Side.LEFT);
                node.setLeft(result);
                return result;
            } else {
                return findForInsert(key, prior, node.getLeft());
            }
        } else {
            if (node.getRight() == null) {
                CartesianNode<T, U> result = new CartesianNode<>(node, key, prior, Side.RIGHT);
                node.setRight(result);
                return result;
            } else {
                return findForInsert(key, prior, node.getRight());
            }
        }
    }

    public CartesianNode<T, U> insertBulk(T key, U prior) {
        SplitResult<T, U> splitResult = split(key);
        CartesianNode<T, U> insertedNode = splitResult.getNode();

        if (insertedNode == null) insertedNode = new CartesianNode<>(null, key, prior, null);

        CartesianNode<T, U> result = merge(splitResult.getLesser(), insertedNode);
        result = merge(result, splitResult.getGreater());
        setRoot(result);

        return insertedNode;
    }

    /**
     * Merge trees. All keys in tree1 =< all keys in tree2
     *
     * @param node1 tree with smaller keys
     * @param node2 tree with greater keys
     * @return merged tree
     */
    private static <T extends Comparable<T>, U extends Comparable<U>> CartesianNode<T, U> merge(
            CartesianNode<T, U> node1, CartesianNode<T, U> node2) {
        if (node1 == null && node2 == null) return null;
        else if (node1 == null) return node2;
        else if (node2 == null) return node1;

        if (node1.getPriority().compareTo(node2.getPriority()) <= 0) {
            node1.setRight(merge(node1.getRight(), node2));
            return node1;
        } else {
            node2.setLeft(merge(node1, node2.getLeft()));
            return node2;
        }
    }

    /**
     * Split tree by key from given node. Key in current node equals to ke param, then in result
     * will be left sub-tree, right sub-tree, node.
     * Else if key in current node lesser then searching key, then call split recursively
     * in right sub-tree.
     * Else search in left sub-tree.
     *
     * @param node node where we split.
     * @param key  searching key
     * @param <T>  key type param in node
     * @param <U>  priority type param in node
     * @return tree where all keys less than searching key, tree where all keys greater than
     * searching key and node with searching key if it exists in tree
     */
    private static <T extends Comparable<T>, U extends Comparable<U>> SplitResult<T, U> split(
            CartesianNode<T, U> node, T key) {
        if (node == null) {
            return new SplitResult<>(null, null, null);
        }
        SplitResult<T, U> splitResult;
        if (key.compareTo(node.getKey()) == 0) {
            CartesianNode<T, U> lesser = node.getLeft();
            if (lesser != null) {
                lesser.setParent(null);
                lesser.setSide(null);
                node.setLeft(null);
            }

            CartesianNode<T, U> greater = node.getRight();
            if (greater != null) {
                greater.setParent(null);
                greater.setSide(null);
                node.setRight(null);
            }

            CartesianNode<T, U> parent = node.getParent();
            if (parent != null) {
                if (node.isLeft()) parent.setLeft(null);
                else parent.setRight(null);
                node.setParent(null);
            }
            node.setSide(null);
            return new SplitResult<>(lesser, greater, node);
        } else if (key.compareTo(node.getKey()) < 0) {
            splitResult = split(node.getLeft(), key);
            CartesianNode<T, U> greater = splitResult.getGreater();

            CartesianNode<T, U> parent = node.getParent();
            if (parent != null) {
                if (node.isLeft()) parent.setLeft(null);
                else parent.setRight(null);
                node.setParent(null);
                node.setSide(null);
            }

            greater = merge(greater, node);
            splitResult.setGreater(greater);
        } else {
            splitResult = split(node.getRight(), key);
            CartesianNode<T, U> lesser = splitResult.getLesser();

            CartesianNode<T, U> parent = node.getParent();
            if (parent != null) {
                if (node.isLeft()) parent.setLeft(null);
                else parent.setRight(null);
                node.setParent(null);
                node.setSide(null);
            }

            lesser = merge(node, lesser);
            splitResult.setLesser(lesser);
        }
        return splitResult;
    }

    /**
     * Split tree by key. If tree contains the key,
     * then result will have two tree and node with that key.
     * <p>
     * First tree will have keys lesser than split key and second
     * tree will have keys greater than split key.
     * Otherwise if tree does not contains key, then node in result will be null.
     *
     * @param key split key
     * @return lesser, greater, node
     */
    private SplitResult<T, U> split(T key) {
        return split(getRoot(), key);
    }

    static class SplitResult<T extends Comparable<T>, U extends Comparable<U>> {
        /**
         * All keys in lesser is smaller than key in node.
         * If all keys in original tree is greater than split key, this field is null.
         */
        private CartesianNode<T, U> lesser;

        /**
         * All keys in greater is bigger than key in node.
         * If all keys in original tree is less than split key, this filed value is null.
         */
        private CartesianNode<T, U> greater;

        /**
         * Node that contains key for split. Not null if tree contains that key, otherwise null.
         */
        private CartesianNode<T, U> node;

        public SplitResult(CartesianNode<T, U> lesserTree, CartesianNode<T, U> greaterTree, CartesianNode<T, U> node) {
            this.lesser = lesserTree;
            this.greater = greaterTree;
            this.node = node;
        }

        public CartesianNode<T, U> getLesser() {
            return lesser;
        }

        public void setLesser(CartesianNode<T, U> lesser) {
            this.lesser = lesser;
        }

        public CartesianNode<T, U> getGreater() {
            return greater;
        }

        public void setGreater(CartesianNode<T, U> greater) {
            this.greater = greater;
        }

        public CartesianNode<T, U> getNode() {
            return node;
        }
    }
}
