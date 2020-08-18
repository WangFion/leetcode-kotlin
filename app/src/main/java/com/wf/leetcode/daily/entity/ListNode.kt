package com.wf.leetcode.daily.entity

/**
 * leetcode -> com.wf.leetcode.daily.entity -> ListNode
 *
 * @Author: wf-pc
 * @Date: 2020-08-18 09:47
 */
class ListNode(var value: Int) {
    var next: ListNode? = null
}

fun findMidPtr(head: ListNode?): ListNode? {
    var slow = head
    var fast = head
    while (fast?.next != null) {
        slow = slow?.next
        fast = fast.next
        fast = fast?.next
    }
    return slow
}

fun arrayToLinkedList(array: IntArray): ListNode? {
    if (array.isEmpty()) {
        return null
    }
    val head = ListNode(array[0])
    var node = head
    for (i in 1 until array.size) {
        node.next = ListNode(array[i])
        node = node.next!!
    }
    return head
}

fun linkedListToArray(head: ListNode?): IntArray {
    val list = mutableListOf<Int>()
    var node = head
    while (node != null) {
        list.add(node.value)
        node = node.next
    }
    return list.toIntArray()
}

fun printLinkedList(head: ListNode?): String {
    val result = linkedListToArray(head).contentToString()
    println(result)
    return result
}

fun main() {
    val arr = intArrayOf(-10, -3, 0, 5, 9)
    val head = arrayToLinkedList(arr)
    printLinkedList(head)
    println(linkedListToArray(head).contentToString())

    println(findMidPtr(head)?.value)
}