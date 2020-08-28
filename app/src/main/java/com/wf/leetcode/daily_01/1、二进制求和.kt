package com.wf.leetcode.daily_01

import java.math.BigInteger

/**
 * leetcode -> com.wf.leetcode.daily_01 -> `1、二进制求和`
 *
 * @Author: wf-pc
 * @Date: 2020-06-23 09:07
 * ------------------------------
 * 给你两个二进制字符串，返回它们的和（用二进制表示）。
 * 输入为 非空 字符串且只包含数字 1 和 0。
 *
 * 示例 1:
 * 输入: a = "11", b = "1"
 * 输出: "100"
 *
 * 示例 2:
 * 输入: a = "1010", b = "1011"
 * 输出: "10101"
 *
 * 提示：
 * 每个字符串仅由字符 '0' 或 '1' 组成。
 * 1 <= a.length, b.length <= 10^4
 * 字符串如果不是 "0" ，就都不含前导零。
 */

@ExperimentalStdlibApi
fun main() {
    val a = "11"
    val b = "1"
    println(sumBin(a, b))
    println(sumBin2(a, b))
    println(sumBin3(a, b))
    val a2 = "1010"
    val b2 = "1011"
    println(sumBin(a2, b2))
    println(sumBin2(a2, b2))
    println(sumBin3(a2, b2))
    /*println(0.toBinaryString())
    println(1.toBinaryString())
    println(2.toBinaryString())*/
}

/**
 * 利用java数据类型提供的接口，数字范围有限，容易溢出
 */
fun sumBin(bin1: String, bin2: String): String {
    val x1 = Integer.parseInt(bin1, 2)
    val x2 = Integer.parseInt(bin2, 2)
    return Integer.toBinaryString(x1 + x2)
}

/**
 * 扩展BigInteger用递归来实现，同样的范围有限，容易溢出
 */
fun sumBin2(bin1: String, bin2: String): String {
    val x1 = bin1.toBigInteger(2)
    val x2 = bin2.toBigInteger(2)
    return (x1 + x2).toBinaryString()
}

fun BigInteger.toBinaryString(): String {
    return when (this) {
        0.toBigInteger() -> {
            "0"
        }
        1.toBigInteger() -> {
            "1"
        }
        else -> {
            (this / 2.toBigInteger()).toBinaryString() + this % 2.toBigInteger()
        }
    }
}

/**
 * 模拟进位算法，逢二进一
 */
fun sumBin3(bin1: String, bin2: String): String {
    val result = StringBuilder()
    var carry = 0
    for (index in 0 until bin1.length.coerceAtLeast(bin2.length)) {
        carry += if (index < bin1.length) bin1[bin1.length - 1 - index] - '0' else 0
        carry += if (index < bin2.length) bin2[bin2.length - 1 - index] - '0' else 0
        result.insert(0, carry % 2)
        carry /= 2
    }
    if (carry > 0) {
        result.insert(0, 1)
    }
    return result.toString()
}