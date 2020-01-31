package com.osk.tests;

import com.osk.trees.redblack.RBNode;
import com.osk.trees.redblack.RBTree;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RBTreeTest {

    @Test
    public void testRandomData() {
        Random r = new Random();
        RBTree<Integer> tree = new RBTree<>(0);
        List<Integer> data = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            int num = r.nextInt(1000);
//            System.out.println("Iter " + i + "; num = " + num);
            data.add(num);
            tree.insert(num);
            testRBTree(tree, data);
        }
    }

    @Test
    public void testSimpleSequence() {
        List<Integer> data = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
//            System.out.println("Iter " + i);
            data.add(i);
        }
        testRBTree(new RBTree<>(data), data);
    }

    @Test
    public void numTestRBTree() {
        numDataTest(Arrays.asList(0, 45));
        numDataTest(Arrays.asList(0, 45, 41));
        numDataTest(Arrays.asList(63, 37, 67, 28, 9, 43, 18, 98));
        numDataTest(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
        numDataTest(Arrays.asList(96, 72, 95, 6, 14, 31, 80, 12, 65));
    }

    private void numDataTest(List<Integer> data) {
        testRBTree(new RBTree<>(data), data);
    }

    private void testRBTree(RBTree<Integer> tree, Collection<Integer> data) {
        // check that all elements are exists in collection
        assertTrue(tree.getAllElements().containsAll(data));

        testSearchTreeValid(tree.getRoot());
        testAllChildrenAreBlack(tree.getRoot());
        testRedNodeHasBlackChildren(tree.getRoot());
        testBlackNodeCountForEachLeaveSame(tree.getRoot());
    }

    private void testSearchTreeValid(RBNode<Integer> node) {
        RBNode<Integer> childLeft = node.getChildLeft();
        if (childLeft != null && childLeft.getKey() != null) {
            assertTrue(node.getKey().compareTo(childLeft.getKey()) > 0);
            testSearchTreeValid(childLeft);
        }
        RBNode<Integer> childRight = node.getChildRight();
        if (childRight != null && childRight.getKey() != null) {
            assertTrue(node.getKey().compareTo(childRight.getKey()) < 0);
            testSearchTreeValid(childRight);
        }
    }

    private void testAllChildrenAreBlack(RBNode<Integer> tree) {
        List<RBNode<Integer>> children = collectChildren(tree, new ArrayList<>());
        for (RBNode<Integer> child : children) {
            assertTrue(child.isBlack());
        }
    }

    private void testBlackNodeCountForEachLeaveSame(RBNode<Integer> tree) {
        //get all children
        List<RBNode<Integer>> children = collectChildren(tree, new ArrayList<>());
        //for each child count black nodes on pass to root
        Map<RBNode<Integer>, Integer> rbNodeMap = new HashMap<>();
        for (RBNode<Integer> node : children) {
            //count black nodes
            rbNodeMap.put(node, getBlackNodesCount(node));
        }
        //compare that all count in map equals
        Integer count = rbNodeMap.get(children.get(0));
        for (Map.Entry<RBNode<Integer>, Integer> entry : rbNodeMap.entrySet()) {
            assertEquals(count, entry.getValue());
        }
    }

    private List<RBNode<Integer>> collectChildren(RBNode<Integer> node, List<RBNode<Integer>> children) {
        if (node.getChildRight() == null && node.getChildLeft() == null) {
            children.add(node);
        } else {
            if (node.getChildLeft() != null) collectChildren(node.getChildLeft(), children);
            if (node.getChildRight() != null) collectChildren(node.getChildRight(), children);
        }
        return children;
    }

    private Integer getBlackNodesCount(RBNode<Integer> node) {
        int result = 0;
        while (node != null) {
            if (node.isBlack()) result++;
            node = node.getParent();
        }
        return result;
    }

    private void testRedNodeHasBlackChildren(RBNode<Integer> node) {
        if (node.isRed()) {
            if (node.getChildLeft() != null) assertTrue(node.getChildLeft().isBlack());
            if (node.getChildRight() != null) assertTrue(node.getChildRight().isBlack());
        }
        if (node.getChildLeft() != null) testRedNodeHasBlackChildren(node.getChildLeft());
        if (node.getChildRight() != null) testRedNodeHasBlackChildren(node.getChildRight());
    }
}
