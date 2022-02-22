package com.osk.tests;

import com.osk.trees.cartesian.CartesianNode;
import com.osk.trees.cartesian.CartesianTree;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
            System.out.println("Step " + i);
            checkSearchTreeValidity(tree.getRoot());
            checkHeapValidity(tree.getRoot());
        }

        for (int i = 15; i > 0; i--) {
            tree.insert(i, rand.nextInt(100));
        }

        checkSearchTreeValidity(tree.getRoot());
        checkHeapValidity(tree.getRoot());
    }

    @Test
    public void testBulkInsert() {
        Random rand = new Random();
        CartesianTree<Integer, Integer> tree = new CartesianTree<>(0, 0);
        for (int i = 1; i < 15; i++) {
            tree.insertBulk(i, rand.nextInt(100));
        }

        assertEquals("[0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 ]", tree.toString());
        checkSearchTreeValidity(tree.getRoot());
        checkHeapValidity(tree.getRoot());
    }

    @Test
    public void testNumbers() {
        CartesianTree<Integer, Integer> tree = new CartesianTree<>(152, 308);
        tree.insert(21, 165);
        tree.insert(389, 39);

        checkSearchTreeValidity(tree.getRoot());
        checkHeapValidity(tree.getRoot());

        tree = new CartesianTree<>(333, 351);
        tree.insert(443, 66);
        tree.insert(305, 1);
        tree.insert(386, 418);

        checkSearchTreeValidity(tree.getRoot());
        checkHeapValidity(tree.getRoot());
    }

    @Test
    public void randomTreeTest() {
        Random rand = new Random();
        int key = rand.nextInt(512);
        int prior = rand.nextInt(512);
        System.out.println("Init params: " + key + ", " + prior);

        CartesianTree<Integer, Integer> tree = new CartesianTree<>(key, prior);
        for (int i = 0; i < 100; i++) {
            key = rand.nextInt(512);
            prior = rand.nextInt(512);
            System.out.println(i + ": " + key + ", " + prior);
            tree.insert(key, prior);
            checkSearchTreeValidity(tree.getRoot());
            checkHeapValidity(tree.getRoot());
        }
        System.out.println(tree);
    }

    @Test
    public void randomInsertBulkTest() {
        Random rand = new Random();
        int key = rand.nextInt(512);
        int prior = rand.nextInt(512);
        System.out.println("Init params: " + key + ", " + prior);

        CartesianTree<Integer, Integer> tree = new CartesianTree<>(key, prior);
        for (int i = 0; i < 100; i++) {
            key = rand.nextInt(512);
            prior = rand.nextInt(512);
            System.out.println(i + ": " + key + ", " + prior);
            tree.insertBulk(key, prior);
            checkSearchTreeValidity(tree.getRoot());
            checkHeapValidity(tree.getRoot());
        }
        System.out.println(tree);
    }

    @Test
    public void testBulkNumbers() {
        CartesianTree<Integer, Integer> tree = new CartesianTree<>(128, 122);
        tree.insertBulk(120, 401);
        tree.insertBulk(166, 65);
        tree.insertBulk(411, 261);
        tree.insertBulk(19, 183);

        checkSearchTreeValidity(tree.getRoot());
        checkHeapValidity(tree.getRoot());
    }

    private void checkSearchTreeValidity(CartesianNode<Integer, Integer> node) {
        if(node == null) {
            return;
        }

        if (node.getLeft() != null) {
            assertTrue(node.getKey().compareTo(node.getLeft().getKey()) > 0);
            checkSearchTreeValidity(node.getLeft());
        }

        if (node.getRight() != null) {
            assertTrue(node.getKey().compareTo(node.getRight().getKey()) < 0);
            checkSearchTreeValidity(node.getRight());
        }
    }

    private void checkHeapValidity(CartesianNode<Integer, Integer> node) {
        if(node == null) {
            return;
        }

        if (node.getParent() != null) assertTrue(
                node.getPriority().compareTo(node.getParent().getPriority()) >= 0);

        if (node.getLeft() != null) checkHeapValidity(node.getLeft());

        if (node.getRight() != null) checkHeapValidity(node.getRight());
    }
}
