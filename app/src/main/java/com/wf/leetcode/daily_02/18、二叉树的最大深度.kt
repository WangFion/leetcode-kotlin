package com.wf.leetcode.daily_02

import com.wf.leetcode.entity.TreeNode
import com.wf.leetcode.entity.arrayToTree
import com.wf.leetcode.entity.treeToArray
import java.util.*
import kotlin.math.max

/**
 * leetcode -> com.wf.leetcode.daily_02 -> `18、二叉树的最大深度`
 *
 * @Author: wf-pc
 * @Date: 2020-07-28 09:10
 * -------------------------
 * 104题：https://leetcode-cn.com/problems/maximum-depth-of-binary-tree/
 * 给定一个二叉树，找出其最大深度。
 * 二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。
 * 说明: 叶子节点是指没有子节点的节点。
 *
 * 示例：
 * 给定二叉树 [3, 9, 20, null, null, 15, 7]
 *   3
 *  / \
 * 9  20
 *   /  \
 *  15   7
 * 返回它的最大深度 3 。
 */
fun main() {
    val arr = arrayOf(3, 9, 20, null, null, 15, 7)
    val root = arrayToTree(arr)
    println(maxDepth(root))
    println(maxDepth2(root))
    println(treeToArray(root).contentToString())

    val arr2 = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
    val root2 = arrayToTree(arr2)
    println(maxDepth(root2))
    println(maxDepth2(root2))
    println(treeToArray(root2).contentToString())
}



/**
 * plan1：递归
 * 如果我们知道了左子树和右子树的最大深度 l 和 r，那么该二叉树的最大深度即为 max(l,r)+1
 *
 * 时间复杂度：O(n)，其中 n 为二叉树节点的个数。每个节点在递归中只被遍历一次。
 * 空间复杂度：O(height)，其中 height 表示二叉树的高度。递归函数需要栈空间，而栈空间取决于递归的深度，因此空间复杂度等价于二叉树的高度。
 */
fun maxDepth(root: TreeNode?): Int {
    return if (root == null) {
        0
    } else {
        val leftDepth = maxDepth(root.left)
        val rightDepth = maxDepth(root.right)
        max(leftDepth, rightDepth) + 1
    }
}

/**
 * plan2：广度优先搜索
 *
 * 我们也可以用「广度优先搜索」的方法来解决这道题目，但我们需要对其进行一些修改，
 * 此时我们广度优先搜索的队列里存放的是「当前层的所有节点」。
 * 每次拓展下一层的时候，不同于广度优先搜索的每次只从队列里拿出一个节点，
 * 我们需要将队列里的所有节点都拿出来进行拓展，这样能保证每次拓展完的时候队列里存放的是当前层的所有节点，
 * 即我们是一层一层地进行拓展，最后我们用一个变量 depth 来维护拓展的次数，该二叉树的最大深度即为 depth。
 *
 * 时间复杂度：O(n)，其中 n 为二叉树的节点个数。与方法一同样的分析，每个节点只会被访问一次。
 * 空间复杂度：此方法空间的消耗取决于队列存储的元素数量，其在最坏情况下会达到 O(n)。
 */
fun maxDepth2(root: TreeNode?): Int {
    if (root == null) {
        return 0
    }
    val queue = LinkedList<TreeNode>()
    queue.offer(root)
    var depth = 0
    while (!queue.isEmpty()) {
        var size = queue.size
        while (size > 0) {
            val treeNode = queue.poll()
            if (treeNode?.left != null) {
                queue.offer(treeNode?.left)
            }
            if (treeNode?.right != null) {
                queue.offer(treeNode?.right)
            }
            size--
        }
        depth++
    }
    return depth
}