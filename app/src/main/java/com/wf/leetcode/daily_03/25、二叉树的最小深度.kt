package com.wf.leetcode.daily_03

import com.wf.leetcode.entity.TreeNode
import com.wf.leetcode.entity.treeToArray
import java.util.*
import kotlin.math.min

/**
 * leetcode -> com.wf.leetcode.daily_03 -> `25、二叉树的最小深度`
 *
 * @Author: wf-pc
 * @Date: 2020-08-21 09:07
 * -------------------------
 * 111题：https://leetcode-cn.com/problems/minimum-depth-of-binary-tree/
 * 给定一个二叉树，找出其最小深度。
 * 最小深度是从根节点到最近叶子节点的最短路径上的节点数量。
 * 说明: 叶子节点是指没有子节点的节点。
 *
 * 示例:
 * 给定二叉树 [3,9,20,null,null,15,7],
 *   3
 *  / \
 * 9  20
 *   /  \
 *  15   7
 * 返回它的最小深度  2.
 */
fun main() {
    val arr = arrayOf(3, 9, 20, null, null, 15, 7)
    val root = com.wf.leetcode.entity.arrayToTree(arr)
    println(treeToArray(root).contentToString())
    println(minDepth(root))
    println(minDepth2(root))
}

/**
 * plan1：深度优先搜索
 * 叶子节点的定义是左孩子和右孩子都为 null 时叫做叶子节点
 * 递归结束条件:
 *     当 root 节点左右孩子都为空时，返回 1
 *     当 root 节点左右孩子有一个为空时，返回不为空的孩子节点的深度
 *     当 root 节点左右孩子都不为空时，返回左右孩子较小深度的节点值
 *
 * 时间复杂度：O(N)，其中 N 是树的节点数。对每个节点访问一次。
 * 空间复杂度：O(H)，其中 H 是树的高度。空间复杂度主要取决于递归时栈空间的开销。
 *           最坏情况下，树呈现链状，空间复杂度为 O(N)。
 *           平均情况下树的高度与节点数的对数正相关，空间复杂度为 O(logN)。
 */
fun minDepth(root: TreeNode?): Int {
    if (root == null) {
        return 0
    }
    if (root.left == null && root.right == null) {
        return 1
    }
    val lDepth = minDepth(root.left)
    val rDepth = minDepth(root.right)
    //其中一个节点为空，说明有一个必然为0，所以可以返回lDepth + rDepth + 1
    if (root.left == null || root.right == null) {
        return lDepth + rDepth + 1
    }
    return min(lDepth, rDepth) + 1
}

/**
 * plan2：广度优先搜索
 * 时间复杂度：O(N)，其中 N 是树的节点数。对每个节点访问一次。
 * 空间复杂度：O(N)，其中 N 是树的节点数。空间复杂度主要取决于队列的开销，队列中的元素个数不会超过树的节点数。
 */
fun minDepth2(root: TreeNode?): Int {
    if (root == null) {
        return 0
    }
    val queue = LinkedList<TreeNode>()
    queue.offer(root)
    var depth = 0
    while (!queue.isEmpty()) {
        depth++
        var size = queue.size
        while (size > 0) {
            val node = queue.poll()
            if (node != null) {
                if (node.left == null && node.right == null) {
                    return depth
                }
                queue.offer(node.left)
                queue.offer(node.right)
            }
            size--
        }
    }
    return depth
}