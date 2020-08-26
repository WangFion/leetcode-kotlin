package com.wf.leetcode.daily

import java.util.*

/**
 * leetcode -> com.wf.leetcode.daily -> `27、重复的子字符串`
 *
 * @Author: wf-pc
 * @Date: 2020-08-24 09:33
 * -------------------------
 * 459题：https://leetcode-cn.com/problems/repeated-substring-pattern/
 * 给定一个非空的字符串，判断它是否可以由它的一个子串重复多次构成。给定的字符串只含有小写英文字母，并且长度不超过10000。
 *
 * 示例 1:
 * 输入: "abab"
 * 输出: True
 * 解释: 可由子字符串 "ab" 重复两次构成。
 *
 * 示例 2:
 * 输入: "aba"
 * 输出: False
 *
 * 示例 3:
 * 输入: "abcabcabcabc"
 * 输出: True
 * 解释: 可由子字符串 "abc" 重复四次构成。 (或者子字符串 "abcabc" 重复两次构成。)
 */
fun main() {
    val s1 = "abab"
    val s2 = "aba"
    val s3 = "abcabcabcabc"

    println(repeatedSubstringPattern(s1))
    println(repeatedSubstringPattern(s2))
    println(repeatedSubstringPattern(s3))
    println()

    println(repeatedSubstringPattern2(s1))
    println(repeatedSubstringPattern2(s2))
    println(repeatedSubstringPattern2(s3))
    println()

    println(repeatedSubstringPattern3(s1))
    println(repeatedSubstringPattern3(s2))
    println(repeatedSubstringPattern3(s3))
    println()

    println(repeatedSubstringPattern4(s1))
    println(repeatedSubstringPattern4(s2))
    println(repeatedSubstringPattern4(s3))
    println()
}

/**
 * plan1：消消乐玩法
 * 注意到一个小优化是，因为子串至少需要重复一次，所以我们只需要在 [1,n/2] 的范围内枚举即可。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 */
fun repeatedSubstringPattern(s: String): Boolean {
    for (i in 1..s.length / 2) {
        if (s.length % i != 0) {
            continue
        }
        var tmp = s
        val key = s.substring(0, i)
        tmp = tmp.replace(key, "")
        if (tmp.isEmpty()) {
            return true
        }
    }
    return false
}

/**
 * plan2：枚举法
 *
 * 时间复杂度：O(n^2)，其中 n 是字符串 s 的长度。枚举 i 的时间复杂度为 O(n)，遍历 s 的时间复杂度为 O(n)，相乘即为总时间复杂度。
 * 空间复杂度：O(1)。
 */
fun repeatedSubstringPattern2(s: String): Boolean {
    for (i in 1..s.length / 2) {
        if (s.length % i != 0) {
            continue
        }
        var j = i
        while (j < s.length) {
            if (s[j] != s[j - i]) {
                break
            }
            j++
        }
        if (j == s.length) {
            return true
        }
    }
    return false
}

/**
 * plan3：双倍字符串解法
 * 1、假设 s 不是重复字串组成，那么 s+s 从位置 1 开始查找 s, 起点必然在第二个 s 的起始位置，即 s.length
 * 2、假设 s 由 n 个 s' 组成（s=s's'...s'），那么 s+s 从位置 1 开始查找 s, 起点必然小于 s.length
 *
 */
fun repeatedSubstringPattern3(s: String): Boolean {
    return (s + s).indexOf(s, 1) != s.length
}

/**
 * plan4：KMP算法
 * https://www.zhihu.com/question/21923021
 * https://www.cnblogs.com/yjiyjige/p/3263858.html
 *
 * 时间复杂度：O(n)，其中 n 是字符串 s 的长度。
 * 空间复杂度：O(n)。
 */
fun repeatedSubstringPattern4(s: String): Boolean {
    return kmp(s + s, s)
}

fun kmp(query: String, pattern: String): Boolean {
    val n = query.length
    val m = pattern.length
    val fail = IntArray(m)
    Arrays.fill(fail, -1)
    for (i in 1 until m) {
        var j = fail[i - 1]
        while (j != -1 && pattern[j + 1] != pattern[i]) {
            j = fail[j]
        }
        if (pattern[j + 1] == pattern[i]) {
            fail[i] = j + 1
        }
    }
    var match = -1
    for (i in 1 until n - 1) {
        while (match != -1 && pattern[match + 1] != query[i]) {
            match = fail[match]
        }
        if (pattern[match + 1] == query[i]) {
            ++match
            if (match == m - 1) {
                return true
            }
        }
    }
    return false
}