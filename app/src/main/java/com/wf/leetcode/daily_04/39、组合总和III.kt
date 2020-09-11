package com.wf.leetcode.daily_04

/**
 * leetcode -> com.wf.leetcode.daily_04 -> `39、组合总和III`
 *
 * @Author: wf-pc
 * @Date: 2020-09-11 09:07
 * -------------------------
 *
 * 216题
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/combination-sum-iii
 *
 * 找出所有相加之和为 n 的 k 个数的组合。组合中只允许含有 1 - 9 的正整数，并且每种组合中不存在重复的数字。
 * 说明：
 * 所有数字都是正整数。
 * 解集不能包含重复的组合。
 *
 * 示例 1:
 * 输入: k = 3, n = 7
 * 输出: [[1,2,4]]
 *
 * 示例 2:
 * 输入: k = 3, n = 9
 * 输出: [[1,2,6], [1,3,5], [2,3,4]]
 */
fun main() {
    for (list in combinationSumIII(3, 7)) {
        print(list.toIntArray().contentToString())
        print(" ")
    }
    println()
    for (list in combinationSumIII2(3, 7)) {
        print(list.toIntArray().contentToString())
        print(" ")
    }
    println()
    for (list in combinationSumIII3(3, 7)) {
        print(list.toIntArray().contentToString())
        print(" ")
    }
    println()
    println()


    for (list in combinationSumIII(3, 9)) {
        print(list.toIntArray().contentToString())
        print(" ")
    }
    println()
    for (list in combinationSumIII2(3, 9)) {
        print(list.toIntArray().contentToString())
        print(" ")
    }
    println()
    for (list in combinationSumIII3(3, 9)) {
        print(list.toIntArray().contentToString())
        print(" ")
    }
    println()
    println()


    for (list in combinationSumIII(3, 15)) {
        print(list.toIntArray().contentToString())
        print(" ")
    }
    println()
    for (list in combinationSumIII2(3, 15)) {
        print(list.toIntArray().contentToString())
        print(" ")
    }
    println()
    for (list in combinationSumIII3(3, 15)) {
        print(list.toIntArray().contentToString())
        print(" ")
    }
    println()
}

/**
 * plan1：回溯 + 剪枝
 */
fun combinationSumIII(k: Int, n: Int): List<List<Int>> {
    val res = ArrayList<ArrayList<Int>>()
    val path = ArrayList<Int>()
    dfsIII(res, path, 1, k, n)
    return res
}

fun dfsIII(
    res: ArrayList<ArrayList<Int>>,
    path: ArrayList<Int>,
    start: Int,
    k: Int,
    n: Int
) {
    if (path.size == k && n == 0) {
        res.add(ArrayList(path))
        return
    }
    if (n < 0) {
        return
    }
    for (i in start..9) {
        path.add(i)
        dfsIII(res, path, i + 1, k, n - i)
        path.removeAt(path.size - 1)
    }
}


/**
 * plan2：递归 + 枚举
 */
fun combinationSumIII2(k: Int, n: Int): List<List<Int>> {
    val res = ArrayList<ArrayList<Int>>()
    val path = ArrayList<Int>()
    dfsIII2(res, path, 1, k, n)
    return res
}

fun dfsIII2(
    res: ArrayList<ArrayList<Int>>,
    path: ArrayList<Int>,
    start: Int,
    k: Int,
    n: Int
) {
    if (path.size == k && n == 0) {
        res.add(ArrayList(path))
        return
    }
    if (n < 0 || start > 9) {
        return
    }
    path.add(start)
    dfsIII(res, path, start + 1, k, n - start)
    path.removeAt(path.size - 1)
    dfsIII(res, path, start + 1, k, n)
}

/**
 * plan3：二进制枚举
 *
 * 「组合中只允许含有 1−9 的正整数，并且每种组合中不存在重复的数字」意味着这个组合中最多包含 9 个数字。
 * 我们可以把原问题转化成集合 S={1,2,3,4,5,6,7,8,9}，我们要找出 S 的当中满足如下条件的子集：
 * （1）大小为 k
 * （2）集合中元素的和为 n
 *
 * 因此我们可以用子集枚举的方法来做这道题。即原序列中有 9 个数，每个数都有两种状态，「被选择到子集中」和
 * 「不被选择到子集中」，所以状态的总数为 2^9。我们用一个 9 位二进制数 mask 来记录当前所有位置的状态，
 * 从低到高第 i 位为 0 表示 i 不被选择到子集中，为 1 表示 i 被选择到子集中。当我们按顺序枚举 [0, 2^9 - 1]
 * 中的所有整数的时候，就可以不重不漏地把每个状态枚举到，
 *
 * 对于一个状态 mask，我们可以用位运算的方法得到对应的子集序列，然后再判断是否满足上面的两个条件，如果满足，就记录答案。
 * 如何通过位运算来得到 mask 各个位置的信息？对于第 i 个位置我们可以判断 (1 << i) & mask 是否为 0，
 * 如果不为 0 则说明 i 在子集当中。当然，这里要注意的是，一个 9 位二进制数 i 的范围是 [0, 8]，而可选择的
 * 数字是 [1, 9]，所以我们需要做一个映射，最简单的办法就是当我们知道 i 位置不为 0 的时候将 i + 1 加入子集。
 *
 * 复杂度分析
 * 时间复杂度：O(M × 2^M)，其中 M 为集合的大小，本题中 M 固定为 9。一共有 2^M 个状态，
 *           每个状态需要 O(M + k) = O(M) 的判断 （k≤M），故时间复杂度为 O(M × 2^M)。
 * 空间复杂度：O(M)。即 path 的空间代价。
 */
fun combinationSumIII3(k: Int, n: Int): List<List<Int>> {
    val res = ArrayList<ArrayList<Int>>()
    val path = ArrayList<Int>()
    for (mask in 0 until (1 shl 9)) {
        if (check(path, mask, k, n)) {
            res.add(ArrayList(path))
        }
    }
    return res
}

fun check(path: ArrayList<Int>, mask: Int, k: Int, n: Int): Boolean {
    path.clear()
    for (i in 0 until 9) {
        if ((1 shl i) and mask != 0) {
            path.add(i + 1)
        }
    }
    if (path.size != k) {
        return false
    }
    var sum = 0
    for (temp in path) {
        sum += temp
    }
    return sum == n
}
