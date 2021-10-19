package jfp.study.calculate.leetcode


object Linked {

    class ListNode(var value: Int) {
        var next: ListNode? = null
    }

    fun reverseLinkedList(list: ListNode): ListNode {
        var preNode: ListNode? = null
        var currNode: ListNode? = list
        while (currNode != null) {
            val next = currNode.next
            currNode.next = preNode
            preNode = currNode
            currNode = next
        }
        return preNode!!
    }

    fun reverseHead(list: ListNode?): ListNode? {
        if (list?.next == null) {
            return list
        }
        val head = reverseHead(list.next)
        list.next!!.next = list
        list.next = null
        return head
    }

    class ComplexNode(
        val value: Int
    ) {
        var next: ComplexNode? = null
        var random: ComplexNode? = null
    }

    fun copyRandomList(head: ComplexNode?): ComplexNode? {
        val map: MutableMap<ComplexNode, ComplexNode> = HashMap()
        var indexNode: ComplexNode? = head
        // 拷贝复制一份到map中
        while (indexNode != null) {
            map[indexNode] = ComplexNode(indexNode.value)
            indexNode = indexNode.next
        }
        indexNode = head
        // 建立映射关系
        while (indexNode != null) {
            val node: ComplexNode? = map[indexNode]
            node?.next = map[indexNode.next]
            node?.random = map[indexNode.random]
            indexNode = indexNode.next
        }
        return map[head]
    }

    /*
     * 合并两个排序的链表
     * https://leetcode-cn.com/problems/he-bing-liang-ge-pai-xu-de-lian-biao-lcof/solution/mian-shi-ti-25-he-bing-liang-ge-pai-xu-de-lian-b-2/
     */
    fun mergeTwoLists(l1: ListNode?, l2: ListNode?): ListNode? {
        var listOne = l1
        var listTwo = l2
        val dum = ListNode(0)
        var cur: ListNode = dum
        while (listOne != null && listTwo != null) {
            if (listOne.value < listTwo.value) {
                cur.next = listOne
                listOne = listOne.next
            } else {
                cur.next = listTwo
                listTwo = listTwo.next
            }
            cur = cur.next!!
        }
        cur.next = listOne ?: listTwo
        return dum.next
    }
}