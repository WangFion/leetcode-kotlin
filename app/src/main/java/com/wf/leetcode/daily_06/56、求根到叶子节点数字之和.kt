package com.wf.leetcode.daily_06

import com.wf.leetcode.entity.TreeNode
import com.wf.leetcode.entity.arrayToTree
import java.util.*

/**
 * leetcode -> com.wf.leetcode.daily_06 -> `56、求根到叶子节点数字之和`
 *
 * @Author: wf-pc
 * @Date: 2020-10-29 09:17
 * ------------------------
 *
 * 129题
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/sum-root-to-leaf-numbers
 *
 * 给定一个二叉树，它的每个结点都存放一个 0-9 的数字，每条从根到叶子节点的路径都代表一个数字。
 * 例如，从根到叶子节点路径 1->2->3 代表数字 123。
 * 计算从根到叶子节点生成的所有数字之和。
 * 说明: 叶子节点是指没有子节点的节点。
 *
 * 示例 1:
 * 输入: [1,2,3]
 *   1
 *  / \
 * 2   3
 * 输出: 25
 * 解释:
 * 从根到叶子节点路径 1->2 代表数字 12.
 * 从根到叶子节点路径 1->3 代表数字 13.
 * 因此，数字总和 = 12 + 13 = 25.
 *
 * 示例 2:
 * 输入: [4,9,0,5,1]
 *     4
 *    / \
 *   9   0
 *  / \
 * 5   1
 * 输出: 1026
 * 解释:
 * 从根到叶子节点路径 4->9->5 代表数字 495.
 * 从根到叶子节点路径 4->9->1 代表数字 491.
 * 从根到叶子节点路径 4->0 代表数字 40.
 * 因此，数字总和 = 495 + 491 + 40 = 1026.
 */
fun main() {
    val root1 = arrayToTree(arrayOf(1, 2, 3))
    val root2 = arrayToTree(arrayOf(4, 9, 0, 5, 1))

    println(sumNumbers(root1))
    println(sumNumbers(root2))

    println(sumNumbers2(root1))
    println(sumNumbers2(root2))
}

/**
 * plan1：dfs
 *
 * 时间复杂度：O(n)，其中 n 是二叉树的节点个数。对每个节点访问一次。
 * 空间复杂度：O(n)，其中 n 是二叉树的节点个数。空间复杂度主要取决于递归调用的栈空间，
 *           递归栈的深度等于二叉树的高度，最坏情况下，二叉树的高度等于节点个数，空间复杂度为 O(n)。
 */
fun sumNumbers(root: TreeNode?): Int {
    return dfs(root, 0)
}

fun dfs(node: TreeNode?, preSum: Int): Int {
    if (node == null) {
        return 0
    }
    val sum = preSum * 10 + node.value
    return if (node.left == null && node.right == null) {
        sum
    } else {
        dfs(node.left, sum) + dfs(node.right, sum)
    }
}

/**
 * plan2：bfs
 *
 * 时间复杂度：O(n)，其中 n 是二叉树的节点个数。对每个节点访问一次。
 * 空间复杂度：O(n)，其中 n 是二叉树的节点个数。空间复杂度主要取决于队列，每个队列中的元素个数不会超过 n。
 */
fun sumNumbers2(root: TreeNode?): Int {
    if (root == null) {
        return 0
    }
    val nodeQueue = LinkedList<TreeNode>()
    val valueQueue = LinkedList<Int>()
    nodeQueue.offer(root)
    valueQueue.offer(root.value)
    var sum = 0
    while (nodeQueue.isNotEmpty()) {
        val node = nodeQueue.poll()
        val value = valueQueue.poll()
        val left = node?.left
        val right = node?.right
        if (left == null && right == null) {
            sum += value ?: 0
        } else {
            if (left != null) {
                nodeQueue.offer(left)
                valueQueue.offer((value ?: 0) * 10 + left.value)
            }
            if (right != null) {
                nodeQueue.offer(right)
                valueQueue.offer((value ?: 0) * 10 + right.value)
            }
        }
    }
    return dfs(root, 0)
}