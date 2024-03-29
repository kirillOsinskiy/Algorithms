package com.osk.tests;

import com.osk.trees.redblack.RBNode;
import com.osk.trees.redblack.RBTree;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RBTreeTest {

    @Test
    public void testRandomData() {
        Random r = new Random();
        RBTree<Integer> tree = new RBTree<>(0);
        List<Integer> data = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            int num = r.nextInt(1000);
            data.add(num);
            tree.insert(num);
            testRBTree(tree, data);
        }

        Set<Integer> data1 = new TreeSet<>(data);
        data1.add(0);
        for (Integer num : data) {
            tree.remove(num);
            data1.remove(num);
            assertEquals(data1, tree.getAllElements());
            testRBTree(tree, data1);
        }
    }

    @Test
    public void testSimpleSequence() {
        List<Integer> data = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            data.add(i);
        }
        RBTree<Integer> tree = new RBTree<>(data);
        testRBTree(tree, data);

        Set<Integer> data1 = new TreeSet<>(data);
        data1.add(0);
        for (Integer num : data) {
            tree.remove(num);
            data1.remove(num);
            assertEquals(data1, tree.getAllElements());
            testRBTree(tree, data1);
        }
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
        RBTree<Integer> tree = new RBTree<>(data);
        testRBTree(tree, data);
        testRemoveRBTree(tree, data);
    }

    private void testRemoveRBTree(RBTree<Integer> tree, List<Integer> data) {
        Set<Integer> data1 = new TreeSet<>(data);
        for (Integer num : data) {
            tree.remove(num);
            data1.remove(num);
            assertEquals(data1, tree.getAllElements());
            testRBTree(tree, data1);
        }
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
        RBNode<Integer> childLeft = node.getLeft();
        if (childLeft != null && childLeft.getKey() != null) {
            assertTrue(node.getKey().compareTo(childLeft.getKey()) > 0);
            testSearchTreeValid(childLeft);
        }
        RBNode<Integer> childRight = node.getRight();
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
        if (node.getRight() == null && node.getLeft() == null) {
            children.add(node);
        } else {
            if (node.getLeft() != null) collectChildren(node.getLeft(), children);
            if (node.getRight() != null) collectChildren(node.getRight(), children);
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
            if (node.getLeft() != null) assertTrue(node.getLeft().isBlack());
            if (node.getRight() != null) assertTrue(node.getRight().isBlack());
        }
        if (node.getLeft() != null) testRedNodeHasBlackChildren(node.getLeft());
        if (node.getRight() != null) testRedNodeHasBlackChildren(node.getRight());
    }
}
