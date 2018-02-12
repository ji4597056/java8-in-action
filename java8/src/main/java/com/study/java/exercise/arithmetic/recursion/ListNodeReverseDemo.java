package com.study.java.exercise.arithmetic.recursion;

import javax.print.attribute.standard.NumberUpSupported;
import org.junit.Test;

/**
 * 实现链表逆序
 *
 * @author Jeffrey
 * @since 2018/02/08 13:53
 */
public class ListNodeReverseDemo {

    public ListNode reverse(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode next = head.next;
        ListNode newHead = reverse(next);
        next.next = head;
        head.next = null;
        return newHead;
    }

    @Test
    public void test() {
        ListNode n1 = new ListNode(1);
        ListNode n2 = new ListNode(2);
        ListNode n3 = new ListNode(3);
        ListNode n4 = new ListNode(4);
        ListNode n5 = new ListNode(5);
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n5;
        print(n1);
        System.out.println();
        print(reverse(n1));
    }

    public static void print(ListNode head) {
        ListNode tmp = head;
        while (tmp != null) {
            System.out.print(tmp.val + "->");
            tmp = tmp.next;
        }
        System.out.println("null");
    }

    public static class ListNode {

        public int val;

        public ListNode next;

        public ListNode(int x) {
            val = x;
        }
    }
}
