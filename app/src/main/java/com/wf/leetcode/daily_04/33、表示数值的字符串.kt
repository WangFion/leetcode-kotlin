package com.wf.leetcode.daily_04

/**
 * leetcode -> com.wf.leetcode.daily_04 -> `33、表示数值的字符串`
 *
 * @Author: wf-pc
 * @Date: 2020-09-02 09:19
 * -------------------------
 * 剑指 Offer 20，1604题
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/biao-shi-shu-zhi-de-zi-fu-chuan-lcof
 *
 * 请实现一个函数用来判断字符串是否表示数值（包括整数和小数）。例如，字符串"+100"、"5e2"、"-123"、"3.1416"、
 * "-1E-16"、"0123"都表示数值，但"12e"、"1a3.14"、"1.2.3"、"+-5"及"12e+5.4"都不是。
 */
fun main() {
    val s1 = "+100"
    val s2 = "-1E-16"
    val s3 = "1a3.14"
    val s4 = "+-5"
    println(isNumber(s1))
    println(isNumber(s2))
    println(isNumber(s3))
    println(isNumber(s4))
}

/**
 * plan1：确定有限状态自动机
 * 确定有限状态自动机（以下简称「自动机」）是一类计算模型。它包含一系列状态，这些状态中：
 *     1、有一个特殊的状态，被称作「初始状态」。
 *     2、还有一系列状态被称为「接受状态」，它们组成了一个特殊的集合。
 *     3、其中，一个状态可能既是「初始状态」，也是「接受状态」。
 *
 * 起初，这个自动机处于「初始状态」。随后，它顺序地读取字符串中的每一个字符，并根据当前状态和读入的字符，
 * 按照某个事先约定好的「转移规则」，从当前状态转移到下一个状态；当状态转移完成后，它就读取下一个字符。
 * 当字符串全部读取完毕后，如果自动机处于某个「接受状态」，则判定该字符串「被接受」；否则，判定该字符串「被拒绝」。
 *
 * 注意：如果输入的过程中某一步转移失败了，即不存在对应的「转移规则」，此时计算将提前中止。在这种情况下我们
 *      也判定该字符串「被拒绝」。
 *
 * 自动机驱动的编程，可以被看做一种暴力枚举方法的延伸：它穷尽了在任何一种情况下，对应任何的输入，需要做的事情。
 * 自动机在计算机科学领域有着广泛的应用。在算法领域，它与大名鼎鼎的字符串查找算法「KMP」算法有着密切的关联；
 * 在工程领域，它是实现「正则表达式」的基础。
 *
 * 思路与算法
 * 根据上面的描述，现在可以定义自动机的「状态集合」了。那么怎么挖掘出所有可能的状态呢？一个常用的技巧是，
 * 用「当前处理到字符串的哪个部分」当作状态的表述。根据这一技巧，不难挖掘出所有状态：
 *     1、起始的空格
 *     2、符号位
 *     3、整数部分
 *     4、左侧有整数的小数点
 *     5、左侧无整数的小数点（根据前面的第二条额外规则，需要对左侧有无整数的两种小数点做区分）
 *     6、小数部分
 *     7、字符 e
 *     8、指数部分的符号位
 *     9、指数部分的整数部分
 *     10、末尾的空格
 * 下一步是找出「初始状态」和「接受状态」的集合。根据题意，「初始状态」应当为状态 1，而「接受状态」的集合则
 * 为状态 3、状态 4、状态 6、状态 9 以及状态 10。换言之，字符串的末尾要么是空格，要么是数字，要么是小数点，
 * 但前提是小数点的前面有数字。
 * 状态转移规则如图：app/src/main/img/33、表示数值的字符串.png
 *
 * 时间复杂度：O(N)，其中 N 为字符串的长度。我们需要遍历字符串的每个字符，其中状态转移所需的时间复杂度为 O(1)。
 * 空间复杂度：O(1)。只需要创建固定大小的状态转移表。
 */
