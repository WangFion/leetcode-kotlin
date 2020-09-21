package com.wf.leetcode.daily_05

import com.wf.leetcode.entity.TreeNode
import com.wf.leetcode.entity.arrayToTree
import com.wf.leetcode.entity.treeToArray

/**
 * leetcode -> com.wf.leetcode.daily_05 -> `44、二叉搜索树转换为累加树`
 *
 * @Author: wf-pc
 * @Date: 2020-09-21 09:32
 * -------------------------
 * 538题
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/convert-bst-to-greater-tree
 *
 * 给定一个二叉搜索树（Binary Search Tree），把它转换成为累加树（Greater Tree)，
 * 使得每个节点的值是原来的节点值加上所有大于它的节点值之和。
 *
 * 例如：
 * 输入: 原始二叉搜索树:
 *    5
 *  /   \
 * 2     13
 * 输出: 转换为累加树:
 *     18
 *   /   \
 * 20     13
 */
fun main() {
    val root = arrayToTree(arrayOf(5, 2, 13))
    println(treeToArray(convertBST(root)).contentToString())

    val root2 = arrayToTree(arrayOf(5, 2, 13))
    println(treeToArray(convertBST2(root2)).contentToString())
}

/**
 * plan1：反向中序遍历
 * 从树中可以看出累加的顺讯是 右中左，所以我们需要中序遍历反过来遍历这个二叉树，然后顺序累加就可以了。
 *
 * 时间复杂度：O(n)，其中 n 是二叉搜索树的节点数。每一个节点恰好被遍历一次。
 * 空间复杂度：O(n)，为递归过程中栈的开销，平均情况下为 O(logn)，最坏情况下树呈现链状，为 O(n)。
 */
var sum = 0
fun convertBST(root: TreeNode?): TreeNode? {
    if (root != null) {
        convertBST(root.right)
        sum += root.value
        root.value = sum
        convertBST(root.left)
    }
    return root
}

/**
 * plan2：Morris（莫里斯）遍历算法
 * 算法思想参考：https://www.jianshu.com/p/484f587c967c
 *
 * 时间复杂度：O(n)，其中 nnn 是二叉搜索树的节点数。没有左子树的节点只被访问一次，有左子树的节点被访问两次。
 * 空间复杂度：O(1)。只操作已经存在的指针（树的空闲指针），因此只需要常数的额外空间。
 */
fun convertBST2(root: TreeNode?): TreeNode? {
    var sum = 0
    var node = root
    var nextOrder: TreeNode?
    while (node != null) {
        if (node.right == null) {
            sum += node.value
            node.value = sum
            node = node.left
        } else {
            //1、找到后序节点：当前节点向右走一步，然后一直向左走至无法走为止
            nextOrder = node.right
            while (nextOrder?.left != null && nextOrder.left != node) {
                nextOrder = nextOrder.left
            }
            if (nextOrder?.left == null) {
                //2、将后序节点的左孩子指向当前节点，然后继续遍历右子树
                nextOrder?.left = node
                node = node.right
            } else {
                //3、遍历完右子树则取出当前值，然后断开左孩子链接
                sum += node.value
                node.value = sum
                nextOrder.left = null
                node = node.left
            }
        }
    }
    return root
}