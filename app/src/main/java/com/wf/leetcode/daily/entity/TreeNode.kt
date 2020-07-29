package com.wf.leetcode.daily.entity

import java.util.*

/**
 * leetcode -> com.wf.leetcode.daily.entity -> TreeNode
 *
 * @Author: wf-pc
 * @Date: 2020-07-07 16:03
 */
class TreeNode(var value: Int, var left: TreeNode?, var right: TreeNode?) {

    constructor(value: Int) : this(value, null, null)
}

/**
 * 将数组构建成二叉树
 */
fun arrayToTree(arr: Array<out Int?>): TreeNode? {
    if (arr.isEmpty() || arr[0] == null) {
        return null
    }
    val root = arr[0]?.let { TreeNode(it) }
    val tree = mutableListOf<TreeNode?>()
    tree.add(root)
    for (i in 0..arr.size / 2 - 1) {
        val left = arr[2 * i + 1]?.let { TreeNode(it) }
        val right = arr[2 * i + 2]?.let { TreeNode(it) }
        tree[i]?.left = left
        tree[i]?.right = right
        tree.add(left)
        tree.add(right)
    }
    return root
}

/**
 * 将二叉树转换成数组
 */
fun treeToArray(node: TreeNode?): Array<Int?> {
    if (node == null) {
        return arrayOf()
    }
    val list = mutableListOf<Int?>()
    val queue = LinkedList<TreeNode?>()
    queue.offer(node)
    while (!queue.isEmpty()) {
        var size = queue.size
        while (size > 0) {
            val tmp = queue.poll()
            list.add(tmp?.value)
            if (tmp != null) {
                queue.offer(tmp.left)
                queue.offer(tmp.right)
            }
            size--
        }
    }
    for (i in list.size - 1 downTo 0) {
        if (list[i] == null) {
            list.removeAt(i)
        } else{
            break
        }
    }
    return list.toTypedArray()
}