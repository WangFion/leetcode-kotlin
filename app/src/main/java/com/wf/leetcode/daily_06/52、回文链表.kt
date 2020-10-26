package com.wf.leetcode.daily_06

import com.wf.leetcode.entity.ListNode
import com.wf.leetcode.entity.arrayToLinkedList

/**
 * leetcode -> com.wf.leetcode.daily_06 -> `52、回文链表`
 *
 * @Author: wf-pc
 * @Date: 2020-10-23 16:42
 * -------------------------
 *
 * 234题
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/palindrome-linked-list
 *
 * 请判断一个链表是否为回文链表。
 * 示例 1:
 * 输入: 1->2
 * 输出: false
 *
 * 示例 2:
 * 输入: 1->2->2->1
 * 输出: true
 *
 * 进阶：
 * 你能否用 O(n) 时间复杂度和 O(1) 空间复杂度解决此题？
 */
fun main() {
    val head1 = arrayToLinkedList(intArrayOf(1, 2))
    println(isPalindrome(head1))
    println(isPalindrome2(head1))
    println(isPalindrome3(head1))

    val head2 = arrayToLinkedList(intArrayOf(1, 2, 2, 1))
    println(isPalindrome(head2))
    println(isPalindrome2(head2))
    println(isPalindrome3(head2))
}

/**
 * plan1：数组 + 双指针
 *
 * 时间复杂度：O(n)，其中 n 指的是链表的元素个数。
 *           第一步： 遍历链表并将值复制到数组中，O(n)。
 *           第二步：双指针判断是否为回文，执行了 O(n/2) 次的判断，即 O(n)。
 *           总的时间复杂度：O(2n)=O(n)。
 * 空间复杂度：O(n)，其中 n 指的是链表的元素个数，我们使用了一个数组列表存放链表的元素值。
 */
fun isPalindrome(head: ListNode?): Boolean {
    val list = mutableListOf<Int>()
    var node = head
    while (node != null) {
        list.add(node.value)
        node = node.next
    }
    var left = 0
    var right = list.size - 1
    while (left < right) {
        if (list[left] != list[right]) {
            return false
        }
        left++
        right--
    }
    return true
}

/**
 * plan2：递归
 *
 * node 指针是先到尾节点，由于递归的特性再从后往前进行比较。outer 是递归函数外的指针。
 * 若 outer?.value != node.value 则返回 false。反之，outer 向前移动并返回 true。
 * 算法的正确性在于递归处理节点的顺序是相反的，而我们在函数外又记录了一个变量，因此从本质上，
 * 我们同时在正向和逆向迭代匹配。
 *
 * 时间复杂度：O(n)，其中 n 指的是链表的大小。
 * 空间复杂度：O(n)，其中 n 指的是链表的大小。
 */
var outer: ListNode? = null
fun isPalindrome2(head: ListNode?): Boolean {
    outer = head
    return recursionNode(head)
}

fun recursionNode(node: ListNode?): Boolean {
    if (node != null) {
        if (!recursionNode(node.next)) {
            return false
        }
        if (outer?.value != node.value) {
            return false
        }
        outer = outer?.next
    }
    return true
}

/**
 * plan3：快慢指针
 *
 * 整个流程可以分为以下五个步骤：
 * （1）找到前半部分链表的尾节点。
 * （2）反转后半部分链表。
 * （3）判断是否回文。
 * （4）恢复链表。
 * （5）返回结果。
 *
 * 时间复杂度：O(n)，其中 n 指的是链表的大小。
 * 空间复杂度：O(1)。我们只会修改原本链表中节点的指向，而在堆栈上的堆栈帧不超过 O(1)。
 */
fun isPalindrome3(head: ListNode?): Boolean {
    if (head == null) {
        return true
    }

    // 找到前半部分链表的尾节点并反转后半部分链表
    val firstHalfEnd = endOfFirstHalf(head)
    val secondHalfStart = reverseList(firstHalfEnd?.next)

    // 判断是否回文
    var p1 = head
    var p2 = secondHalfStart
    var result = true
    while (result && p2 != null) {
        if (p1?.value != p2.value) {
            result = false
        }
        p1 = p1?.next
        p2 = p2.next
    }

    // 还原链表并返回结果
    firstHalfEnd?.next = reverseList(secondHalfStart)
    return result
}

fun reverseList(head: ListNode?): ListNode? {
    var prev: ListNode? = null
    var curr = head;
    while (curr != null) {
        val nextTemp = curr.next;
        curr.next = prev
        prev = curr
        curr = nextTemp
    }
    return prev
}

fun endOfFirstHalf(head: ListNode?): ListNode? {
    var fast = head
    var slow = head
    while (fast?.next != null && fast.next?.next != null) {
        fast = fast.next!!.next!!
        slow = slow?.next!!
    }
    return slow
}