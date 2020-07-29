package com.wf.leetcode.daily

/**
 * leetcode -> com.wf.leetcode.daily -> `17、判断子序列`
 *
 * @Author: wf-pc
 * @Date: 2020-07-27 09:23
 * -------------------------
 * 392题：https://leetcode-cn.com/problems/is-subsequence/
 * 给定字符串 s 和 t ，判断 s 是否为 t 的子序列。
 * 你可以认为 s 和 t 中仅包含英文小写字母。字符串 t 可能会很长（长度 ~= 500,000），而 s 是个短字符串（长度 <=100）。
 * 字符串的一个子序列是原始字符串删除一些（也可以不删除）字符而不改变剩余字符相对位置形成的新字符串。
 * （例如，"ace"是"abcde"的一个子序列，而"aec"不是）。
 *
 * 示例 1:
 * s = "abc", t = "ahbgdc"
 * 返回 true.
 *
 * 示例 2:
 * s = "axc", t = "ahbgdc"
 * 返回 false.
 *
 * 后续挑战 :
 * 如果有大量输入的 S，称作S1, S2, ... , Sk 其中 k >= 10亿，你需要依次检查它们是否为 T 的子序列。在这种情况下，你会怎样改变代码？
 */
fun main() {
    val s = "abc"
    val t = "ahbgdc"
    println(isSubsequence(s, t))
    println(isSubsequence2(s, t))

    val s2 = "axc"
    val t2 = "ahbgdc"
    println(isSubsequence(s2, t2))
    println(isSubsequence2(s2, t2))

    val s3 = ""
    val t3 = "ahbgdc"
    println(isSubsequence(s3, t3))
    println(isSubsequence2(s3, t3))
}

/**
 * plan1：双指针
 * 我们初始化两个指针 i 和 j，分别指向 s 和 t 的初始位置。
 * 每次贪心地匹配，匹配成功则 i 和 j 同时右移，匹配 s 的下一个位置，
 * 匹配失败则 j 右移，i 不变，尝试用 t 的下一个字符匹配 s。
 * 最终如果 i 移动到 s 的末尾，就说明 s 是 t 的子序列。
 *
 * 贪心地匹配：假定当前需要匹配字符 c，而字符 c 在 t 中的位置 x1 和 x2 出现（x1 < x2），那么贪心取 x1 是最优解，
 *           因为 x2 后面能取到的字符，x1 也都能取到，并且通过 x1 与 x2 之间的可选字符，更有希望能匹配成功。
 *
 * 时间复杂度：O(n+m)，其中 n 为 s 的长度，m 为 t 的长度。每次无论是匹配成功还是失败，
 *            都有至少一个指针发生右移，两指针能够位移的总距离为 n+m。
 * 空间复杂度：O(1)。
 */
fun isSubsequence(s: String, t: String): Boolean {
    var i = 0
    var j = 0
    while (i < s.length && j < t.length) {
        if (s[i] == t[j]) {
            i++
        }
        j++
    }
    return i == s.length
}


/**
 * plan：动态规划
 *
 * 其核心思想是根据 t 字符串构建一张 dp[下标][字符]（dp[0][a]） 的网格来存储 某个字符 在 t 字符串中的起始位置，
 * 类似于字典的形式，然后再去匹配子串 s 的每个字符是否存在这张网中，并且位置是升序的。
 *
 * 考虑前面的双指针的做法，我们注意到我们有大量的时间用于在 t 中找到下一个匹配字符。
 * 这样我们可以预处理出对于 t 的每一个位置，从该位置开始往后每一个字符第一次出现的位置。
 * 我们可以使用动态规划的方法实现预处理，令 f[i][c] 表示字符串 t 中从位置 i 开始往后字符 c 第一次出现的位置。
 * 在进行状态转移时，如果 t 中位置 i 的字符就是 c，那么 f[i][c]=i，否则 c 出现在位置 i+1 开始往后，
 * 即 f[i][c]=f[i+1][c]，因此我们要倒过来进行动态规划，从后往前枚举 i。
 * 这样我们可以写出状态转移方程：
 *     f[i][c]=i,         t[i]=c
 *     f[i][c]=f[i+1][c],​t[i]!=c​
 * 假定下标从 0 开始，那么 f[i][c] 中有 0≤i≤m−1，对于边界状态 f[m−1][..]，我们置 f[m][..] 为 m，
 * 让 f[m−1][..] 正常进行转移。这样如果 f[i][c]=m，则表示从位置 i 开始往后不存在字符 c。
 * 这样，我们可以利用 f 数组，每次 O(1) 地跳转到下一个位置，直到位置变为 m 或 s 中的每一个字符都匹配成功。
 *
 * 同时我们注意到，该解法中对 t 的处理与 s 无关，且预处理完成后，可以利用预处理数组的信息，
 * 线性地算出任意一个字符串 s 是否为 t 的子串。这样我们就可以解决「后续挑战」啦。
 *
 * 时间复杂度：O(m ×∣Σ∣+ n)，其中 n 为 s 的长度，m 为 t 的长度，Σ 为字符集，
 * 在本题中字符串只包含小写字母，∣Σ∣=26。预处理时间复杂度 O(m)，判断子序列时间复杂度 O(n)。
 *     如果是计算 k 个平均长度为 n 的字符串是否为 t 的子序列，则时间复杂度为 O(m ×∣Σ∣+ k × n)。
 * 空间复杂度：O(m ×∣Σ∣)，为动态规划数组的开销。
 */
fun isSubsequence2(s: String, t: String): Boolean {
    val n = s.length
    val m = t.length
    val dp = Array(m + 1) { IntArray(26) }
    //1、将最后一行全部初始化为m，表示 m 位置后不存在任何字符了
    for (i in 0 until 26) {
        dp[m][i] = m
    }
    //2、预处理动态规划数组
    for (i in m - 1 downTo 0) {
        for (j in 0 until 26) {
            dp[i][j] = if (t[i].toInt() == j + 'a'.toInt()) i else dp[i + 1][j]
        }
    }
    //3、匹配字串
    var index = 0
    for (i in 0 until n) {
        //说明从index开始后面找不到字符s[i]了
        if (dp[index][s[i].toInt() - 'a'.toInt()] == m) {
            return false
        }
        //查找下一个字符则从当前匹配位置的下一个位置开始
        index = dp[index][s[i].toInt() - 'a'.toInt()] + 1
    }
    return true
}