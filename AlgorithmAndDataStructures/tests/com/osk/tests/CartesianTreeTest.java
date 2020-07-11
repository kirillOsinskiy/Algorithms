package com.osk.tests;

import com.osk.trees.cartesian.CartesianNode;
import com.osk.trees.cartesian.CartesianTree;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Kirill on 02.07.2020.
 */
public class CartesianTreeTest {

    @Test
    public void testTree() {
        Random rand = new Random();
        CartesianTree<Integer, Integer> tree = new CartesianTree<>(0, 0);
        for (int i = 1; i < 15; i++) {
            tree.insert(i, rand.nextInt(100));
        }

        assertEquals("[0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 ]", tree.toString());
        checkSearchTreeValidity(tree.getRoot());
        checkHeapValidity(tree.getRoot());

        for (int i = 15; i >= 0; i--) {
            tree.remove(i);
            checkSearchTreeValidity(tree.getRoot());
            checkHeapValidity(tree.getRoot());
        }

        for (int i = 15; i > 0; i--) {
            tree.insert(i, rand.nextInt(100));
        }

        checkSearchTreeValidity(tree.getRoot());
        checkHeapValidity(tree.getRoot());
    }

    private void checkSearchTreeValidity(CartesianNode<Integer, Integer> node) {
        if(node.getLeft() != null) {
            assertTrue(node.getKey().compareTo(node.getLeft().getKey()) > 0);
            checkSearchTreeValidity(node.getLeft());
        }

        if(node.getRight() != null) {
            assertTrue(node.getKey().compareTo(node.getRight().getKey()) < 0);
            checkSearchTreeValidity(node.getRight());
        }
    }

    private void checkHeapValidity(CartesianNode<Integer, Integer> node) {
        if(node.getParent() != null) assertTrue(
                node.getPriority().compareTo(node.getParent().getPriority()) >= 0);

        if(node.getLeft() != null) checkHeapValidity(node.getLeft());

        if(node.getRight() != null) checkHeapValidity(node.getRight());
    }
}
