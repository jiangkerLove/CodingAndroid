package jfp.study.calculate

import jfp.study.calculate.leetcode.Linked
import org.junit.Test

class LeetcodeTest {

    @Test
    fun reverse_list() {
        val list = Linked.ListNode(1).apply {
            next = Linked.ListNode(2).apply {
                next = Linked.ListNode(3).apply {
                    next = Linked.ListNode(4).apply {
                        next = Linked.ListNode(5)
                    }
                }
            }
        }
        print(list)
        val result = Linked.reverseHead(list)
        print(result)
    }

    private fun print(list: Linked.ListNode?) {
        var curr: Linked.ListNode? = list
        while (curr != null) {
            print("${curr.value}${if (curr.next != null) " -> " else "\n"}")
            curr = curr.next
        }
    }
}