fun isNumber(s: String): Boolean {
    val transfer = buildStateRule()
    var state = State.STATE_INITIAL
    for (c in s) {
        val type = toCharType(c)
        val map = transfer[state] ?: return false
        if (!map.containsKey(type)) {
            return false
        } else {
            state = map[type] ?: error("");
        }
    }
    return state == State.STATE_INTEGER
            || state == State.STATE_POINT
            || state == State.STATE_FRACTION
            || state == State.STATE_EXP_NUMBER
            || state == State.STATE_END;
}

fun buildStateRule(): Map<State, Map<CharType, State>> {
    return mapOf(
        State.STATE_INITIAL to mapOf(
            CharType.CHAR_SPACE to State.STATE_INITIAL,
            CharType.CHAR_NUMBER to State.STATE_INTEGER,
            CharType.CHAR_POINT to State.STATE_POINT_WITHOUT_INT,
            CharType.CHAR_SIGN to State.STATE_INT_SIGN
        ),
        State.STATE_INT_SIGN to mapOf(
            CharType.CHAR_NUMBER to State.STATE_INTEGER,
            CharType.CHAR_POINT to State.STATE_POINT_WITHOUT_INT
        ),
        State.STATE_INTEGER to mapOf(
            CharType.CHAR_NUMBER to State.STATE_INTEGER,
            CharType.CHAR_EXP to State.STATE_EXP,
            CharType.CHAR_POINT to State.STATE_POINT,
            CharType.CHAR_SPACE to State.STATE_END
        ),
        State.STATE_POINT to mapOf(
            CharType.CHAR_NUMBER to State.STATE_FRACTION,
            CharType.CHAR_EXP to State.STATE_EXP,
            CharType.CHAR_SPACE to State.STATE_END
        ),
        State.STATE_POINT_WITHOUT_INT to mapOf(
            CharType.CHAR_NUMBER to State.STATE_FRACTION
        ),
        State.STATE_FRACTION to mapOf(
            CharType.CHAR_NUMBER to State.STATE_FRACTION,
            CharType.CHAR_EXP to State.STATE_EXP,
            CharType.CHAR_SPACE to State.STATE_END
        ),
        State.STATE_EXP to mapOf(
            CharType.CHAR_NUMBER to State.STATE_EXP_NUMBER,
            CharType.CHAR_SIGN to State.STATE_EXP_SIGN
        ),
        State.STATE_EXP_SIGN to mapOf(
            CharType.CHAR_NUMBER to State.STATE_EXP_NUMBER
        ),
        State.STATE_EXP_NUMBER to mapOf(
            CharType.CHAR_NUMBER to State.STATE_EXP_NUMBER,
            CharType.CHAR_SPACE to State.STATE_END
        ),
        State.STATE_END to mapOf(
            CharType.CHAR_SPACE to State.STATE_END
        )
    )
}

fun toCharType(ch: Char): CharType {
    return if (ch in '0'..'9') {
        CharType.CHAR_NUMBER;
    } else if (ch == 'e' || ch == 'E') {
        CharType.CHAR_EXP;
    } else if (ch == '.') {
        CharType.CHAR_POINT;
    } else if (ch == '+' || ch == '-') {
        CharType.CHAR_SIGN;
    } else if (ch == ' ') {
        CharType.CHAR_SPACE;
    } else {
        CharType.CHAR_ILLEGAL;
    }
}

enum class CharType {
    CHAR_NUMBER,
    CHAR_EXP,
    CHAR_POINT,
    CHAR_SIGN,
    CHAR_SPACE,
    CHAR_ILLEGAL
}

enum class State {
    STATE_INITIAL,
    STATE_INT_SIGN,
    STATE_INTEGER,
    STATE_POINT,
    STATE_POINT_WITHOUT_INT,
    STATE_FRACTION,
    STATE_EXP,
    STATE_EXP_SIGN,
    STATE_EXP_NUMBER,
    STATE_END
}