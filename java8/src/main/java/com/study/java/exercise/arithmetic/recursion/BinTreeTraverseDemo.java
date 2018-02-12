package com.study.java.exercise.arithmetic.recursion;

import org.junit.Test;

/**
 * 二叉树遍历
 *
 * @author Jeffrey
 * @since 2018/02/08 15:12
 */
public class BinTreeTraverseDemo {

    // 前序遍历
    public static void preOrderTraverse(Node node) {
        if (node == null) {
            return;
        }
        System.out.print(node.data + " ");
        preOrderTraverse(node.leftChild);
        preOrderTraverse(node.rightChild);
    }

    // 中序遍历
    public static void inOrderTraverse(Node node) {
        if (node == null) {
            return;
        }
        inOrderTraverse(node.leftChild);
        System.out.print(node.data + " ");
        inOrderTraverse(node.rightChild);
    }

    // 后序遍历
    public static void postOrderTraverse(Node node) {
        if (node == null) {
            return;
        }
        postOrderTraverse(node.leftChild);
        postOrderTraverse(node.rightChild);
        System.out.print(node.data + " ");
    }

    private static class Node {
        Node leftChild;
        Node rightChild;
        int data;

        Node(int newData) {
            leftChild = null;
            rightChild = null;
            data = newData;
        }
    }

    @Test
    public void test(){
        Node n1 = new Node(0);
        Node n2 = new Node(1);
        Node n3 = new Node(2);
        Node n4 = new Node(3);
        Node n5 = new Node(4);
        Node n6 = new Node(5);
        Node n7 = new Node(6);
        n1.leftChild = n2;
        n1.rightChild = n3;
        n2.leftChild = n4;
        n2.rightChild = n5;
        n3.leftChild = n6;
        n3.rightChild = n7;
        preOrderTraverse(n1);
        System.out.println("=========");
        inOrderTraverse(n1);
        System.out.println("=========");
        postOrderTraverse(n1);
    }
}
