package com.wf.leetcode.daily_05

import com.wf.leetcode.entity.TreeNode
import com.wf.leetcode.entity.arrayToTree
import java.util.*
import kotlin.collections.ArrayList

/**
 * leetcode -> com.wf.leetcode.daily_05 -> `49、二叉树的后序遍历`
 *
 * @Author: wf-pc
 * @Date: 2020-09-29 09:07
 * 145题
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/binary-tree-postorder-traversal
 *
 * 给定一个二叉树，返回它的 后序 遍历。
 * 示例:
 * 输入: [1,null,2,3]
 *  1
 *   \
 *   2
 *  /
 * 3
 * 输出: [3,2,1]
 * 进阶: 递归算法很简单，你可以通过迭代算法完成吗？
 */
fun main() {
    val array = arrayOf(1, null, 2, null, null, 3)
    println(postOrderTraversal(arrayToTree(array)).toTypedArray().contentToString())
    println(postOrderTraversal2(arrayToTree(array)).toTypedArray().contentToString())
    println(postOrderTraversal3(arrayToTree(array)).toTypedArray().contentToString())
    println(postOrderTraversal4(arrayToTree(array)).toTypedArray().contentToString())
}

/**
 * plan1：dfs
 *
 * 时间复杂度：O(n)，其中 nnn 是二叉搜索树的节点数。每一个节点恰好被遍历一次。
 * 空间复杂度：O(n)，为递归过程中栈的开销，平均情况下为 O(logn)，最坏情况下树呈现链状，为 O(n)。
 */
fun postOrderTraversal(root: TreeNode?): List<Int> {
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
    dfs(node.left, list)
    dfs(node.right, list)
    list.add(node.value)
}

/**
 * plan2：迭代
 * 显式地将这个栈模拟出来，其余的实现与细节都与递归相同
 *
 * 时间复杂度：O(n)，其中 n 是二叉搜索树的节点数。每一个节点恰好被遍历一次。
 * 空间复杂度：O(n)，为迭代过程中显式栈的开销，平均情况下为 O(logn)，最坏情况下树呈现链状，为 O(n)。
 */
fun postOrderTraversal2(root: TreeNode?): List<Int> {
    val list = ArrayList<Int>()
    if (root == null) {
        return list
    }
    val stack = Stack<TreeNode>()
    var node = root
    var prev: TreeNode? = null
    while (node != null || stack.isNotEmpty()) {
        //1、把当前节点左节点入栈
        while (node != null) {
            stack.push(node)
            node = node.left
        }
        //2、弹出栈顶元素
        val temp = stack.pop()
        //3、当前节点没有右节点，或者当前节点的右节点已经处理过了，则输出当前节点
        if (temp.right == null || temp.right == prev) {
            list.add(temp.value)
            prev = temp
        } else {
            //4、存在右子树，则把当前节点入栈，然后把右子树的左节点依次入栈
            stack.push(temp)
            node = temp.right
        }
    }
    return list
}

/**
 * plan3：Morris 遍历
 *
 * 时间复杂度：O(n)，其中 n 是二叉树的节点数。没有左子树的节点只被访问一次，有左子树的节点被访问两次。
 * 空间复杂度：O(1)。只操作已经存在的指针（树的空闲指针），因此只需要常数的额外空间。
 */
fun postOrderTraversal3(root: TreeNode?): List<Int> {
    val list = ArrayList<Int>()
    if (root == null) {
        return list
    }
    var p1 = root
    var p2: TreeNode? = null
    while (p1 != null) {
        p2 = p1.left
        if (p2 != null) {
            while (p2?.right != null && p2.right != p1) {
                p2 = p2.right
            }
            if (p2?.right == null) {
                p2?.right = p1
                p1 = p1.left
                continue
            } else {
                p2.right = null
                addPath(list, p1.left)
            }
        }
        p1 = p1.right
    }
    addPath(list, root)
    return list
}

fun addPath(res: ArrayList<Int>, treeNode: TreeNode?) {
    var node = treeNode
    val tmp = ArrayList<Int>()
    while (node != null) {
        tmp.add(node.value)
        node = node.right
    }
    for (i in tmp.size - 1 downTo 0) {
        res.add(tmp[i])
    }
}

/**
 * plan4：标记法
 * 其核心思想如下：
 * （1）新节点存储TreeNode，已访问的节点存储值Int。
 * （2）如果遇到的节点为TreeNode，则将其 自身value、右子节点、左子节点 依次入栈。
 * （3）如果遇到的节点为值Int，则将值输出。
 * 注：栈是一种 先进后出 的结构，出栈顺序为 左，右，中
 *     那么入栈顺序必须调整为倒序，也就是 中，右，左
 */
fun postOrderTraversal4(root: TreeNode?): List<Int> {
    val list = ArrayList<Int>()
    val stack = Stack<Any?>()
    stack.push(root)
    while (stack.isNotEmpty()) {
        val node = stack.pop() ?: continue
        if (node is Int) {
            list.add(node)
        } else {
            node as TreeNode
            stack.push(node.value)
            stack.push(node.right)
            stack.push(node.left)
        }
    }
    return list
}