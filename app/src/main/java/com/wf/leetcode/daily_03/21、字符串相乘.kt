package com.wf.leetcode.daily_03

/**
 * leetcode -> com.wf.leetcode.daily_03 -> `21、字符串相乘`
 *
 * @Author: wf-pc
 * @Date: 2020-08-13 09:29
 * -------------------------
 * 43题：https://leetcode-cn.com/problems/multiply-strings/
 * 给定两个以字符串形式表示的非负整数 num1 和 num2，返回 num1 和 num2 的乘积，它们的乘积也表示为字符串形式。
 *
 * 示例 1:
 * 输入: num1 = "2", num2 = "3"
 * 输出: "6"
 *
 * 示例 2:
 * 输入: num1 = "123", num2 = "456"
 * 输出: "56088"
 *
 * 说明：
 * num1 和 num2 的长度小于110。
 * num1 和 num2 只包含数字 0-9。
 * num1 和 num2 均不以零开头，除非是数字 0 本身。
 * 不能使用任何标准库的大数类型（比如 BigInteger）或直接将输入转换为整数来处理。
 */
fun main() {
    val num1 = "2"
    val num2 = "3"
    println(multiply(num1, num2))
    println(multiply2(num1, num2))
    println(multiply3(num1, num2))
    //println(addString(num1, num2))
    println()

    val num3 = "123"
    val num4 = "456"
    println(multiply(num3, num4))
    println(multiply2(num3, num4))
    println(multiply3(num3, num4))
    //println(addString(num3, num4))
    println()

    val num5 = "123456789"
    val num6 = "987654321"
    println(multiply(num5, num6))
    println(multiply2(num5, num6))
    println(multiply3(num5, num6))
    //println(addString(num5, num6))
    println()

    val num7 = "9"
    val num8 = "99"
    println(multiply(num7, num8))
    println(multiply2(num7, num8))
    println(multiply3(num7, num8))
    //println(addString(num5, num6))
    println()
}

/**
 * plan1：使用系统api
 */
fun multiply(num1: String, num2: String): String {
    return (num1.toBigDecimal() * num2.toBigDecimal()).toString()
}

/**
 * plan2：竖式计算法
 * 时间复杂度：O(mn+n^2)，其中 m 和 n 分别是 num1 和 num2 的长度。需要从右往左遍历 num2，对于 num2 的每一位，
 *           都需要和 num1 的每一位计算乘积，因此计算乘积的总次数是 mn。字符串相加操作共有 n 次，
 *           相加的字符串长度最长为 m+n，因此字符串相加的时间复杂度是 O(mn+n^2)。
 *           总时间复杂度是 O(mn+n^2)。
 * 空间复杂度：O(m+n)，其中 m 和 n 分别是 num1 和 num2 的长度。空间复杂度取决于存储中间状态的字符串，
 *           由于乘积的最大长度为 m+n，因此存储中间状态的字符串的长度不会超过 m+n。
 */
fun multiply2(num1: String, num2: String): String {
    if (num1.isEmpty() || num2.isEmpty() || num1 == "0" || num2 == "0") {
        return "0"
    }
    var ans = ""
    var x: Int
    var y: Int
    var carry = 0
    for (i in num2.length - 1 downTo 0) {
        x = num2[i] - '0'
        val curr = StringBuilder()
        //给低位补0
        var k = num2.length - 1 - i
        while (k > 0) {
            curr.append(0)
            k--
        }
        for (j in num1.length - 1 downTo 0) {
            y = num1[j] - '0'
            carry += x * y
            curr.append(carry % 10)
            carry /= 10
        }
        if (carry != 0) {
            curr.append(carry)
            carry = 0
        }
        ans = addString(ans, curr.reverse().toString())
    }
    return ans
}

fun addString(num1: String, num2: String): String {
    var i = num1.length - 1
    var j = num2.length - 1
    val ans = StringBuilder()
    var carry = 0
    while (i >= 0 || j >= 0) {
        carry += if (i >= 0) num1[i] - '0' else 0
        carry += if (j >= 0) num2[j] - '0' else 0
        ans.append(carry % 10)
        carry /= 10
        i--
        j--
    }
    if (carry != 0) {
        ans.append(carry)
    }
    return ans.reverse().toString()
}

/**
 * plan3：竖式计算法优化方案
 * plan2的做法是从右往左遍历乘数，将乘数的每一位与被乘数相乘得到对应的结果，再将每次得到的结果累加，
 * 整个过程中涉及到较多字符串相加的操作。如果使用数组代替字符串存储结果，则可以减少对字符串的操作。
 * 令 m 和 n 分别表示 num1 和 num2 的长度，并且它们均不为 0，则 num1 和 num2 的乘积的长度为 m+n−1 或 m+n。
 * 由于 num1 和 num2 的乘积的最大长度为 m+n，因此创建长度为 m+n 的数组 ansArr 用于存储乘积。
 * 对于任意 0≤i<m 和 0≤j<n，num1[i]×num2[j] 的结果位于 ansArr[i+j+1]，如果 ansArr[i+j+1]≥10，
 * 则将进位部分加到 ansArr[i+j]。最后，将数组 ansArr 转成字符串，如果最高位是 0 则舍弃最高位。
 *        2   5
 *   x    4   3
 * --------------
 *        6   15
 *   + 8  20
 * --------------
 *     8  26  15
 * --------------
 *  1  0  7   5
 *
 * 时间复杂度：O(mn)，其中 m 和 n 分别是 num1 和 num2 的长度。需要计算 num1 的每一位和 num2 的每一位的乘积。
 * 空间复杂度：O(m+n)，其中 m 和 n 分别是 num1 和 num2 的长度。需要创建一个长度为 m+n 的数组存储乘积。
 */
fun multiply3(num1: String, num2: String): String {
    if (num1.isEmpty() || num2.isEmpty() || num1 == "0" || num2 == "0") {
        return "0"
    }
    val ansArr = IntArray(num1.length + num2.length)
    //计算每位的乘积
    for (i in num1.length - 1 downTo 0) {
        val x = num1[i] - '0'
        for (j in num2.length - 1 downTo 0) {
            val y = num2[j] - '0'
            ansArr[i + j + 1] += x * y
        }
    }
    //处理进位
    for (i in ansArr.size - 1 downTo 1) {
        ansArr[i - 1] += ansArr[i] / 10
        ansArr[i] %= 10
    }
    //处理最高位
    val start = if (ansArr[0] == 0) 1 else 0
    //转化成字符串
    val ans = StringBuilder()
    for (i in start until ansArr.size) {
        ans.append(ansArr[i])
    }
    return ans.toString()
}

 