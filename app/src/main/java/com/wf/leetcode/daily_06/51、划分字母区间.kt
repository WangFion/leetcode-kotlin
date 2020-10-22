package com.wf.leetcode.daily_06

import kotlin.math.max

/**
 * leetcode -> com.wf.leetcode.daily_06 -> `51、划分字母区间`
 *
 * @Author: wf-pc
 * @Date: 2020-10-22 09:42
 * -------------------------
 * 763题
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/partition-labels
 * 字符串 S 由小写字母组成。我们要把这个字符串划分为尽可能多的片段，同一个字母只会出现在其中的一个片段。
 * 返回一个表示每个字符串片段的长度的列表。
 *
 * 示例 1：
 * 输入：S = "ababcbacadefegdehijhklij"
 * 输出：[9,7,8]
 * 解释：
 * 划分结果为 "ababcbaca", "defegde", "hijhklij"。
 * 每个字母最多出现在一个片段中。
 * 像 "ababcbacadefegde", "hijhklij" 的划分是错误的，因为划分的片段数较少。
 *
 * 提示：
 * S的长度在[1, 500]之间。
 * S只包含小写字母 'a' 到 'z' 。
 */
fun main() {
    val S = "ababcbacadefegdehijhklij"
    println(partitionLabels(S).toTypedArray().contentToString())
}

/**
 * plan1：贪心算法 + 双指针
 * 第一遍遍历先找出字符串中某个字符最后出现的位置
 * 第二遍遍历再求出满足条件的字符串片段长度
 *
 * 时间复杂度：O(n)，其中 n 是字符串的长度。需要遍历字符串两次。
 * 空间复杂度：O(Σ)，其中 Σ 是字符串中的字符集大小。这道题中，字符串只包含小写字母，因此 Σ=26。
 */
fun partitionLabels(S: String): List<Int> {
    val last = IntArray(26)
    for ((i, c) in S.withIndex()) {
        last[c - 'a'] = i
    }
    val result = mutableListOf<Int>()
    var start = 0
    var end = 0
    for ((i, c) in S.withIndex()) {
        end = max(end, last[c - 'a'])
        if (i == end) {
            result.add(end - start + 1)
            start = end + 1
        }
    }
    return result
}