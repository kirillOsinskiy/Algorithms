package com.osk.tests;

import com.osk.trees.BinaryTree;
import com.osk.trees.Node;
import com.osk.trees.SplayTree;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class BinaryTreeTest {

    private BinaryTree<Integer> tree;

    @Before
    public void setUp() {
        tree = new BinaryTree<>(4);
        tree.insert(2);
        tree.insert(6);
        tree.insert(1);
        tree.insert(3);
        tree.insert(5);
        tree.insert(7);
    }

    @Test
    public void testBinaryTree() {
        assertEquals("[1 2 3 4 5 6 7 ]", tree.toString());
    }

    @Test
    public void testSplayTree() {
        tree = new SplayTree<>(4);

        Node<Integer> two = tree.insert(2);
        Node<Integer> six = tree.insert(6);
        Node<Integer> one = tree.insert(1);
        Node<Integer> three = tree.insert(3);
        Node<Integer> five = tree.insert(5);
        Node<Integer> seven = tree.insert(7);
        assertEquals("[1 2 3 4 5 6 7 ]", tree.toString());

        assertEquals(one, tree.find(1));
        assertEquals(two, tree.find(2));
        assertEquals(three, tree.find(3));
        assertEquals(five, tree.find(5));
        assertEquals(six, tree.find(6));
        assertEquals(seven, tree.find(7));

        tree.remove(4);
        assertEquals("[1 2 3 5 6 7 ]", tree.toString());
        tree.remove(2);
        assertEquals("[1 3 5 6 7 ]", tree.toString());
    }

    @Test
    public void testRemove() {
        tree.remove(4);
        assertEquals("[1 2 3 5 6 7 ]", tree.toString());
        tree.remove(2);
        assertEquals("[1 3 5 6 7 ]", tree.toString());

    }

    @Test
    public void testSuccessorPredecessor() {
        assertNull(tree.predecessor(1));
        assertEquals(new Integer(2), tree.successor(1));

        assertEquals(new Integer(1), tree.predecessor(2));
        assertEquals(new Integer(3), tree.successor(2));

        assertEquals(new Integer(2), tree.predecessor(3));
        assertEquals(new Integer(4), tree.successor(3));

        assertEquals(new Integer(3), tree.predecessor(4));
        assertEquals(new Integer(5), tree.successor(4));

        assertEquals(new Integer(4), tree.predecessor(5));
        assertEquals(new Integer(6), tree.successor(5));

        assertEquals(new Integer(5), tree.predecessor(6));
        assertEquals(new Integer(7), tree.successor(6));

        assertEquals(new Integer(6), tree.predecessor(7));
        assertNull(tree.successor(7));
    }

}
