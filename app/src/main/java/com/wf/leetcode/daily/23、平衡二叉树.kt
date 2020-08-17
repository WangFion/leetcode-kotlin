package com.wf.leetcode.daily

import com.wf.leetcode.daily.entity.TreeNode
import com.wf.leetcode.daily.entity.arrayToTree
import com.wf.leetcode.daily.entity.treeDepth
import kotlin.math.abs
import kotlin.math.max

/**
 * leetcode -> com.wf.leetcode.daily -> `23、平衡二叉树`
 *
 * @Author: wf-pc
 * @Date: 2020-08-17 10:16
 * -------------------------
 * 110题：https://leetcode-cn.com/problems/balanced-binary-tree/
 * 给定一个二叉树，判断它是否是高度平衡的二叉树。
 * 本题中，一棵高度平衡二叉树定义为：一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过1。
 *
 * 示例 1:
 * 给定二叉树 [3,9,20,null,null,15,7]
 *   3
 *  / \
 * 9  20
 *   /  \
 *  15   7
 * 返回 true 。
 *
 * 示例 2:
 * 给定二叉树 [1,2,2,3,3,null,null,4,4]
 *       1
 *      / \
 *     2   2
 *    / \
 *   3   3
 *  / \
 * 4   4
 * 返回 false 。
 */
fun main() {
    val arr1 = arrayOf(3, 9, 20, null, null, 15, 7);
    val tree1 = arrayToTree(arr1)
    println(isBalanced(tree1))
    println(isBalanced2(tree1))

    val arr2 = arrayOf(1, 2, 2, 3, 3, null, null, 4, 4);
    val tree2 = arrayToTree(arr2)
    println(isBalanced(tree2))
    println(isBalanced2(tree2))
}

/**
 * plan1：自顶向下递归
 * 一棵二叉树是平衡二叉树，当且仅当其所有子树也都是平衡二叉树，
 * 因此可以使用递归的方式判断二叉树是不是平衡二叉树，递归的顺序可以是自顶向下或者自底向上。
 *
 * 时间复杂度：O(n^2)，其中 n 是二叉树中的节点个数。
 *           最坏情况下，二叉树是满二叉树，需要遍历二叉树中的所有节点，时间复杂度是 O(n)。
 *           对于节点 p，如果它的高度是 d，则 height(p) 最多会被调用 d 次（即遍历到它的每一个祖先节点时）。
 *           对于平均的情况，一棵树的高度 h 满足 O(h)=O(logn)，因为 d≤h，所以总时间复杂度为 O(nlogn)。
 *           对于最坏的情况，二叉树形成链式结构，高度为 O(n)，此时总时间复杂度为 O(n^2)。
 * 空间复杂度：O(n)，其中 n 是二叉树中的节点个数。空间复杂度主要取决于递归调用的层数，递归调用的层数不会超过 n。
 */
fun isBalanced(root: TreeNode?): Boolean {
    return if (root == null) {
        true
    } else {
        abs(treeDepth(root.left) - treeDepth(root.right)) <= 1
                && isBalanced(root.left) && isBalanced(root.right)
    }
}

/**
 * plan2：自底向上递归
 * 自底向上递归的做法类似于后序遍历，对于当前遍历到的节点，先递归地判断其左右子树是否平衡，
 * 再判断以当前节点为根的子树是否平衡。如果一棵子树是平衡的，则返回其高度（高度一定是非负整数），
 * 否则返回 −1。如果存在一棵子树不平衡，则整个二叉树一定不平衡。
 *
 * 时间复杂度：O(n)，其中 n 是二叉树中的节点个数。使用自底向上的递归，每个节点的计算高度和判断是否平衡都只
 *            需要处理一次，最坏情况下需要遍历二叉树中的所有节点，因此时间复杂度是 O(n)。
 * 空间复杂度：O(n)，其中 n 是二叉树中的节点个数。空间复杂度主要取决于递归调用的层数，递归调用的层数不会超过 n。
 */
fun isBalanced2(root: TreeNode?): Boolean {
    return height(root) >= 0
}

fun height(root: TreeNode?): Int {
    if (root == null) {
        return 0
    }
    val leftHeight = height(root.left)
    val rightHeight = height(root.right)
    return if (leftHeight == -1 || rightHeight == -1 || abs(leftHeight - rightHeight) > 1) {
        -1
    } else {
        max(leftHeight, rightHeight) + 1
    }
}