package com.wf.leetcode.daily_04

import java.util.*
import kotlin.collections.ArrayList

/**
 * leetcode -> com.wf.leetcode.daily_04 -> `37、组合总和`
 *
 * @Author: wf-pc
 * @Date: 2020-09-09 13:58
 * -------------------------
 *
 * 39题
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/combination-sum
 *
 * 给定一个无重复元素的数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。
 * candidates 中的数字可以无限制重复被选取。
 * 说明：
 * 所有数字（包括 target）都是正整数。
 * 解集不能包含重复的组合。
 *
 * 示例 1：
 * 输入：candidates = [2,3,6,7], target = 7,
 * 所求解集为：
 * [[7], [2,2,3]]
 *
 * 示例 2：
 * 输入：candidates = [2,3,5], target = 8,
 * 所求解集为：[[2,2,2,2], [2,3,3], [3,5]]
 *
 * 提示：
 * 1 <= candidates.length <= 30
 * 1 <= candidates[i] <= 200
 * candidate 中的每个元素都是独一无二的。
 * 1 <= target <= 500
 */
fun main() {
    val arr1 = intArrayOf(2, 3, 6, 7)
    val arr2 = intArrayOf(2, 3, 5)
    for (list in combinationSum(arr1, 7)) {
        println(list.toTypedArray().contentToString())
    }
    println()

    for (list in combinationSum(arr2, 8)) {
        println(list.toTypedArray().contentToString())
    }
    println()

    for (list in combinationSum2(arr1, 7)) {
        println(list.toTypedArray().contentToString())
    }
    println()

    for (list in combinationSum2(arr2, 8)) {
        println(list.toTypedArray().contentToString())
    }
}

/**
 * plan1：回溯
 *
 * 树形图：app/src/main/img/37、组合总和1.png
 */
fun combinationSum(candidates: IntArray, target: Int): List<List<Int>> {
    val res = ArrayList<ArrayList<Int>>()
    if (candidates.isEmpty()) {
        return res
    }

    val path = ArrayList<Int>()
    dfs(candidates, 0, target, path, res);
    return res
}

/**
 * @param candidates 候选数组
 * @param begin      搜索起点
 * @param target     每减去一个元素，目标值变小
 * @param path       从根结点到叶子结点的路径，是一个栈
 * @param res        结果集列表
 */
fun dfs(
    candidates: IntArray,
    begin: Int,
    target: Int,
    path: ArrayList<Int>,
    res: ArrayList<ArrayList<Int>>
) {
    // target 为负数的时候不再产生新的孩子结点
    if (target < 0) {
        return
    }
    // target 为 0 的时候得到一组解
    if (target == 0) {
        res.add(ArrayList(path))
        return
    }

    // 重点理解这里从 begin 开始搜索的语意
    for (i in begin until candidates.size) {
        path.add(candidates[i]);
        // 注意：由于每一个元素可以重复使用，下一轮搜索的起点依然是 i，这里非常容易弄错
        dfs(candidates, i, target - candidates[i], path, res)
        // 状态重置
        path.removeAt(path.size - 1)
    }
}

/**
 * plan2：回溯 + 剪枝
 *
 * 树形图：app/src/main/img/37、组合总和2.png
 */
fun combinationSum2(candidates: IntArray, target: Int): List<List<Int>> {
    val res = ArrayList<ArrayList<Int>>()
    if (candidates.isEmpty()) {
        return res
    }

    // 排序是剪枝的前提
    Arrays.sort(candidates)
    val path = ArrayList<Int>()
    dfs2(candidates, 0, target, path, res);
    return res
}

/**
 * @param candidates 候选数组
 * @param begin      搜索起点
 * @param target     每减去一个元素，目标值变小
 * @param path       从根结点到叶子结点的路径，是一个栈
 * @param res        结果集列表
 */
fun dfs2(
    candidates: IntArray,
    begin: Int,
    target: Int,
    path: ArrayList<Int>,
    res: ArrayList<ArrayList<Int>>
) {
    // 因小于 0 的情况被剪枝，故只有 target 为 0 的时候得到一组解，并终止递归
    if (target == 0) {
        res.add(ArrayList(path))
        return
    }

    // 重点理解这里从 begin 开始搜索的语意
    for (i in begin until candidates.size) {
        // 小于 0 剪枝
        if (target - candidates[i] < 0) {
            break
        }
        path.add(candidates[i]);
        // 注意：由于每一个元素可以重复使用，下一轮搜索的起点依然是 i，这里非常容易弄错
        dfs(candidates, i, target - candidates[i], path, res)
        // 状态重置
        path.removeAt(path.size - 1)
    }
}