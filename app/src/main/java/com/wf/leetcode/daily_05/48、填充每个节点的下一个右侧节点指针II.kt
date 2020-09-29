package com.wf.leetcode.daily_05

import java.util.*

/**
 * leetcode -> com.wf.leetcode.daily_05 -> `48、填充每个节点的下一个右侧节点指针 II`
 *
 * @Author: wf-pc
 * @Date: 2020-09-28 14:19
 * -------------------------
 * 117题
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/populating-next-right-pointers-in-each-node-ii
 *
 * 给定一个二叉树
 * struct Node {
 *     int val;
 *     Node *left;
 *     Node *right;
 *     Node *next;
 * }
 * 填充它的每个 next 指针，让这个指针指向其下一个右侧节点。如果找不到下一个右侧节点，则将 next 指针设置为 NULL。
 * 初始状态下，所有 next 指针都被设置为 NULL。
 *
 * 进阶：
 * 你只能使用常量级额外空间。
 * 使用递归解题也符合要求，本题中递归程序占用的栈空间不算做额外的空间复杂度。
 *
 * 示例：
 *     1                1-->null
 *    / \              / \
 *   2   3            2-->3-->null
 *  / \   \          / \   \
 * 4   5   7        4-->5-->7-->null
 * 输入：root = [1,2,3,4,5,null,7]
 * 输出：[1,#,2,3,#,4,5,7,#]
 * 解释：给定二叉树如图 A 所示，你的函数应该填充它的每个 next 指针，以指向其下一个右侧节点，如图 B 所示。
 *
 * 提示：
 * 树中的节点数小于 6000
 * -100 <= node.val <= 100
 */
data class Node(
    var value: Int = 0,
    var left: Node? = null,
    var right: Node? = null,
    var next: Node? = null
)

/**
 * plan1：bfs
 *
 * 时间复杂度：O(N)。我们需要遍历这棵树上所有的点，时间复杂度为 O(N)。
 * 空间复杂度：O(N)。即队列的空间代价。
 */
fun connect(root: Node?): Node? {
    if (root == null) {
        return null
    }
    val queue = LinkedList<Node>()
    queue.offer(root)
    while (queue.isNotEmpty()) {
        val size = queue.size
        var last: Node? = null
        for (i in 0 until size) {
            val node = queue.poll()
            //入队左节点
            node?.left?.let { queue.offer(it) }
            //入队右节点
            node?.right?.let { queue.offer(it) }
            //设置next节点
            last?.let { it.next = node }
            last = node
        }
    }
    return root
}

/**
 * plan2：利用上层的 next 指针建立下层的 next
 *
 * （1）从根节点开始。因为第 0 层只有一个节点，不需要处理。可以在上一层为下一层建立 next 指针。
 *     该方法最重要的一点是：位于第 x 层时为第 x+1 层建立 next 指针。
 *     一旦完成这些连接操作，移至第 x+1 层为第 x+2 层建立 next 指针。
 * （2）当遍历到某层节点时，该层节点的 next 指针已经建立。这样就不需要队列从而节省空间。
 *     每次只要知道下一层的最左边的节点，就可以从该节点开始，像遍历链表一样遍历该层的所有节点。
 *
 * 时间复杂度：O(N)。分析同「方法一」。
 * 空间复杂度：O(1)。
 */
fun connect2(root: Node?): Node? {
    if (root == null) {
        return null
    }
    var cur = root
    var last: Node?
    var nextStart: Node?
    while (cur != null) {
        last = null
        nextStart = null
        while (cur != null) {
            cur.left?.let {
                if (last != null){
                    last?.next = it
                }
                if (nextStart == null){
                    nextStart = it
                }
                last = it
            }
            cur.right?.let {
                if (last != null){
                    last?.next = it
                }
                if (nextStart == null){
                    nextStart = it
                }
                last = it
            }
            cur = cur.next
        }
        cur = nextStart
    }
    return root
}