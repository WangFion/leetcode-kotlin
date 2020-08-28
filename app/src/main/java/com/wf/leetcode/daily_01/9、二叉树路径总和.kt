package com.wf.leetcode.daily_01

import com.wf.leetcode.entity.TreeNode
import java.util.*

/**
 * leetcode -> com.wf.leetcode.daily_01 -> `9、二叉树路径总和`
 *
 * @Author: wf-pc
 * @Date: 2020-07-07 14:54
 * -------------------------
 * 112题：https://leetcode-cn.com/problems/path-sum/
 * 给定一个二叉树和一个目标和，判断该树中是否存在根节点到叶子节点的路径，这条路径上所有节点值相加等于目标和。
 * 说明: 叶子节点是指没有子节点的节点。
 *
 * 示例:
 * 给定如下二叉树，以及目标和 sum = 22，
 *       5
 *      / \
 *     4   8
 *    /   / \
 *   11  13  4
 *  /  \      \
 * 7    2      1
 * 返回 true, 因为存在目标和为 22 的根节点到叶子节点的路径 5->4->11->2。
 */
fun main() {
    val values = arrayOf(5, 4, 8, 11, 13, 4, 7, 2, 1)
    val nodes = mutableListOf<TreeNode>()
    for (value in values) {
        nodes.add(TreeNode(value))
    }
    nodes[0].left = nodes[1]
    nodes[0].right = nodes[2]

    nodes[1].left = nodes[3]

    nodes[2].left = nodes[4]
    nodes[2].right = nodes[5]

    nodes[3].left = nodes[6]
    nodes[3].right = nodes[7]

    nodes[5].right = nodes[8]

    println(hasPathSum(nodes[0], 22))
    println(hasPathSum2(nodes[0], 22))
}

/**
 * paln1：递归算法
 * 时间复杂度：O(N)，其中 N 是树的节点数。对每个节点访问一次。
 * 空间复杂度：O(H)，其中 H 是树的高度。空间复杂度主要取决于递归时栈空间的开销，最坏情况下，树呈现链状，
 *            空间复杂度为 O(N)。平均情况下树的高度与节点数的对数正相关，O(logN)。
 */
fun hasPathSum(root: TreeNode?, sum: Int): Boolean {
    if (root == null) {
        return false
    }
    // 如果当前节点是叶子节点，则直接判断差值是否为0
    if (root.left == null && root.right == null) {
        return sum == root.value
    }
    // 如果当前节点不是叶子节点，则从当前节点开始递归搜索满足要求的子节点的路径
    return hasPathSum(root.left, sum - root.value)
            || hasPathSum(root.right, sum - root.value)
}

/**
 * plan2：广度优先搜索
 * 时间复杂度：O(N)，其中 N 是树的节点数。对每个节点访问一次。
 * 空间复杂度：O(N)，其中 N 是树的节点数。空间复杂度主要取决于队列的开销，队列中的元素个数不会超过树的节点数。
 */
fun hasPathSum2(root: TreeNode?, sum: Int): Boolean {
    if (root == null) {
        return false
    }
    val nodeQueue = LinkedList<TreeNode>()
    val valueQueue = LinkedList<Int>()
    nodeQueue.push(root)
    valueQueue.push(root.value)
    while (!nodeQueue.isEmpty()) {
        val node = nodeQueue.pop()
        val value = valueQueue.pop()
        if (node.left == null && node.right == null) {
            if (value == sum) {
                return true
            }
            continue
        }
        if (node.left != null) {
            nodeQueue.offer(node.left)
            valueQueue.offer(node.left?.value?.plus(value))
        }
        if (node.right != null) {
            nodeQueue.push(node.right)
            valueQueue.push(node.right?.value?.plus(value))
        }
    }
    return false
}