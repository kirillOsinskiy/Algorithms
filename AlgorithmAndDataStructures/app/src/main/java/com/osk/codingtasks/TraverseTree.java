package com.osk.codingtasks;

import com.osk.trees.BinaryTree;
import com.osk.trees.Node;

/**
 * Created by Kirill on 08.07.2020.
 *
 * Write java function to find some element in binary search tree
 */
public class TraverseTree {

    public static void main(String[] args) {
        BinaryTree<Integer> tree = new BinaryTree<>(5);
        tree.insert(2);
        tree.insert(1);
        tree.insert(3);

        tree.insert(7);
        tree.insert(6);
        tree.insert(8);
        System.out.println(method(tree, 0));
    }

    public static boolean method(BinaryTree<Integer> tree, int k) {
        Node<Integer> node = tree.getRoot();
        while (node != null) {
            if(node.getKey() == k) return true;
            else if(node.getKey() > k) node = node.getLeft();
            else if(node.getKey() < k) node = node.getRight();
        }
        return false;
    }

}
