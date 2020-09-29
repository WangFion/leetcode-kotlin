package com.wf.leetcode.daily_05

import com.wf.leetcode.entity.TreeNode
import com.wf.leetcode.entity.treeToArray

/**
 * leetcode -> com.wf.leetcode.daily_05 -> `47、从中序与后序遍历序列构造二叉树`
 *
 * @Author: wf-pc
 * @Date: 2020-09-25 10:34
 * -------------------------
 * 106题
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/construct-binary-tree-from-inorder-and-postorder-traversal
 *
 * 根据一棵树的中序遍历与后序遍历构造二叉树。
 * 注意:
 * 你可以假设树中没有重复的元素。
 *
 * 例如，给出
 * 中序遍历 inorder = [9,3,15,20,7]
 * 后序遍历 postorder = [9,15,7,20,3]
 * 返回如下的二叉树：
 *   3
 *  / \
 * 9  20
 *   /  \
 *  15   7
 */
fun main() {
    val inorder = intArrayOf(9, 3, 15, 20, 7)
    val postorder = intArrayOf(9, 15, 7, 20, 3)
    println(treeToArray(buildTree(inorder, postorder)).contentToString())
}

/**
 * plan1：递归
 *
 * 时间复杂度：O(n)，其中 n 是树中的节点个数。
 * 空间复杂度：O(n)。我们需要使用 O(n) 的空间存储哈希表，以及 O(h)（其中 h 是树的高度）的空间表示递归时栈空间。
 *           这里 h<n，所以总空间复杂度为 O(n)。
 */
var postIndex = 0
fun buildTree(inorder: IntArray, postorder: IntArray): TreeNode? {
    val inorderMap = HashMap<Int, Int>()
    for ((i, v) in inorder.withIndex()) {
        inorderMap[v] = i
    }
    postIndex = postorder.size - 1
    return helper(postorder, inorderMap, 0, inorder.size - 1)
}

fun helper(
    postorder: IntArray,
    inorderMap: HashMap<Int, Int>,
    left: Int,
    right: Int
): TreeNode? {
    //没有节点了，则结束
    if (left > right) {
        return null
    }
    //构建根节点
    val value = postorder[postIndex]
    val root = TreeNode(value)
    //划分左右子树
    val mid = inorderMap[value]!!
    postIndex--
    //构建右子树
    root.right = helper(postorder, inorderMap, mid + 1, right)
    //构建左子树
    root.left = helper(postorder, inorderMap, left, mid - 1)
    return root
}

