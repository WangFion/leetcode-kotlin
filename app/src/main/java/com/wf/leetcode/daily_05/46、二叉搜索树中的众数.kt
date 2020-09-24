package com.wf.leetcode.daily_05

import com.wf.leetcode.entity.TreeNode
import com.wf.leetcode.entity.arrayToTree

/**
 * leetcode -> com.wf.leetcode.daily_05 -> `46、二叉搜索树中的众数`
 *
 * @Author: wf-pc
 * @Date: 2020-09-24 09:15
 * -------------------------
 * 501题
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/find-mode-in-binary-search-tree
 *
 * 给定一个有相同值的二叉搜索树（BST），找出 BST 中的所有众数（出现频率最高的元素）。
 * 假定 BST 有如下定义：
 * 结点左子树中所含结点的值小于等于当前结点的值
 * 结点右子树中所含结点的值大于等于当前结点的值
 * 左子树和右子树都是二叉搜索树
 *
 * 例如：
 * 给定 BST [1,null,2,2],
 *  1
 *   \
 *   2
 *  /
 * 2
 * 返回[2].
 * 提示：如果众数超过1个，不需考虑输出顺序
 * 进阶：你可以不使用额外的空间吗？（假设由递归产生的隐式调用栈的开销不被计算在内）
 */
fun main() {
    val root = arrayToTree(arrayOf(1, null, 2, null, null, 2))

    println(findMode(root).contentToString())
}

/**
 * plan：Morris 中序遍历
 *
 * 时间复杂度：O(n)。每个点被访问的次数不会超过两次，故这里的时间复杂度是 O(n)。
 * 空间复杂度：O(1)。使用临时空间的大小和输入规模无关。
 */
val list = ArrayList<Int>()
var base: Int = 0
var count: Int = 0
var maxCount: Int = 0
fun findMode(root: TreeNode?): IntArray {
    var node = root
    var pre: TreeNode?
    while (node != null) {
        //中序遍历，如果左节点为空，则输出当前节点，然后遍历右节点
        if (node.left == null) {
            update(node.value)
            node = node.right
        } else {
            //1、找到先序节点，左移一步，然后一直向右移动
            pre = node.left
            while (pre?.right != null && pre.right != node) {
                pre = pre.right
            }
            //2、如果先序节点的右子树为空，则将右节点指向当前节点，然后继续遍历左子树
            if (pre?.right == null) {
                pre?.right = node
                node = node.left
            } else {
                //3、先序节点的右节点为当前节点，则当前节点左子树已遍历完，
                //断开连接，输出当前节点，然后继续遍历当前节点的右子树
                pre.right = null
                update(node.value)
                node = node.right
            }
        }
    }
    return list.toIntArray()
}

fun update(value: Int) {
    if (value == base) {
        count++
    } else {
        count = 1
        base = value
    }
    if (count == maxCount) {
        list.add(value)
    }
    if (count > maxCount) {
        maxCount = count
        list.clear()
        list.add(value)
    }
}
