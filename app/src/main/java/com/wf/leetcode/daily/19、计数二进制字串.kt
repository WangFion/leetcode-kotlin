package com.wf.leetcode.daily

import kotlin.math.min

/**
 * leetcode -> com.wf.leetcode.daily -> `19、计数二进制字串`
 *
 * @Author: wf-pc
 * @Date: 2020-08-10 09:03
 * -------------------------
 * 696题：https://leetcode-cn.com/problems/count-binary-substrings/
 * 给定一个字符串 s，计算具有相同数量0和1的非空(连续)子字符串的数量，并且这些子字符串中的所有0和所有1都是组合在一起的。
 * 重复出现的子串要计算它们出现的次数。
 *
 * 示例 1 :
 * 输入: "00110011"
 * 输出: 6
 * 解释: 有6个子串具有相同数量的连续1和0：“0011”，“01”，“1100”，“10”，“0011” 和 “01”。
 * 请注意，一些重复出现的子串要计算它们出现的次数。
 * 另外，“00110011”不是有效的子串，因为所有的0（和1）没有组合在一起。
 *
 * 示例 2 :
 * 输入: "10101"
 * 输出: 4
 * 解释: 有4个子串：“10”，“01”，“10”，“01”，它们具有相同数量的连续1和0。
 *
 * 注意：
 * s.length 在1到50,000之间。
 * s 只包含“0”或“1”字符。
 */
fun main() {
    val s1 = "00110011"
    println(countBinarySubstrings(s1))
    println(countBinarySubstrings2(s1))

    val s2 = "10101"
    println(countBinarySubstrings(s2))
    println(countBinarySubstrings2(s2))
}

/**
 * 我们可以将字符串 s 按照 0 和 1 的连续段分组，存在 counts 数组中，例如 s=00111011，可以得到数组：counts={2,3,1,2}。
 * 这里 counts 数组中两个相邻的数一定代表的是两种不同的字符。假设 counts 数组中两个相邻的数字为 u 或者 v，
 * 它们对应着 u 个 0 和 v 个 1，或者 u 个 1 和 v 个 0。它们能组成的满足条件的子串数目为 min{u,v}，即一对相邻的数字对答案的贡献。
 * 我们只要遍历所有相邻的数对，求它们的贡献总和，即可得到答案。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 */
fun countBinarySubstrings(s: String): Int {
    val counts = mutableListOf<Int>()
    var ptr = 0
    var count = 0
    while (ptr < s.length) {
        val char = s[ptr]
        while (ptr < s.length && s[ptr] == char) {
            ptr++
            count++
        }
        counts.add(count)
        count = 0
    }
    var result = 0
    for (i in 0..counts.size - 2) {
        result += min(counts[i], counts[i + 1])
    }
    return result
}

/**
 * 空间优化
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 */
fun countBinarySubstrings2(s: String): Int {
    var result = 0
    var ptr = 0
    var count = 0
    var last = 0
    while (ptr < s.length) {
        val char = s[ptr]
        while (ptr < s.length && s[ptr] == char) {
            ptr++
            count++
        }
        result += min(count, last)
        last = count
        count = 0
    }
    return result
}
 