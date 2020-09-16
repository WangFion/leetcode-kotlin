package com.wf.leetcode.daily_05

import com.wf.leetcode.entity.TreeNode
import com.wf.leetcode.entity.arrayToTree
import com.wf.leetcode.entity.treeToArray
import java.util.*

/**
 * leetcode -> com.wf.leetcode.daily_05 -> `42、翻转二叉树`
 *
 * @Author: wf-pc
 * @Date: 2020-09-16 09:22
 * -------------------------
 * 226题
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/invert-binary-tree
 *
 * 翻转一棵二叉树。
 * 示例：
 * 输入：
 *      4
 *    /   \
 *   2     7
 *  / \   / \
 * 1   3 6   9
 * 输出：
 *      4
 *    /   \
 *   7     2
 *  / \   / \
 * 9   6 3   1
 * 备注:
 * 这个问题是受到 Max Howell 的 原问题 启发的 ：
 * 谷歌：我们90％的工程师使用您编写的软件(Homebrew)，但是您却无法在面试时在白板上写出翻转二叉树这道题，这太糟糕了。
 */
fun main() {
    val arr1 = arrayOf(4, 2, 7, 1, 3, 6, 9)
    val root1 = arrayToTree(arr1)
    println(treeToArray(invertTree(root1)).contentToString())

    val arr2 = arrayOf(4, 2, 7, 1, 3, 6, 9)
    val root2 = arrayToTree(arr2)
    println(treeToArray(invertTree2(root2)).contentToString())

    val arr3 = arrayOf(4, 2, 7, 1, 3, 6, 9)
    val root3 = arrayToTree(arr3)
    println(treeToArray(invertTree3(root3)).contentToString())
}

/**
 * plan1：递归 dfs
 * 时间复杂度：O(N)，其中 N 为二叉树节点的数目。我们会遍历二叉树中的每一个节点，对每个节点而言，
 *           我们在常数时间内交换其两棵子树。
 * 空间复杂度：O(N)。使用的空间由递归栈的深度决定，它等于当前节点在二叉树中的高度。在平均情况下，
 *           二叉树的高度与节点个数为对数关系，即 O(logN)。而在最坏情况下，树形成链状，空间复杂度为 O(N)。
 */
fun invertTree(root: TreeNode?): TreeNode? {
    if (root == null) {
        return null
    }
    val left = invertTree(root.left)
    val right = invertTree(root.right)
    root.left = right
    root.right = left
    return root
}

/**
 * plan2：迭代 bfs + stack
 */
fun invertTree2(root: TreeNode?): TreeNode? {
    if (root == null) {
        return null
    }
    val stack = Stack<TreeNode>()
    stack.push(root)
    while (stack.isNotEmpty()) {
        val node = stack.pop() ?: continue
        val temp = node.left
        node.left = node.right
        node.right = temp
        stack.push(node.left)
        stack.push(node.right)
    }
    return root
}

/**
 * plan3：迭代 bfs + queue
 */
fun invertTree3(root: TreeNode?): TreeNode? {
    if (root == null) {
        return null
    }
    val queue = LinkedList<TreeNode>()
    queue.offer(root)
    while (queue.isNotEmpty()) {
        val node = queue.poll() ?: continue
        val temp = node.left
        node.left = node.right
        node.right = temp
        queue.offer(node.left)
        queue.offer(node.right)
    }
    return root
}
