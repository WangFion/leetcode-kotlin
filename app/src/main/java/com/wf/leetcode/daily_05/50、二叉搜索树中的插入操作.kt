package com.wf.leetcode.daily_05

import com.wf.leetcode.entity.TreeNode
import com.wf.leetcode.entity.arrayToTree
import com.wf.leetcode.entity.treeToArray

/**
 * leetcode -> com.wf.leetcode.daily_05 -> `50、二叉搜索树中的插入操作`
 *
 * @Author: wf-pc
 * @Date: 2020-09-30 09:26
 * -------------------------
 * 701题
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/insert-into-a-binary-search-tree
 *
 * 给定二叉搜索树（BST）的根节点和要插入树中的值，将值插入二叉搜索树。 返回插入后二叉搜索树的根节点。
 * 输入数据保证，新值和原始二叉搜索树中的任意节点值都不同。
 * 注意，可能存在多种有效的插入方式，只要树在插入后仍保持为二叉搜索树即可。 你可以返回任意有效的结果。
 *
 * 例如,
 * 给定二叉搜索树:
 *     4
 *    / \
 *   2   7
 *  / \
 * 1   3
 * 和 插入的值: 5
 *
 * 你可以返回这个二叉搜索树:
 *      4
 *    /   \
 *   2     7
 *  / \   /
 * 1   3 5
 *
 * 或者这个树也是有效的:
 *      5
 *    /   \
 *   2     7
 *  / \
 * 1   3
 *      \
 *       4
 *
 * 提示：
 * 给定的树上的节点数介于 0 和 10^4 之间
 * 每个节点都有一个唯一整数值，取值范围从 0 到 10^8
 * -10^8 <= val <= 10^8
 * 新值和原始二叉搜索树中的任意节点值都不同
 */
fun main() {
    val root = arrayToTree(arrayOf(4, 2, 7, 1, 3))
    val newRoot = insertIntoBST(root, 5)
    println(treeToArray(newRoot).contentToString())

    val root2 = arrayToTree(arrayOf(4, 2, 7, 1, 3))
    val newRoot2 = insertIntoBST2(root2, 5)
    println(treeToArray(newRoot2).contentToString())
}

/**
 * plan1：迭代模拟建树
 * 时间复杂度：O(N)，其中 N 为树中节点的数目。最坏情况下，我们需要将值插入到树的最深的叶子结点上，而叶子节点最深为 O(N)。
 * 空间复杂度：O(1)。我们只使用了常数大小的空间。
 */
fun insertIntoBST(root: TreeNode?, value: Int): TreeNode? {
    if (root == null) {
        return TreeNode(value)
    }
    var node = root
    while (node != null) {
        if (value < node.value) {
            if (node.left == null) {
                node.left = TreeNode(value)
                break
            } else {
                node = node.left
            }
        } else {
            if (node.right == null) {
                node.right = TreeNode(value)
                break
            } else {
                node = node.right
            }
        }
    }
    return root
}

/**
 * plan2：递归
 * 时间复杂度：O(N)，其中 N 为树中节点的数目。最坏情况下，我们需要将值插入到树的最深的叶子结点上，而叶子节点最深为 O(N)。
 * 空间复杂度：O(N)。方法栈的最大深度。
 */
fun insertIntoBST2(root: TreeNode?, value: Int): TreeNode? {
    if (root == null) {
        return TreeNode(value)
    }
    if (value < root.value) {
        root.left = insertIntoBST2(root.left, value)
    } else {
        root.right = insertIntoBST2(root.right, value)
    }
    return root
}