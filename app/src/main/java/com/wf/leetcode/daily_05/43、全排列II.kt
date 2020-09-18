package com.wf.leetcode.daily_05

import java.util.*
import kotlin.collections.ArrayList

/**
 * leetcode -> com.wf.leetcode.daily_05 -> `43、全排列II`
 *
 * @Author: wf-pc
 * @Date: 2020-09-18 09:08
 * -------------------------
 *
 * 47题
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/permutations-ii
 * 难度中等404收藏分享切换为英文关注反馈给定一个可包含重复数字的序列，返回所有不重复的全排列。
 *
 * 示例:
 * 输入: [1,1,2]
 * 输出:
 * [
 *   [1,1,2],
 *   [1,2,1],
 *   [2,1,1]
 * ]
 */
fun main() {
    val nums = intArrayOf(1, 1, 2)
    for (list in permuteUnique(nums)) {
        println(list.toTypedArray().contentToString())
    }
}

/**
 * plan1：回溯 + 剪枝
 *
 * 我们定义递归函数 dfs，那么整个递归函数分为两个情况：
 * （1）如果 index==n，说明我们已经填完了 n 个位置，找到了一个可行的解，我们将 path 放入答案数组中，递归结束。
 * （2）如果 index<n，我们要考虑第 index 个位置填哪个数。根据题目要求我们肯定不能填已经填过的数，因此很容易想
 *      到的一个处理手段是我们定义一个标记数组 used 来标记已经填过的数，那么在填第 index 个数的时候我们遍历题
 *      目给定的 n 个数，如果这个数没有被标记过，我们就尝试填入，并将其标记，继续尝试填下一个位置，即调用函数
 *      dfs。搜索回溯的时候要撤销该个位置填的数以及标记，并继续尝试其他没被标记过的数。
 *
 * 但题目解到这里并没有满足「全排列不重复」 的要求，在上述的递归函数中我们会生成大量重复的排列，因为对于第
 * index 的位置，如果存在重复的数字 i，我们每次会将重复的数字都重新填上去并继续尝试导致最后答案的重复，因此
 * 我们需要处理这个情况。要解决重复问题，我们只要设定一个规则，保证在填第 index 个数的时候重复数字只会被填入
 * 一次即可。而在本题解中，我们选择对原数组排序，保证相同的数字都相邻，然后每次填入的数一定是这个数所在重复数
 * 集合中「从左往右第一个未被填过的数字」，即如下的判断条件：
 *     if (i > 0 && nums[i] == nums[i - 1] && !vis[i - 1]) {
 *         continue
 *     }
 * 这个判断条件保证了对于重复数的集合，一定是从左往右逐个填入的。
 * 假设我们有 3 个重复数排完序后相邻，那么我们一定保证每次都是拿从左往右第一个未被填过的数字，即整个数组的状态
 * 其实是保证了 [未填入，未填入，未填入] 到 [填入，未填入，未填入]，再到 [填入，填入，未填入]，最后到 [填入，
 * 填入，填入] 的过程的，因此可以达到去重的目标。
 *
 * 时间复杂度：O(n×n!)，其中 n 为序列的长度。
 * 空间复杂度：O(n)。我们需要 O(n) 的标记数组，同时在递归的时候栈深度会达到 O(n)，因此总空间复杂度为 O(n+n)=O(2n)=O(n)。
 */
fun permuteUnique(nums: IntArray): List<List<Int>> {
    val res = ArrayList<ArrayList<Int>>()
    val path = ArrayList<Int>()
    val used = BooleanArray(nums.size)
    //排序是剪枝的前途
    Arrays.sort(nums)
    dfs(nums, res, path, used, 0)
    return res
}

fun dfs(
    nums: IntArray,
    res: ArrayList<ArrayList<Int>>,
    path: ArrayList<Int>,
    used: BooleanArray,
    index: Int
) {
    if (index == nums.size) {
        res.add(ArrayList(path))
        return
    }
    for (i in nums.indices) {
        //剪枝
        if (used[i]) {
            continue
        }
        // 剪枝条件：i > 0 是为了保证 nums[i - 1] 有意义
        // 写 !used[i - 1] 是因为 nums[i - 1] 在深度优先遍历的过程中刚刚被撤销选择
        if (i > 0 && nums[i] == nums[i - 1] && !used[i - 1]) {
            continue
        }

        //取值
        path.add(nums[i])
        used[i] = true

        //递归
        dfs(nums, res, path, used, index + 1)

        //回溯
        path.removeAt(path.size - 1)
        used[i] = false
    }
}