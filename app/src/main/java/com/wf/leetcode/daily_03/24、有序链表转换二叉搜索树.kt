package com.wf.leetcode.daily_03

import com.wf.leetcode.entity.*

/**
 * leetcode -> com.wf.leetcode.daily_03 -> `24、有序链表转换二叉搜索树`
 *
 * @Author: wf-pc
 * @Date: 2020-08-18 09:44
 * -------------------------
 * 109题：https://leetcode-cn.com/problems/convert-sorted-list-to-binary-search-tree/
 * 给定一个单链表，其中的元素按升序排序，将其转换为高度平衡的二叉搜索树。
 * 本题中，一个高度平衡二叉树是指一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过 1。
 * 二叉“搜索”树就是要满足一个额外的条件：所有左儿子的数字都比爸爸数字小，所有右儿子的数字都比爸爸数字大。
 *
 * 示例:
 * 给定的有序链表： [-10, -3, 0, 5, 9],
 * 一个可能的答案是：[0, -3, 9, -10, null, 5], 它可以表示下面这个高度平衡二叉搜索树：
 *      0
 *     / \
 *   -3   9
 *   /   /
 * -10  5
 */
fun main() {
    val arr = intArrayOf(-10, -3, 0, 5, 9)
    val head = arrayToLinkedList(arr)

    val tree = sortedListToBST(head)
    println(treeToArray(tree).contentToString())

    val tree2 = sortedListToBST2(head)
    println(treeToArray(tree2).contentToString())

    val tree3 = sortedListToBST3(head)
    println(treeToArray(tree3).contentToString())
}

/**
 * plan1：先转数组，再递归构建成树
 *
 * 时间复杂度：O(N)，递归遍历了每个节点。
 * 空间复杂度：O(N)。
 */
fun sortedListToBST(head: ListNode?): TreeNode? {
    val arr = linkedListToArray(head)
    return arrayToTree(arr, 0, arr.size - 1)
}

fun arrayToTree(array: IntArray, left: Int, right: Int): TreeNode? {
    if (left > right) {
        return null
    }
    //val mid = (left + right) / 2
    val mid = (left + right + 1) / 2
    val root = TreeNode(array[mid])
    root.left = arrayToTree(array, left, mid - 1)
    root.right = arrayToTree(array, mid + 1, right)
    return root
}

/**
 * plan2：分治 + 快慢指针
 * 用快慢指针找出中位数，然后再分别构建左右子树
 *
 * 时间复杂度：O(nlogn)，其中 n 是链表的长度。
 *           设长度为 n 的链表构造二叉搜索树的时间为 T(n)，递推式为 T(n)=2⋅T(n/2)+O(n)，
 *           根据主定理，T(n)=O(nlogn)。
 * 空间复杂度：O(logn)，这里只计算除了返回答案之外的空间。平衡二叉树的高度为 O(logn)，
 *           即为递归过程中栈的最大深度，也就是需要的空间。
 */
fun sortedListToBST2(head: ListNode?): TreeNode? {
    return buildTree(head, null)
}

fun buildTree(head: ListNode?, footer: ListNode?): TreeNode? {
    if (head == footer) {
        return null
    }
    val mid = findMidNode(head, footer)
    val root = mid?.value?.let { TreeNode(it) }
    root?.left = buildTree(head, mid)
    root?.right = buildTree(mid?.next, footer)
    return root
}

fun findMidNode(start: ListNode?, end: ListNode?): ListNode? {
    var slow = start
    var fast = start
    while (fast != end && fast?.next != end) {
        slow = slow?.next
        fast = fast?.next
        fast = fast?.next
    }
    return slow
}

/**
 * plan3：分治 + 中序遍历
 * 具体地，设当前链表的左端点编号为 left，右端点编号为 right，包含关系为「双闭」，
 * 即 left 和 right 均包含在链表中。链表节点的编号为 [0,n)。中序遍历的顺序是
 * 「左子树 - 根节点 - 右子树」，那么在分治的过程中，我们不用急着找出链表的中位数节点，
 * 而是使用一个占位节点，等到中序遍历到该节点时，再填充它的值。
 *
 * 时间复杂度：O(n)，其中 n 是链表的长度。
 *           设长度为 n 的链表构造二叉搜索树的时间为 T(n)，递推式为 T(n)=2⋅T(n/2)+O(1)，根据主定理，T(n)=O(n)。
 * 空间复杂度：O(logn)，这里只计算除了返回答案之外的空间。平衡二叉树的高度为 O(logn)，即为递归过程中栈的最大深度，也就是需要的空间。
 */
var mHead: ListNode? = null
fun sortedListToBST3(head: ListNode?): TreeNode? {
    mHead = head
    return midBuildTree(
        0,
        getLinkedLength(head) - 1
    )
}

fun getLinkedLength(head: ListNode?): Int {
    var length = 0
    var node = head
    while (node != null) {
        node = node.next
        length++
    }
    return length
}

fun midBuildTree(left: Int, right: Int): TreeNode? {
    if (left > right) {
        return null
    }
    //val mid = (left + right) / 2
    val mid = (left + right + 1) / 2
    val root = TreeNode(0)
    root.left = midBuildTree(left, mid - 1)
    root.value = mHead?.value ?: 0
    mHead = mHead?.next
    root.right = midBuildTree(mid + 1, right)
    return root
}
