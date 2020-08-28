package com.wf.leetcode.daily_02

/**
 * leetcode -> com.wf.leetcode.daily_02 -> `12、交错字符串`
 *
 * @Author: wf-pc
 * @Date: 2020-07-18 10:43
 * ------------------------
 * 97题：https://leetcode-cn.com/problems/interleaving-string/
 * 给定三个字符串 s1, s2, s3, 验证 s3 是否是由 s1 和 s2 交错组成的。
 *
 * 示例 1:
 * 输入: s1 = "aabcc", s2 = "dbbca", s3 = "aadbbcbcac"
 * 输出: true
 *
 * 示例 2:
 * 输入: s1 = "aabcc", s2 = "dbbca", s3 = "aadbbbaccc"
 * 输出: false
 */
fun main() {
    val s1 = "aabcc"
    val s2 = "dbbca"
    val s3 = "aadbbcbcac"
    println(isInterleave(s1, s2, s3))
    println(isInterleave2(s1, s2, s3))

    val s4 = "aabcc"
    val s5 = "dbbca"
    val s6 = "aadbbbaccc"
    println(isInterleave(s4, s5, s6))
    println(isInterleave2(s4, s5, s6))

    val s7 = "a"
    val s8 = ""
    val s9 = "a"
    println(isInterleave(s7, s8, s9))
    println(isInterleave2(s7, s8, s9))
}

/**
 * 动态规划
 * 1、如果 s1.length + s2.length != s3.length，则必然返回 false
 * 2、如果 s1.length + s2.length == s3.length 的情况下：
 *     （1）令 f(i,j)表示 s1 的前 i 个元素和 s2 的前 j 个元素是否能交错组成 s3 的前 i+j 个元素
 *     （2）如果 s1 的第 i 个元素和 s3 的第 i+j 个元素相等，那么 f(i,j) 取决于 f(i-1,j) 是否为真
 *     （3）同理，如果 s2 的第 j 个元素和 s3 的第 i+j 个元素相等，那么 f(i,j) 取决于 f(i,j-1) 是否为真
 *     （4）于是就有了下面这个推导公式
 *         f(i,j) = [f(i−1,j) and s1(i−1) == s3(p)] or [f(i,j−1) and s2(j−1) == s3(p)]
 *         其中 p = i + j - 1，边界条件为 f(0,0)=True，注意 f 的 i、j 分别对应到 s 的 i-1、j-1
 *
 * 时间复杂度：O(nm)
 * 空间复杂度：O(nm)
 */
fun isInterleave(s1: String, s2: String, s3: String): Boolean {
    if (s1.length + s2.length != s3.length) {
        return false
    }
    val f = Array(s1.length + 1) { BooleanArray(s2.length + 1) }
    f[0][0] = true
    for (i in 0..s1.length) {
        for (j in 0..s2.length) {
            val p = i + j - 1
            if (i > 0) {
                f[i][j] = /*f[i][j] ||*/ (f[i - 1][j] && s1[i - 1] == s3[p])
            }
            if (j > 0) {
                f[i][j] = f[i][j] || (f[i][j - 1] && s2[j - 1] == s3[p])
            }
        }
    }
    return f[s1.length][s2.length]
}

/**
 * 滚动数组优化
 *
 * 时间复杂度：O(nm)
 * 空间复杂度：O(m)
 */
fun isInterleave2(s1: String, s2: String, s3: String): Boolean {
    if (s1.length + s2.length != s3.length) {
        return false
    }
    val f = BooleanArray(s2.length + 1)
    f[0] = true
    for (i in 0..s1.length) {
        for (j in 0..s2.length) {
            val p = i + j - 1
            if (i > 0) {
                f[j] = f[j] && s1[i - 1] == s3[p]
            }
            if (j > 0) {
                f[j] = f[j] || (f[j - 1] && s2[j - 1] == s3[p])
            }
        }
    }
    return f[s2.length]
}