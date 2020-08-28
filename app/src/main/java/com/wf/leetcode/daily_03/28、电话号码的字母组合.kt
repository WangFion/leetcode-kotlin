package com.wf.leetcode.daily_03

/**
 * leetcode -> com.wf.leetcode.daily_03 -> `28、电话号码的字母组合`
 *
 * @Author: wf-pc
 * @Date: 2020-08-26 09:38
 * -------------------------
 * 17题：https://leetcode-cn.com/problems/letter-combinations-of-a-phone-number/
 * 给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。
 * 给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。
 *
 * 1[!@#]   2[abc]   3[def]
 * 4[ghi]   5[jkl]   6[mno]
 * 7[pqrs]  8[tuv]   9[wxyz]
 * *[+]     0[_]     #[shift]
 *
 * 示例:
 * 输入："23"
 * 输出：["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"].
 */
fun main() {
    println(letterCombinations("23").toTypedArray().contentToString())
}

/**
 * plan1：回溯算法
 *
 * 2:     a          b          c
 *      / | \      / | \      / | \
 * 3:  d  e  f    d  e  f    d  e  f
 *     |  |  |    |  |  |    |  |  |
 * R: ad ae af   bd be bf   cd ce cf
 *
 * 时间复杂度：O(3^m x 4^n)，
 *            其中 m 是输入中对应 3 个字母的数字个数（包括数字 2、3、4、5、6、8），
 *            n 是输入中对应 4 个字母的数字个数（包括数字 7、9），
 *            m+n 是输入数字的总个数。当输入包含 m 个对应 3 个字母的数字和 n 个对应 4 个字母的数字时，
 *            不同的字母组合一共有 3^m x 4^n 种，需要遍历每一种字母组合。
 * 空间复杂度：O(m+n)，
 *            其中 m 是输入中对应 3 个字母的数字个数，n 是输入中对应 4 个字母的数字个数，
 *            m+n 是输入数字的总个数。除了返回值以外，空间复杂度主要取决于哈希表以及回溯过程中的递归调用层数，
 *            哈希表的大小与输入无关，可以看成常数，递归调用层数最大为 m+n。
 */
fun letterCombinations(digits: String): List<String> {
    val list = mutableListOf<String>()
    if (digits.isEmpty()) {
        return list
    }
    val map = mapOf(
        '2' to "abc", '3' to "def", '4' to "ghi", '5' to "jkl",
        '6' to "mno", '7' to "pqrs", '8' to "tuv", '9' to "wxyz"
    )
    backTrack(list, map, digits, 0, StringBuilder())
    return list
}

fun backTrack(
    list: MutableList<String>,
    map: Map<Char, String>,
    digits: String,
    index: Int,
    builder: StringBuilder
) {
    if (index == digits.length) {
        list.add(builder.toString())
    } else {
        val letter = map[digits[index]] ?: ""
        for (c in letter) {
            builder.append(c)
            backTrack(
                list,
                map,
                digits,
                index + 1,
                builder
            )
            //回溯返回后删除上一个字符，进行下一个字符的回溯
            builder.deleteCharAt(index)
        }
    }
}