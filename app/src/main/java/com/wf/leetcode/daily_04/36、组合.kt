package com.wf.leetcode.daily_04

/**
 * leetcode -> com.wf.leetcode.daily_04 -> `36、组合`
 *
 * @Author: wf-pc
 * @Date: 2020-09-08 15:48
 * -------------------------
 * 77题
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/combinations
 *
 * 给定两个整数 n 和 k，返回 1 ... n 中所有可能的 k 个数的组合。
 *
 * 示例:
 * 输入: n = 4, k = 2
 * 输出:
 * [
 *     [2,4],
 *     [3,4],
 *     [2,3],
 *     [1,2],
 *     [1,3],
 *     [1,4],
 * ]
 */
fun main() {
    for (list in combine(4, 2)) {
        println(list.toTypedArray().contentToString())
    }
    println()

    for (list in combine2(4, 2)) {
        println(list.toTypedArray().contentToString())
    }
    println()

    for (list in combine3(4, 2)) {
        println(list.toTypedArray().contentToString())
    }
}

/**
 * plan1：递归 + 剪枝
 *
 * 时间复杂度：O((nk)×k)
 * 空间复杂度：O(n+k)=O(n)，即递归使用栈空间的空间代价和临时数组 temp 的空间代价。
 */
fun combine(n: Int, k: Int): List<List<Int>> {
    val temp = ArrayList<Int>()
    val ans = ArrayList<ArrayList<Int>>()
    dfs(ans, temp, 1, n, k)
    return ans
}

fun dfs(ans: ArrayList<ArrayList<Int>>, temp: ArrayList<Int>, cur: Int, n: Int, k: Int) {
    // 剪枝：temp 长度加上区间 [cur, n] 的长度小于 k，不可能构造出长度为 k 的 temp
    if (temp.size + (n - cur + 1) < k) {
        return
    }
    //temp 长度满足要求则为一个解，记录到 ans 里面
    if (temp.size == k) {
        ans.add(ArrayList(temp))
        return
    }
    //选择当前 cur
    temp.add(cur)
    dfs(ans, temp, cur + 1, n, k)
    //不选择当前 cur
    temp.removeAt(temp.size - 1)
    dfs(ans, temp, cur + 1, n, k)
}

/**
 * plan2：回溯 + 剪枝
 */
fun combine2(n: Int, k: Int): List<List<Int>> {
    val temp = ArrayList<Int>()
    val ans = ArrayList<ArrayList<Int>>()
    dfs2(ans, temp, 1, n, k)
    return ans
}

fun dfs2(ans: ArrayList<ArrayList<Int>>, temp: ArrayList<Int>, cur: Int, n: Int, k: Int) {
    if (temp.size == k) {
        ans.add(ArrayList(temp))
        return
    }
    // 剪枝：temp 长度加上区间 [cur, n] 的长度小于 k，不可能构造出长度为 k 的 temp
    for (i in cur..n - (k - temp.size) + 1) {
        temp.add(i)
        dfs2(ans, temp, i + 1, n, k)
        temp.removeAt(temp.size - 1)
    }
}

/**
 * plan3：非递归（字典序法）实现组合型枚举
 *
 * 原序列中被选中的数    对应的二进制数    方案
 * 43[2][1]            0011             2,1
 * 4[3]2[1]            0101             3,1
 * 4[3][2]1            0110             3,2
 * [4]32[1]            1001             4,1
 * [4]3[2]1            1010             4,2
 * [4][3]21            1100             4,3
 *
 * 时间复杂度：O((nk)×k)。外层循环的执行次数是 (nk) 次，每次需要做一个 O(k) 的添加答案和 O(k) 的内层循环，
 *           故时间复杂度 O((nk)×k)。
 * 空间复杂度：O(k)。即 temp 的空间代价。
 */
fun combine3(n: Int, k: Int): List<List<Int>> {
    val temp = ArrayList<Int>()
    val ans = ArrayList<ArrayList<Int>>()
    // 初始化
    // 将 temp 中 [0, k - 1] 每个位置 i 设置为 i + 1，即 [0, k - 1] 存 [1, k]
    // 末尾加一位 n + 1 作为哨兵
    for (i in 1..k) {
        temp.add(i)
    }
    temp.add(n + 1)

    var j = 0
    while (j < k) {
        ans.add(ArrayList(temp.subList(0, k)))
        j = 0
        // 寻找第一个 temp[j] + 1 != temp[j + 1] 的位置 t
        // 我们需要把 [0, t - 1] 区间内的每个位置重置成 [1, t]
        while (j < k && temp[j] + 1 == temp[j + 1]) {
            temp[j] = j + 1
            ++j
        }
        // j 是第一个 temp[j] + 1 != temp[j + 1] 的位置
        temp[j] = temp[j] + 1
    }
    return ans
}

