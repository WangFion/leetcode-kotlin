package com.wf.leetcode.sort

import java.math.BigInteger

/**
 * leetcode -> com.wf.leetcode.sort -> 4、中文转阿拉伯数字
 *
 * @Author: wf-pc
 * @Date: 2020-08-14 16:54
 */
object ChineseArabic {
    private val numbers = mapOf(
        '零' to 0L, '一' to 1L,
        '壹' to 1L, '二' to 2L,
        '贰' to 2L, '两' to 2L,
        '三' to 3L, '叁' to 3L,
        '四' to 4L, '肆' to 4L,
        '五' to 5L, '伍' to 5L,
        '六' to 6L, '陆' to 6L,
        '七' to 7L, '柒' to 7L,
        '八' to 8L, '捌' to 8L,
        '九' to 9L, '玖' to 9L
    )

    private val units = mapOf(
        '个' to 1L, '個' to 1L,
        '十' to 10L, '拾' to 10L,
        '百' to 100L, '佰' to 100L,
        '千' to 1000L, '仟' to 1000L,
        '万' to 10000L, '萬' to 10000L,
        '亿' to 100000000L, '億' to 100000000L
    )

    /**
     * 将亿以下的中文数字字符串转换为阿拉伯数字
     */
    private fun convertToArabic(number: String): BigInteger {
        var arabic = BigInteger.valueOf(0)
        //var num = BigInteger.valueOf(0)
        var unit = BigInteger.valueOf(1)
        var carry = BigInteger.valueOf(1)
        for (c in number.reversed()) {
            //计算单位，如果下一个单位比当前单位小，则需要乘以进位单位
            if (units.keys.contains(c)) {
                val tmp = BigInteger.valueOf(units[c] ?: 1)
                if (tmp < unit) {
                    unit = carry.multiply(tmp)
                } else {
                    unit = tmp
                    carry = tmp
                }
            }
            //x = x + y * 单位
            if (numbers.keys.contains(c)) {
                val num = BigInteger.valueOf(numbers[c] ?: 0)
                arabic = arabic.add(num.multiply(unit))
            }
        }
        return arabic
    }

    /**
     * 判断是否为标准的中文数字字符串
     */
    fun isArabic(number: String): Boolean {
        if (units.keys.contains(number[0])) {
            return false
        }
        for (c in number) {
            if (!numbers.keys.contains(c) && !units.keys.contains(c)) {
                return false
            }
        }
        return true
    }

    /**
     * 将中文数字字符串转换为阿拉伯数字
     */
    fun toArabic(number: String): BigInteger {
        if (!isArabic(number)) {
            throw NumberFormatException("Parameter is not a string of Arabic numerals !")
        }
        return when {
            number.contains("亿") -> {
                val list = number.split("亿")
                convertToArabic(list[0])
                    .multiply(BigInteger.valueOf(100000000))
                    .add(convertToArabic(list[1]))
            }
            number.contains("億") -> {
                val list = number.split("億")
                convertToArabic(list[0])
                    .multiply(BigInteger.valueOf(100000000))
                    .add(convertToArabic(list[1]))
            }
            else -> {
                convertToArabic(number)
            }
        }
    }
}

fun String.toArabic(): BigInteger {
    return ChineseArabic.toArabic(this)
}

fun main() {
    println("零".toArabic())
    println("一".toArabic())
    println("二十".toArabic())
    println("伍拾八".toArabic())
    println("一百零六".toArabic())
    println("五千三百四十一".toArabic())
    println("一亿零四".toArabic())
    println("一亿二千万".toArabic())
    //120000000
    println("二千三百四十五亿一千二百零四万一千零三十四".toArabic())
    println("二千三百四十五亿一千二百三十四万一千二百三十四".toArabic())
    println("一万二千三百四十五亿一千二百三十四万一千二百三十四".toArabic())
    //1234512341234
    println("一千二百三十四万一千二百三十四亿一千二百三十四万一千二百三十四".toArabic())
    //1234123412341234


    println(ChineseArabic.toArabic("一千二百三十四万一千二百三十四亿一千二百三十四万一千二百三十四"))
}