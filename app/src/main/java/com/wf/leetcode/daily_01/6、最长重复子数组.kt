package com.wf.leetcode.daily_01

import kotlin.math.max

/**
 * leetcode -> com.wf.leetcode.daily_01 -> `6、最长重复子数组`
 *
 * @Author: wf-pc
 * @Date: 2020-07-01 10:15
 * -------------------------
 *
 * 718题：https://leetcode-cn.com/problems/maximum-length-of-repeated-subarray/
 * 给两个整数数组 A 和 B ，返回两个数组中公共的、长度最长的子数组的长度。
 *
 * 示例 1:
 * 输入:
 * A: [1,2,3,2,1]
 * B: [3,2,1,4,7]
 * 输出: 3
 * 解释:
 * 长度最长的公共子数组是 [3, 2, 1]。
 *
 * 说明:
 * 1 <= len(A), len(B) <= 1000
 * 0 <= A[i], B[i] < 100
 */
fun main() {
    val a = intArrayOf(1, 2, 3, 2, 1)
    val b = intArrayOf(3, 2, 1, 4, 7)
    println(findLength(a, b))
    println(findLength2(a, b))
    println(findLength3(a, b))
}

/**
 * plan1:暴力法
 *
 * 时间复杂度：O(n^3)
 * 空间复杂度：O(1)
 */
fun findLength(A: IntArray, B: IntArray): Int {
    var result = 0
    for (i in A.indices) {
        for (j in B.indices) {
            var k = 0
            while (i + k in A.indices && j + k in B.indices && A[i + k] == B[j + k]) {
                k++
            }
            result = max(result, k)
        }
    }
    return result
}

/**
 * plan2:动态规划（app/src/main/img/动态规划实例图.png）
 * 时间复杂度：O(N×M)。
 * 空间复杂度：O(N×M)。
 *
 * 单看 A 、B数组的最后一项
 *    -> 如果它们俩不一样，公共子序列不包括它们俩 —— 以它们为末尾项形成不了公共子序列：dp[i][j] = 0
 *    -> 如果他们俩一样，以它们俩为末尾项的公共子序列，长度至少为 1  —— dp[i][j] 至少为 1，
 *
 * 考虑它们俩前面的序列【能为它们俩提供多大的公共长度】 —— dp[i-1][j-1]
 *    -> 它们俩的前缀序列的【最后一项】不相同，即它们的前一项不相同，前缀序列提供的公共长度 为 0 —— dp[i-1][j-1] = 0
 *       以它们俩为末尾项的公共子序列的长度 = 0 + 1 = 1 —— dp[i][j] = 0 + 1 = 1
 *    -> 如果它们俩的前缀序列的【最后一项】相同，前缀部分能提供的公共长度 —— dp[i-1][j-1]，至少为 1
 *       加上它们俩本身的长度 1 ，就是以它们俩为末尾项的公共子序列的长度 —— dp[i][j] = dp[i-1][j-1] + 1
 */
fun findLength2(A: IntArray, B: IntArray): Int {
    var result = 0
    val n = A.size
    val m = B.size
    val dp = Array(n + 1) { Array(m + 1) { 0 } }
    for (i in 1..n) {
        for (j in 1..m) {
            dp[i][j] = if (A[i - 1] == B[j - 1]) dp[i - 1][j - 1] + 1 else 0
            result = max(result, dp[i][j])
        }
    }
    return result
}

/**
 * plan3:滑动窗口
 * 时间复杂度：O((N+M)×min(N,M))。
 * 空间复杂度：O(1)。
 *
 * A: [1,2,3]        [1,2,3]      [1,2,3]      [1,2,3]        [1,2,3]
 * B:     [2,3,1]      [2,3,1]    [2,3,1]    [2,3,1]      [2,3,1]
 */
fun findLength3(A: IntArray, B: IntArray): Int {
    var result = 0
    val total = A.size + B.size - 1
    var aStart: Int
    var bStart: Int
    var len: Int
    for (i in 0..total) {
        if (i < A.size) {
            aStart = A.size - 1 - i
            bStart = 0
            len = i + 1
        } else {
            aStart = 0
            bStart = i - A.size + 1
            len = total - i
        }
        result = max(result, maxLength(A, B, aStart, bStart, len))
    }
    return result
}

fun maxLength(A: IntArray, B: IntArray, aStart: Int, bStart: Int, len: Int): Int {
    var result = 0
    var count = 0
    for (i in 0 until len) {
        if (A[aStart + i] == B[bStart + i]) {
            count++
            result = max(result, count)
        } else {
            count = 0
        }
    }
    return result
}
