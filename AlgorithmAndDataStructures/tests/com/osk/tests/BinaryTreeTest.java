package com.osk.tests;

import com.osk.trees.BinaryTree;
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
