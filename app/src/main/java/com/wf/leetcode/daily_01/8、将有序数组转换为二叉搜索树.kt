package com.wf.leetcode.daily_01

import com.wf.leetcode.entity.TreeNode
import java.util.*

/**
 * leetcode -> com.wf.leetcode.daily_01 -> `8、将有序数组转换为二叉搜索树`
 *
 * @Author: wf-pc
 * @Date: 2020-07-03 09:04
 * -------------------------
 * 将一个按照升序排列的有序数组，转换为一棵高度平衡二叉搜索树。
 * 本题中，一个高度平衡二叉树是指一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过 1。
 * 二叉“搜索”树就是要满足一个额外的条件：所有左儿子的数字都比爸爸数字小，所有右儿子的数字都比爸爸数字大。
 * 示例:
 * 给定有序数组: [-10,-3,0,5,9],
 * 一个可能的答案是：[0,-3,9,-10,null,5]，它可以表示下面这个高度平衡二叉搜索树：
 *      0
 *     / \
 *   -3   9
 *   /   /
 * -10  5
 */
fun main() {
    val nums = intArrayOf(-10, -3, 0, 5, 9)
    midOrder(sortedArrayToBST(nums))
    println()
    midOrder(sortedArrayToBST2(nums))
    println()
    midOrder(sortedArrayToBST3(nums))
}

fun midOrder(node: TreeNode?) {
    if (node == null) {
        return
    } else {
        midOrder(node.left)
        print("${node.value}  ")
        midOrder(node.right)
    }
}

/**
 * plan1:中序遍历，总是选择中间位置左边的数字作为根节点
 * 时间复杂度：O(n)，其中 n 是数组的长度。每个数字只访问一次。
 * 空间复杂度：O(logn)，其中 n 是数组的长度。空间复杂度不考虑返回值，
 *           因此空间复杂度主要取决于递归栈的深度，递归栈的深度是 O(logn)。
 *      0
 *     / \
 *   -10  5
 *     \   \
 *    -3    9
 */
fun sortedArrayToBST(nums: IntArray): TreeNode? {
    return buildNode(nums, 0, nums.size - 1)
}

fun buildNode(nums: IntArray, start: Int, end: Int): TreeNode? {
    if (start > end) {
        return null
    }
    val mid = (start + end) / 2
    val root = TreeNode(nums[mid])
    root.left = buildNode(nums, start, mid - 1)
    root.right = buildNode(nums, mid + 1, end)
    return root
}

/**
 * plan2:中序遍历，总是选择中间位置右边的数字作为根节点
 * 时间复杂度：O(n)，其中 n 是数组的长度。每个数字只访问一次。
 * 空间复杂度：O(logn)，其中 n 是数组的长度。空间复杂度不考虑返回值，
 *           因此空间复杂度主要取决于递归栈的深度，递归栈的深度是 O(logn)。
 *      0
 *     / \
 *   -3   9
 *   /   /
 * -10  5
 */
fun sortedArrayToBST2(nums: IntArray): TreeNode? {
    return buildNode2(nums, 0, nums.size - 1)
}

fun buildNode2(nums: IntArray, start: Int, end: Int): TreeNode? {
    if (start > end) {
        return null
    }
    val mid = (start + end + 1) / 2
    val root = TreeNode(nums[mid])
    root.left = buildNode(nums, start, mid - 1)
    root.right = buildNode(nums, mid + 1, end)
    return root
}

/**
 * plan3:中序遍历，选择任意一个中间位置数字作为根节点
 * 则根节点的下标为 mid=(left+right)/2 和 mid=(left+right+1)/2 两者中随机选择一个
 * 时间复杂度：O(n)，其中 n 是数组的长度。每个数字只访问一次。
 * 空间复杂度：O(logn)，其中 n 是数组的长度。空间复杂度不考虑返回值，
 *           因此空间复杂度主要取决于递归栈的深度，递归栈的深度是 O(logn)。
 *      0              0              0              0
 *     / \            / \            / \            / \
 *   -3   9        -10   5        -10   9          -3  5
 *   /   /           \   \          \   /          /   \
 * -10  5            -3   9         -3 5         -10    9
 */
fun sortedArrayToBST3(nums: IntArray): TreeNode? {
    return buildNode3(nums, 0, nums.size - 1)
}

fun buildNode3(nums: IntArray, start: Int, end: Int): TreeNode? {
    if (start > end) {
        return null
    }
    val mid = (start + end + Random().nextInt(2)) / 2
    val root = TreeNode(nums[mid])
    root.left = buildNode(nums, start, mid - 1)
    root.right = buildNode(nums, mid + 1, end)
    return root
}
