package com.wf.leetcode.daily_04

import java.util.*
import kotlin.collections.ArrayList

/**
 * leetcode -> com.wf.leetcode.daily_04 -> `38、组合总和II`
 *
 * @Author: wf-pc
 * @Date: 2020-09-10 16:11
 * -------------------------
 *
 * 40题
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/combination-sum-ii
 * 给定一个数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。
 * candidates 中的每个数字在每个组合中只能使用一次。
 * 说明：
 * 所有数字（包括目标数）都是正整数。
 * 解集不能包含重复的组合。
 *
 * 示例 1:
 * 输入: candidates = [10,1,2,7,6,1,5], target = 8,
 * 所求解集为:
 * [
 *   [1, 7],
 *   [1, 2, 5],
 *   [2, 6],
 *   [1, 1, 6]
 * ]
 *
 * 示例 2:
 * 输入: candidates = [2,5,2,1,2], target = 5,
 * 所求解集为:
 * [
 *   [1,2,2],
 *   [5]
 * ]
 */
fun main() {
    val arr1 = intArrayOf(10, 1, 2, 7, 6, 1, 5)
    val arr2 = intArrayOf(2, 5, 2, 1, 2)
    for (list in combinationSumII(arr1, 8)) {
        println(list.toTypedArray().contentToString())
    }
    println()

    for (list in combinationSumII(arr2, 5)) {
        println(list.toTypedArray().contentToString())
    }
}

/**
 * plan1：回溯 + 大剪枝 + 小剪枝（去重）
 *
 * 树形图：
 * app/src/main/img/38、组合总和II-1.png
 * app/src/main/img/38、组合总和II-2.png
 */
fun combinationSumII(candidates: IntArray, target: Int): List<List<Int>> {
    val res = ArrayList<ArrayList<Int>>()
    if (candidates.isEmpty()) {
        return res
    }
    // 排序是剪枝的关键
    Arrays.sort(candidates)
    val path = ArrayList<Int>()
    dfsII(candidates, target, 0, path, res)
    return res
}

/**
 * @param candidates 候选数组
 * @param target     每减去一个元素，目标值变小
 * @param begin      搜索起点
 * @param path       从根结点到叶子结点的路径，是一个栈
 * @param res        结果集列表
 */
fun dfsII(
    candidates: IntArray,
    target: Int,
    begin: Int,
    path: ArrayList<Int>,
    res: ArrayList<ArrayList<Int>>
) {
    // 当 target 为 0，则当前路径满足条件，记录下来
    if (target == 0) {
        res.add(ArrayList(path))
        return
    }
    for (i in begin until candidates.size) {
        // 大剪枝：target - candidates[i] 为负，则后续不满足要求
        if (target - candidates[i] < 0) {
            break
        }
        // 小剪枝：去重
        //    例1              例2
        //     1                1
        //    / \              /
        //   2   2            2
        //  /     \          /
        // 5       5        2
        // 在一个 for 循环里所有遍历的数属于同一个层级（注意重点理解，add 之后回溯回来又 remove 了）
        // 例1不合法，例2合法，而 candidates[i] == candidates[i - 1] 会同时去掉例1、例2
        // 故用 i > begin 来保留例2的合法情况
        if (i > begin && candidates[i] == candidates[i - 1]) {
            continue
        }
        path.add(candidates[i])
        dfsII(candidates, target - candidates[i], i + 1, path, res)
        path.removeAt(path.size - 1)
    }
}