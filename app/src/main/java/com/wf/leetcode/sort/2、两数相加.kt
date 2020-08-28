package com.wf.leetcode.sort

/**
 * leetcode -> com.wf.leetcode.sort -> `1、两数相加`
 *
 * @Author: wf-pc
 * @Date: 2020-06-23 16:08
 * -------------------------
 * 给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
 * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
 * 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 *
 * 示例：
 * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
 * 输出：7 -> 0 -> 8
 * 原因：342 + 465 = 807
 */

fun main() {
    val header1 = ListNode(2)
    var p = header1
    p.next = ListNode(4)
    p = p.next!!
    p.next = ListNode(3)

    val header2 = ListNode(5)
    var q = header2
    q.next = ListNode(6)
    q = q.next!!
    q.next = ListNode(4)

    var result = addTwoNumbers(header1, header2)
    while (result != null) {
        print(result.value)
        result = result?.next
        if (result != null) {
            print(" -> ")
        }
    }
}

class ListNode(var value: Int) {
    var next: ListNode? = null
}

fun addTwoNumbers(l1: ListNode?, l2: ListNode?): ListNode? {
    val header = ListNode(0)
    var p = l1
    var q = l2
    var curr = header
    var carry = 0
    while (p != null || q != null) {
        carry += p?.value ?: 0
        carry += q?.value ?: 0
        curr.value = carry % 10
        carry /= 10

        p = p?.next
        q = q?.next
        if (p != null || q != null) {
            curr.next = ListNode(0)
            curr = curr.next!!
        }
    }
    if (carry > 0) {
        curr.next = ListNode(carry)
    }
    return header
}







