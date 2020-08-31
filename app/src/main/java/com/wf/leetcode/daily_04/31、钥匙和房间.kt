package com.wf.leetcode.daily_04

import java.util.*

/**
 * leetcode -> com.wf.leetcode.daily_04 -> `31、钥匙和房间`
 *
 * @Author: wf-pc
 * @Date: 2020-08-31 09:21
 * -------------------------
 * 841题：https://leetcode-cn.com/problems/keys-and-rooms/
 * 有 N 个房间，开始时你位于 0 号房间。每个房间有不同的号码：0，1，2，...，N-1，并且房间里可能有一些钥匙能
 * 使你进入下一个房间。在形式上，对于每个房间 i 都有一个钥匙列表 rooms[i]，每个钥匙 rooms[i][j] 由
 * [0,1，...，N-1] 中的一个整数表示，其中 N = rooms.length。 钥匙 rooms[i][j] = v 可以打开编号为 v 的房间。
 * 最初，除 0 号房间外的其余所有房间都被锁住。你可以自由地在房间之间来回走动。
 * 如果能进入每个房间返回 true，否则返回 false。
 *
 * 示例 1：
 * 输入: [[1],[2],[3],[]]
 * 输出: true
 * 解释:
 * 我们从 0 号房间开始，拿到钥匙 1。
 * 之后我们去 1 号房间，拿到钥匙 2。
 * 然后我们去 2 号房间，拿到钥匙 3。
 * 最后我们去了 3 号房间。
 * 由于我们能够进入每个房间，我们返回 true。
 *
 * 示例 2：
 * 输入：[[1,3],[3,0,1],[2],[0]]
 * 输出：false
 * 解释：我们不能进入 2 号房间。
 *
 * 提示：
 * 1 <= rooms.length <= 1000
 * 0 <= rooms[i].length <= 1000
 * 所有房间中的钥匙数量总计不超过 3000。
 */
fun main() {
    val rooms1 = listOf(listOf(1), listOf(2), listOf(3), listOf())
    val rooms2 = listOf(listOf(1, 3), listOf(3, 0, 1), listOf(2), listOf(0))

    println(canVisitAllRooms(rooms1))
    println(canVisitAllRooms(rooms2))

    println(canVisitAllRooms2(rooms1))
    println(canVisitAllRooms2(rooms2))
}

/**
 * plan1：深度优先遍历
 *
 * 时间复杂度：O(n+m)，其中 n 是房间的数量，m 是所有房间中的钥匙数量的总数。
 * 空间复杂度：O(n)，其中 n 是房间的数量。主要为栈空间的开销。
 */
fun canVisitAllRooms(rooms: List<List<Int>>): Boolean {
    val vis = mutableListOf<Int>()
    dfs(rooms, vis, 0)
    return rooms.size == vis.size
}

fun dfs(rooms: List<List<Int>>, vis: MutableList<Int>, index: Int) {
    vis.add(index)
    for (i in rooms[index]) {
        //如果此房间没有进入过，则进入此房间并其所有钥匙能去的房间
        if (!vis.contains(i)) {
            dfs(rooms, vis, i)
        }
    }
}

/**
 * plan2：广度优先遍历
 *
 * 时间复杂度：O(n+m)，其中 n 是房间的数量，m 是所有房间中的钥匙数量的总数。
 * 空间复杂度：O(n)，其中 n 是房间的数量。主要为队列的开销。
 */
fun canVisitAllRooms2(rooms: List<List<Int>>): Boolean {
    val vis = mutableListOf<Int>()
    bfs(rooms, vis)
    return rooms.size == vis.size
}

fun bfs(rooms: List<List<Int>>, vis: MutableList<Int>) {
    val queue = LinkedList<Int>()
    queue.offer(0)
    vis.add(0)
    while (queue.isNotEmpty()) {
        val room = queue.poll()!!
        for (i in rooms[room]) {
            if (!vis.contains(i)) {
                vis.add(i)
                queue.offer(i)
            }
        }
    }
}