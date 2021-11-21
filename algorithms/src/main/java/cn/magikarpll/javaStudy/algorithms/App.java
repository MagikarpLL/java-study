package cn.magikarpll.javaStudy.algorithms;

import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        ListNode first = new ListNode(1);
        ListNode second = new ListNode(2);
        ListNode third = new ListNode(3);
        first.next = second;
        second.next = third;
        third.next = null;
        printListNode(first);

        Solution solution = new Solution();
        ListNode root = solution.reverseList(first);
        printListNode(root);

    }

    public static void printListNode(ListNode node){
        while (null != node){
            System.out.print(node.val);
            node = node.next;
        }
        System.out.println("");
    }

}

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */

class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}

class Solution {

    public ListNode reverseList(ListNode head) {
        List<ListNode> results = new ArrayList<ListNode>();
        ListNode result = findHead(head, results);
        result.next = null;
        return results.get(0);
    }

    public ListNode findHead(ListNode head, List<ListNode> start){
        if(head.next == null){
            start.add(head);
            return head;
        }
        ListNode pre = findHead(head.next, start);
        head.next = null;
        pre.next = head;
        return head;
    }

}
