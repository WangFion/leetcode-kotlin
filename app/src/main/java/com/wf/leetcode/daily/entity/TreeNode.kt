package com.wf.leetcode.daily.entity

import java.util.*
import kotlin.math.abs
import kotlin.math.max

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
    //1、初始化根节点
    val root = arr[0]?.let { TreeNode(it) }
    //2、创建列表存储节点
    val tree = mutableListOf<TreeNode?>()
    tree.add(root)
    //3、遍历数组构建成树，跟节点的个数为 n/2 -1
    for (i in 0 until arr.size / 2) {
        //4、创建左节点，在数组中的位置为2 * i + 1
        val left = arr[2 * i + 1]?.let { TreeNode(it) }
        //5、创建右节点，在数组中的位置为2 * i + 2
        val right = arr[2 * i + 2]?.let { TreeNode(it) }
        //6、为跟节点添加左右子节点
        tree[i]?.left = left
        tree[i]?.right = right
        //7、将左、右子节点放入列表循环创建其左右子节点
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
        } else {
            break
        }
    }
    return list.toTypedArray()
}

/**
 * 返回二叉树的最大深度
 */
fun treeDepth(root: TreeNode?): Int {
    return if (root == null) {
        0
    } else {
        max(treeDepth(root.left), treeDepth(root.right)) + 1
    }
}

/**
 * 先序遍历
 */
fun preOrderTraverse(root: TreeNode?) {
    if (root == null) {
        return
    }
    print("${root.value}  ")
    preOrderTraverse(root.left)
    preOrderTraverse(root.right)
}

/**
 * 中序遍历
 */
fun inOrderTraverse(root: TreeNode?) {
    if (root == null) {
        return
    }
    inOrderTraverse(root.left)
    print("${root.value}  ")
    inOrderTraverse(root.right)
}

/**
 * 后序遍历
 */
fun endOrderTraverse(root: TreeNode?) {
    if (root == null) {
        return
    }
    endOrderTraverse(root.left)
    endOrderTraverse(root.right)
    print("${root.value}  ")
}

fun main() {
    val treeNode = arrayToTree(arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9))
    println(treeToArray(treeNode).contentToString())
    println(treeDepth(treeNode))

    preOrderTraverse(treeNode)
    println()
    //1  2  4  8  9  5  3  6  7

    inOrderTraverse(treeNode)
    println()
    //8  4  9  2  5  1  6  3  7

    endOrderTraverse(treeNode)
    println()
    //8  9  4  5  2  6  7  3  1
}