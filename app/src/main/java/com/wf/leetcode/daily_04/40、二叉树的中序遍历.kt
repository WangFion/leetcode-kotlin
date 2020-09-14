package com.wf.leetcode.daily_04

import com.wf.leetcode.entity.TreeNode
import com.wf.leetcode.entity.arrayToTree
import java.util.*
import kotlin.collections.ArrayList

/**
 * leetcode -> com.wf.leetcode.daily_04 -> `40、二叉树的中序遍历`
 *
 * @Author: wf-pc
 * @Date: 2020-09-14 09:17
 * -------------------------
 *
 * 94题
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/binary-tree-inorder-traversal
 *
 * 给定一个二叉树，返回它的中序 遍历。
 *示例:
 * 输入: [1,null,2,3]
 *  1
 *   \
 *   2
 *  /
 * 3
 * 输出: [1,3,2]
 * 进阶: 递归算法很简单，你可以通过迭代算法完成吗？
 */
fun main() {
    val root = arrayToTree(arrayOf(1, null, 2, null, null, 3))

    println(inorderTraversal(root).toTypedArray().contentToString())
    println(inorderTraversal2(root).toTypedArray().contentToString())
    println(inorderTraversal3(root).toTypedArray().contentToString())
    println(inorderTraversal4(root).toTypedArray().contentToString())
}

/**
 * plan1：递归
 *
 * 时间复杂度：O(n)，其中 n 为二叉树节点的个数。二叉树的遍历中每个节点会被访问一次且只会被访问一次。
 * 空间复杂度：O(n)。空间复杂度取决于递归的栈深度，而栈深度在二叉树为一条链的情况下会达到 O(n) 的级别。
 */
fun inorderTraversal(root: TreeNode?): List<Int> {
    val list = ArrayList<Int>()
    dfs(list, root)
    return list
}

fun dfs(list: ArrayList<Int>, node: TreeNode?) {
    if (node == null) {
        return
    }
    dfs(list, node.left)
    list.add(node.value)
    dfs(list, node.right)
}

/**
 * plan2: 迭代 + 栈
 *
 * 时间复杂度：O(n)，其中 n 为二叉树节点的个数。二叉树的遍历中每个节点会被访问一次且只会被访问一次。
 * 空间复杂度：O(n)。空间复杂度取决于栈深度，而栈深度在二叉树为一条链的情况下会达到 O(n) 的级别。
 */
fun inorderTraversal2(root: TreeNode?): List<Int> {
    val list = ArrayList<Int>()
    val stack = Stack<TreeNode>()
    var node = root
    while (node != null || stack.isNotEmpty()) {
        //入栈左节点
        while (node != null) {
            stack.push(node)
            node = node.left
        }
        //出栈并记录值
        node = stack.pop()
        list.add(node.value)
        //获取右节点，再将右节点的左孩子依次入栈
        node = node.right
    }
    return list
}

/**
 * plan3：Morris（莫里斯）遍历算法
 * 算法思想参考：https://www.jianshu.com/p/484f587c967c
 *
 * 时间复杂度：O(n)，其中 n 为二叉搜索树的节点个数。Morris 遍历中每个节点会被访问两次，因此总时间复杂度为 O(2n)=O(n)。
 * 空间复杂度：O(1)。
 */
fun inorderTraversal3(root: TreeNode?): List<Int> {
    val list = ArrayList<Int>()
    var preOrder: TreeNode? = null
    var node = root
    while (node != null) {
        if (node.left == null) {
            list.add(node.value)
            node = node.right
        } else {
            //1、找到前序节点：当前节点向左走一步，然后一直向右走至无法走为止
            preOrder = node.left
            while (preOrder?.right != null && preOrder.right != node) {
                preOrder = preOrder.right
            }
            if (preOrder?.right == null) {
                //2、将前序节点的右孩子指向当前节点，然后继续遍历左子树
                preOrder?.right = node
                node = node.left
            } else {
                //3、遍历完左子树则取出当前值，然后断开右孩子链接
                list.add(node.value)
                preOrder.right = null
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
 * （2）如果遇到的节点为TreeNode，则将其右子节点、自身value、左子节点依次入栈。
 * （3）如果遇到的节点为值Int，则将值输出。
 * 注：栈是一种 先进后出 的结构，出栈顺序为 左，中，右
 *     那么入栈顺序必须调整为倒序，也就是 右，中，左
 *
 */
fun inorderTraversal4(root: TreeNode?): List<Int> {
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
            stack.push(node.value)
            stack.push(node.left)
        }
    }
    return list
}