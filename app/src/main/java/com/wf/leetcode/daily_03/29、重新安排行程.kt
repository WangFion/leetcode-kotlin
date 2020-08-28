package com.wf.leetcode.daily_03

import java.util.*
import kotlin.collections.HashMap

/**
 * leetcode -> com.wf.leetcode.daily_03 -> `29、重新安排行程`
 *
 * @Author: wf-pc
 * @Date: 2020-08-27 10:19
 * -------------------------
 * 332题：https://leetcode-cn.com/problems/reconstruct-itinerary/
 * 给定一个机票的字符串二维数组 [from, to]，子数组中的两个成员分别表示飞机出发和降落的机场地点，
 * 对该行程进行重新规划排序。所有这些机票都属于一个从 JFK（肯尼迪国际机场）出发的先生，所以该行程必须从 JFK 开始。
 *
 * 说明:
 * 如果存在多种有效的行程，你可以按字符自然排序返回最小的行程组合。例如，行程 ["JFK", "LGA"] 与 ["JFK", "LGB"] 相比就更小，排序更靠前
 * 所有的机场都用三个大写字母表示（机场代码）。
 * 假定所有机票至少存在一种合理的行程。
 *
 * 示例 1:
 * 输入: [["MUC", "LHR"], ["JFK", "MUC"], ["SFO", "SJC"], ["LHR", "SFO"]]
 * 输出: ["JFK", "MUC", "LHR", "SFO", "SJC"]
 *
 * 示例 2:
 * 输入: [["JFK","SFO"],["JFK","ATL"],["SFO","ATL"],["ATL","JFK"],["ATL","SFO"]]
 * 输出: ["JFK","ATL","JFK","SFO","ATL","SFO"]
 * 解释: 另一种有效的行程是 ["JFK","SFO","ATL","JFK","ATL","SFO"]。但是它自然排序更大更靠后。
 */
fun main() {
    val list = listOf(
        listOf("MUC", "LHR"),
        listOf("JFK", "MUC"),
        listOf("SFO", "SJC"),
        listOf("LHR", "SFO")
    )
    println(findItinerary(list).toTypedArray().contentToString())

    val list2 = listOf(
        listOf("JFK", "SFO"),
        listOf("JFK", "ATL"),
        listOf("SFO", "ATL"),
        listOf("ATL", "JFK"),
        listOf("ATL", "SFO")
    )
    println(findItinerary(list2).toTypedArray().contentToString())
}

/**
 * 欧拉通路、欧拉回路、欧拉图、半欧拉图
 * 我们化简本题题意：给定一个 n 个点 m 条边的图，要求从指定的顶点出发，
 * 经过所有的边恰好一次（可以理解为给定起点的「一笔画」问题），使得路径的字典序最小。
 * 这种「一笔画」问题与欧拉图或者半欧拉图有着紧密的联系，下面给出定义：
 *     通过图中所有边恰好一次且行遍所有顶点的通路称为欧拉通路。
 *     通过图中所有边恰好一次且行遍所有顶点的回路称为欧拉回路。
 *     具有欧拉回路的无向图称为欧拉图。
 *     具有欧拉通路但不具有欧拉回路的无向图称为半欧拉图。
 * 因为本题保证至少存在一种合理的路径，也就告诉了我们，这张图是一个欧拉图或者半欧拉图。我们只需要输出这条欧拉通路的路径即可。
 *
 * 时间复杂度：O(mlogm)，其中 m 是边的数量。对于每一条边我们需要 O(logm) 地删除它，最终的答案序列长度为 m+1，而与 n 无关。
 * 空间复杂度：O(m)，其中 m 是边的数量。我们需要存储每一条边。
 */
fun findItinerary(tickets: List<List<String>>): List<String> {
    val map = HashMap<String, PriorityQueue<String>>()
    for (ticket in tickets) {
        val from = ticket[0]
        val to = ticket[1]
        if (!map.containsKey(from)) {
            //字符串自带比较器，故可使用优先队列存储
            map[from] = PriorityQueue()
        }
        map[from]?.offer(to)
    }
    val list = ArrayList<String>()
    dfs(map, list, "JFK")
    return list.reversed()
}

fun dfs(map: Map<String, PriorityQueue<String>>, list: ArrayList<String>, ticket: String) {
    while (map.containsKey(ticket) && (map[ticket]?.size ?: 0) > 0) {
        dfs(map, list, map[ticket]?.poll() ?: "")
    }
    //逆序保存，返回结果时取反
    //后续没有通路，说明已经到达了终点
    list.add(ticket)
}