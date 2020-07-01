package com.wf.leetcode.daily

import java.util.*

/**
 * leetcode -> com.wf.leetcode.daily -> `5、用两个栈实现队列`
 *
 * @Author: wf-pc
 * @Date: 2020-06-30 15:57
 * -------------------------
 *
 * 用两个栈实现一个队列。队列的声明如下，请实现它的两个函数 appendTail 和 deleteHead ，
 * 分别完成在队列尾部插入整数和在队列头部删除整数的功能。(若队列中没有元素，deleteHead 操作返回 -1 )
 *
 * 示例 1：
 * 输入：
 * ["CQueue", "appendTail", "deleteHead", "deleteHead"]
 * [[], [3], [], []]
 * 输出：[null, null, 3, -1]
 *
 * 示例 2：
 * 输入：
 * ["CQueue", "deleteHead", "appendTail", "appendTail", "deleteHead", "deleteHead"]
 * [[], [], [5], [2], [], []]
 * 输出：[null, -1, null, null, 5, 2]
 *
 * 提示：
 * 1 <= values <= 10000
 * 最多会对 appendTail、deleteHead 进行 10000 次调用
 */

fun main() {
    val opt1 = arrayOf("CQueue", "appendTail", "deleteHead", "deleteHead")
    val num1 = arrayOf("", "3", "", "")
    val queue1 = CQueue()
    for (i in opt1.indices) {
        when (opt1[i]) {
            "appendTail" -> {
                queue1.appendTail(num1[i].toInt())
                print("null  ")
            }
            "deleteHead" -> print("${queue1.deleteHead()}  ")
            else -> print("null  ")
        }
    }

    println()
    val opt2 =
        arrayOf("CQueue", "deleteHead", "appendTail", "appendTail", "deleteHead", "deleteHead")
    val num2 = arrayOf("", "", "5", "2", "", "")
    val queue2 = CQueue()
    for (i in opt2.indices) {
        when (opt2[i]) {
            "appendTail" -> {
                queue2.appendTail(num2[i].toInt())
                print("null  ")
            }
            "deleteHead" -> print("${queue2.deleteHead()}  ")
            else -> print("null  ")
        }
    }

}

class CQueue() {

    private val stack1 = Stack<Int>()
    private val stack2 = Stack<Int>()

    fun appendTail(value: Int) {
        stack1.push(value)
    }

    fun deleteHead(): Int {
        if (stack2.empty()) {
            while (!stack1.empty()) {
                stack2.push(stack1.pop())
            }
        }
        return if (stack2.empty()) -1 else stack2.pop()
    }

}