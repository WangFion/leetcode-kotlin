package com.wf.leetcode.daily_03

import kotlin.math.min

/**
 * leetcode -> com.wf.leetcode.daily_03 -> `26、回文子串`
 *
 * @Author: wf-pc
 * @Date: 2020-08-19 16:07
 * -------------------------
 * 647题：https://leetcode-cn.com/problems/palindromic-substrings/
 * 给定一个字符串，你的任务是计算这个字符串中有多少个回文子串。
 * 具有不同开始位置或结束位置的子串，即使是由相同的字符组成，也会被视作不同的子串。
 *
 * 示例 1：
 * 输入："abc"
 * 输出：3
 * 解释：三个回文子串: "a", "b", "c"
 *
 * 示例 2：
 * 输入："aaa"
 * 输出：6
 * 解释：6个回文子串: "a", "a", "a", "aa", "aa", "aaa"
 *
 * 提示：
 * 输入的字符串长度不会超过 1000 。
 */
fun main() {
    val str1 = "abc"
    val str2 = "aaa"
    println(countSubstrings(str1))
    println(countSubstrings(str2))

    println(countSubstrings2(str1))
    println(countSubstrings2(str2))

    println(countSubstrings3(str1))
    println(countSubstrings3(str2))
}

/**
 * plan1：暴力循环
 * 暴力循环找出所有字串，然后判断字串是否是回文.
 *
 * 时间复杂度：O(n^3)，循环时间复杂度O(n^2)，回文判断O(n)。
 * 空间复杂度：O(1)。
 */
fun countSubstrings(s: String): Int {
    var count = 0
    for (i in s.indices) {
        for (j in i until s.length) {
            if (isPalindrome(s.substring(i..j))) {
                count++
            }
        }
    }
    return count
}

fun isPalindrome(str: String): Boolean {
    val tmp = str.reversed()
    return str == tmp
}

/**
 * plan2：中心拓展
 * 计算有多少个回文子串的最朴素方法就是枚举出所有的回文子串，而枚举出所有的回文字串又有两种思路，分别是：
 *     1、枚举出所有的子串，然后再判断这些子串是否是回文；
 *     2、枚举每一个可能的回文中心，然后用两个指针分别向左右两边拓展，当两个指针指向的元素相同的时候就拓展，否则停止拓展。
 * 需要考虑回文长度是奇数和回文长度是偶数的两种情况。如果回文长度是奇数，那么回文中心是一个字符；如果回文长度是偶数，那么中心是两个字符。
 * 举例当 n = 4 时，有如下回文中心点：
 *     编号    左起始点    右起始点
 *     0       0          0
 *     1       0          1
 *     2       1          1
 *     3       1          2
 *     4       2          2
 *     5       2          3
 *     6       3          3
 * 不难发现回文中心点的个数为 2n - 1个。
 *
 * 时间复杂度：O(n^2)。
 * 空间复杂度：O(1)。
 */
fun countSubstrings2(s: String): Int {
    var count = 0
    for (i in 0..2 * s.length - 2) {
        var l = i / 2
        var r = i / 2 + i % 2
        while (l >= 0 && r < s.length && s[l] == s[r]) {
            l--
            r++
            count++
        }
    }
    return count
}

/**
 * plan3：Manacher算法
 * https://www.cnblogs.com/cloudplankroader/p/10988844.html
 *
 * 时间复杂度：O(n)。即 Manacher 算法的时间复杂度，由于最大回文右端点 r 只会增加而不会减少，
 *            故中心拓展进行的次数最多为 O(n)，此外我们只会遍历字符串一次，故总复杂度为 O(n)。
 * 空间复杂度：O(n)。
 */
fun countSubstrings3(s: String): Int {
    val t = StringBuffer("@#")
    for (i in s.indices) {
        t.append(s[i])
        t.append('#')
    }
    val n = t.length
    t.append('!');

    val f = IntArray(n)
    var iMax = 0
    var rMax = 0
    var ans = 0;
    for (i in 1 until n) {
        // 初始化 f[i]
        f[i] = if (i <= rMax) min(rMax - i + 1, f[2 * iMax - i]) else 1
        // 中心拓展
        while (t[i + f[i]] == t[i - f[i]]) {
            ++f[i];
        }
        // 动态维护 iMax 和 rMax
        if (i + f[i] - 1 > rMax) {
            iMax = i;
            rMax = i + f[i] - 1;
        }
        // 统计答案, 当前贡献为 (f[i] - 1) / 2 上取整
        ans += f[i] / 2;
    }
    return ans;
}

