package com.wf.leetcode.daily_03

import java.util.*

/**
 * leetcode -> com.wf.leetcode.daily_03 -> `22、有效的括号`
 *
 * @Author: wf-pc
 * @Date: 2020-08-14 09:08
 * -------------------------
 * 20题：https://leetcode-cn.com/problems/valid-parentheses/
 * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效。
 * 有效字符串需满足：
 *     1、左括号必须用相同类型的右括号闭合。
 *     2、左括号必须以正确的顺序闭合。
 *     3、注意空字符串可被认为是有效字符串。
 *
 * 示例 1:
 * 输入: "()"
 * 输出: true
 *
 * 示例 2:
 * 输入: "()[]{}"
 * 输出: true
 *
 * 示例 3:
 * 输入: "(]"
 * 输出: false
 *
 * 示例 4:
 * 输入: "([)]"
 * 输出: false
 *
 * 示例 5:
 * 输入: "{[]}"
 * 输出: true
 */
fun main() {
    val s1 = "()"
    println(isValid(s1))
    println(isValid2(s1))

    val s2 = "()[]{}"
    println(isValid(s2))
    println(isValid2(s2))

    val s3 = "(]"
    println(isValid(s3))
    println(isValid2(s3))

    val s4 = "([)]"
    println(isValid(s4))
    println(isValid2(s4))

    val s5 = "{[]}"
    println(isValid(s5))
    println(isValid2(s5))

    val s6 = ")("
    println(isValid(s6))
    println(isValid2(s6))
}

/**
 * plan1；栈
 * 时间复杂度：O(n)，其中 n 是字符串 s 的长度。
 * 空间复杂度：O(n+∣Σ∣)，其中 Σ 表示字符集，本题中字符串只包含 6 种括号，∣Σ∣=6。栈中的字符数量为 O(n)，
 *           而哈希映射使用的空间为 O(∣Σ∣)，相加即可得到总空间复杂度。
 */
fun isValid(s: String): Boolean {
    if (s.length % 2 != 0) {
        return false
    }
    val map = mapOf('(' to ')', '[' to ']', '{' to '}')
    val stack = Stack<Char>()
    for (c in s) {
        if (map.keys.contains(c)) {
            stack.push(c)
        } else {
            if (stack.isNotEmpty() && c == map[stack.peek()]) {
                stack.pop()
            } else {
                return false
            }
        }
    }
    return stack.isEmpty()
}

/**
 * plan2：消消乐解法
 * 时间复杂度：O(n/2)，其中 n 是字符串 s 的长度。
 * 空间复杂度：O(1)。
 */
fun isValid2(s: String): Boolean {
    if (s.length % 2 != 0) {
        return false
    }
    var str = s
    while (str.contains("()")
        || str.contains("[]")
        || str.contains("{}")
    ) {
        str = str.replace("()", "")
            .replace("[]", "")
            .replace("{}", "")
    }
    return str.isEmpty()
}