package com.wf.leetcode.daily_05

import com.wf.leetcode.entity.TreeNode
import com.wf.leetcode.entity.arrayToTree
import com.wf.leetcode.entity.treeToArray
import java.util.*

/**
 * leetcode -> com.wf.leetcode.daily_05 -> `45、合并二叉树`
 *
 * @Author: wf-pc
 * @Date: 2020-09-23 09:21
 * -------------------------
 * 617题
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/merge-two-binary-trees
 *
 * 给定两个二叉树，想象当你将它们中的一个覆盖到另一个上时，两个二叉树的一些节点便会重叠。
 * 你需要将他们合并为一个新的二叉树。合并的规则是如果两个节点重叠，那么将他们的值相加作为节点合并后的新值，
 * 否则不为 NULL 的节点将直接作为新二叉树的节点。

 * 示例 1:

 * 输入:
 * Tree 1                     Tree 2
 *     1                         2
 *    / \                       / \
 *   3   2                     1   3
 *  /                           \   \
 * 5                             4   7
 * 输出:
 * 合并后的树:
 *     3
 *    / \
 *   4   5
 *  / \   \
 * 5   4   7
 * 注意: 合并必须从两个树的根节点开始。
 */
fun main() {
    val t1 = arrayToTree(arrayOf(1, 3, 2, 5))
    val t2 = arrayToTree(arrayOf(2, 1, 3, null, 4, null, 7))
    println(treeToArray(t1).contentToString())
    println(treeToArray(t2).contentToString())

    println(treeToArray(mergeTrees(t1, t2)).contentToString())
    println(treeToArray(mergeTrees2(t1, t2)).contentToString())
}

/**
 * plan1：dfs
 *
 * 时间复杂度：O(min(m,n))，其中 m 和 n 分别是两个二叉树的节点个数。对两个二叉树同时进行深度优先搜索，
 *           只有当两个二叉树中的对应节点都不为空时才会对该节点进行显性合并操作，因此被访问到的节点数不会
 *           超过较小的二叉树的节点数。
 * 空间复杂度：O(min(m,n))，其中 m 和 n 分别是两个二叉树的节点个数。空间复杂度取决于递归调用的层数，
 *            递归调用的层数不会超过较小的二叉树的最大高度，最坏情况下，二叉树的高度等于节点数。
 */
fun mergeTrees(t1: TreeNode?, t2: TreeNode?): TreeNode? {
    if (t1 == null) {
        return t2
    }
    if (t2 == null) {
        return t1
    }
    val root = TreeNode(t1.value + t2.value)
    root.left = mergeTrees(t1.left, t2.left)
    root.right = mergeTrees(t1.right, t2.right)
    return root
}

/**
 * plan2：bfs
 *
 * 时间复杂度：O(min(m,n))，其中 m 和 n 分别是两个二叉树的节点个数。对两个二叉树同时进行广度优先搜索，
 *            只有当两个二叉树中的对应节点都不为空时才会访问到该节点，因此被访问到的节点数不会超过较小的二叉树的节点数。
 * 空间复杂度：O(min(m,n))，其中 m 和 n 分别是两个二叉树的节点个数。空间复杂度取决于队列中的元素个数，
 *            队列中的元素个数不会超过较小的二叉树的节点数。
 */
fun mergeTrees2(t1: TreeNode?, t2: TreeNode?): TreeNode? {
    if (t1 == null) {
        return t2
    }
    if (t2 == null) {
        return t1
    }
    val root = TreeNode(t1.value + t2.value)
    val queue = LinkedList<TreeNode>()
    val queue1 = LinkedList<TreeNode>()
    val queue2 = LinkedList<TreeNode>()
    queue.offer(root)
    queue1.offer(t1)
    queue2.offer(t2)
    while (queue1.isNotEmpty() && queue2.isNotEmpty()) {
        val node = queue.poll()
        val node1 = queue1.poll()
        val node2 = queue2.poll()
        val l1 = node1?.left
        val l2 = node2?.left
        val r1 = node1?.right
        val r2 = node2?.right
        if (l1 != null && l2 != null) {
            val left = TreeNode(l1.value + l2.value)
            node?.left = left
            queue.offer(left)
            queue1.offer(l1)
            queue2.offer(l2)
        } else if (l1 != null && l2 == null) {
            node?.left = l1
        } else if (l1 == null && l2 != null) {
            node?.left = l2
        }
        if (r1 != null && r2 != null) {
            val right = TreeNode(r1.value + r2.value)
            node?.right = right
            queue.offer(right)
            queue1.offer(r1)
            queue2.offer(r2)
        } else if (r1 != null && r2 == null) {
            node?.right = r1
        } else if (r1 == null && r2 != null) {
            node?.right = r2
        }
    }
    return root
}