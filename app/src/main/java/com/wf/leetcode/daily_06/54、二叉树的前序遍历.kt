package com.wf.leetcode.daily_06

import com.wf.leetcode.entity.TreeNode
import com.wf.leetcode.entity.arrayToTree
import java.util.*
import kotlin.collections.ArrayList

/**
 * leetcode -> com.wf.leetcode.daily_06 -> `54、二叉树的前序遍历`
 *
 * @Author: wf-pc
 * @Date: 2020-10-27 13:59
 * -------------------------
 *
 * 144题
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/binary-tree-preorder-traversal
 *
 * 给定一个二叉树，返回它的 前序 遍历。
 * 示例:
 * 输入: [1,null,2,3]
 * 1
 *  \
 *   2
 *  /
 * 3
 * 输出: [1,2,3]
 * 进阶: 递归算法很简单，你可以通过迭代算法完成吗？
 */
fun main() {
    val array = arrayOf(1, null, 2, null, null, 3)
    val root = arrayToTree(array)

    println(preOrderTraversal(root).toIntArray().contentToString())
    println(preOrderTraversal2(root).toIntArray().contentToString())
    println(preOrderTraversal3(root).toIntArray().contentToString())
    println(preOrderTraversal4(root).toIntArray().contentToString())
}

/**
 * plan1：递归
 *
 * 时间复杂度：O(n)，其中 n 是二叉树的节点数。每一个节点恰好被遍历一次。
 * 空间复杂度：O(n)，为递归过程中栈的开销，平均情况下为 O(logn)，最坏情况下树呈现链状，为 O(n)。
 */
fun preOrderTraversal(root: TreeNode?): List<Int> {
    val list = ArrayList<Int>()
    if (root == null) {
        return list
    }
    dfs(root, list)
    return list
}

fun dfs(node: TreeNode?, list: ArrayList<Int>) {
    if (node == null) {
        return
    }
    list.add(node.value)
    dfs(node.left, list)
    dfs(node.right, list)
}

/**
 * plan2：迭代
 *
 * 时间复杂度：O(n)，其中 n 是二叉树的节点数。每一个节点恰好被遍历一次。
 * 空间复杂度：O(n)，为迭代过程中显式栈的开销，平均情况下为 O(logn)，最坏情况下树呈现链状，为 O(n)。
 */
fun preOrderTraversal2(root: TreeNode?): List<Int> {
    val list = ArrayList<Int>()
    val stack = Stack<TreeNode>()
    var node = root
    while (node != null || stack.isNotEmpty()) {
        while (node != null) {
            list.add(node.value)
            stack.push(node)
            node = node.left
        }
        node = stack.pop()
        node = node.right
    }
    return list
}

/**
 * plan3：Morris 遍历
 *
 * 时间复杂度：O(n)，其中 n 是二叉树的节点数。没有左子树的节点只被访问一次，有左子树的节点被访问两次。
 * 空间复杂度：O(1)。只操作已经存在的指针（树的空闲指针），因此只需要常数的额外空间。
 */
fun preOrderTraversal3(root: TreeNode?): List<Int> {
    val list = ArrayList<Int>()
    var node = root
    var preNode: TreeNode? = null
    while (node != null) {
        if (node.left == null) {
            list.add(node.value)
            node = node.right
        } else {
            //1、找到前序节点：当前节点向左走一步，然后一直向右走至无法走为止
            preNode = node.left
            while (preNode?.right != null && preNode.right != node) {
                preNode = preNode.right
            }
            if (preNode?.right == null) {
                //2、取出当前值，然后将前序节点的右孩子指向当前节点，然后继续遍历左子树
                list.add(node.value)
                preNode?.right = node
                node = node.left
            } else {
                //3、遍历完后断开右孩子链接
                preNode.right = null
                node = node.right
            }
        }
    }
    return list
}

/**
 * plan4：标记法
 * 其核心思想如下：
 * （1）新节点存储TreeNode，已访问的节点存储值Int。
 * （2）如果遇到的节点为TreeNode，则将其右子节点、左子节点、自身value依次入栈。
 * （3）如果遇到的节点为值Int，则将值输出。
 * 注：栈是一种 先进后出 的结构，出栈顺序为 中，左，右
 *     那么入栈顺序必须调整为倒序，也就是 右，左，中
 *
 */
fun preOrderTraversal4(root: TreeNode?): List<Int> {
    val list = ArrayList<Int>()
    val stack = Stack<Any?>()
    stack.push(root)
    while (stack.isNotEmpty()) {
        val node = stack.pop() ?: continue
        if (node is Int) {
            list.add(node)
        } else {
            node as TreeNode
            stack.push(node.right)
            stack.push(node.left)
            stack.push(node.value)
        }
    }
    return list
}