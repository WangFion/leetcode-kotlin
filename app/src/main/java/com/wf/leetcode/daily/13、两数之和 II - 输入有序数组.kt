package com.wf.leetcode.daily

/**
 * leetcode -> com.wf.leetcode.daily -> `13、两数之和 II - 输入有序数组`
 *
 * @Author: wf-pc
 * @Date: 2020-07-20 15:26
 * -------------------------
 * 167题：https://leetcode-cn.com/problems/two-sum-ii-input-array-is-sorted/
 * 给定一个已按照升序排列 的有序数组，找到两个数使得它们相加之和等于目标数。
 * 函数应该返回这两个下标值 index1 和 index2，其中 index1 必须小于 index2。
 *
 * 说明:
 * 返回的下标值（index1 和 index2）不是从零开始的。
 * 你可以假设每个输入只对应唯一的答案，而且你不可以重复使用相同的元素。
 *
 * 示例:
 * 输入: numbers = [2, 7, 11, 15], target = 9
 * 输出: [1,2]
 * 解释: 2 与 7 之和等于目标数 9 。因此 index1 = 1, index2 = 2 。
 */
fun main() {
    val numbers = intArrayOf(2, 7, 11, 15)
    println(twoSum(numbers, 9).contentToString())
    println(twoSum2(numbers, 9).contentToString())
}

/**
 * plan1:二分查找
 * 时间复杂度：O(nlogn)，其中 n 是数组的长度。需要遍历数组一次确定第一个数，时间复杂度是 O(n)，
 *           寻找第二个数使用二分查找，时间复杂度是 O(logn)。
 * 空间复杂度：O(1)。
 */
fun twoSum(numbers: IntArray, target: Int): IntArray {
    val index = IntArray(2)
    for (i in 0..numbers.size - 2) {
        val tmp = target - numbers[i]
        var l = i + 1
        var r = numbers.size - 1
        while (l <= r) {
            val center = (l + r) / 2
            when {
                numbers[center] == tmp -> {
                    index[0] = i + 1
                    index[1] = center + 1
                    return index
                }
                numbers[center] > tmp -> {
                    r = center - 1
                }
                else -> {
                    l = center + 1
                }
            }
        }
    }
    return index
}

/**
 * plan2:双指针
 *
 * 时间复杂度：O(n)，其中 n 是数组的长度。两个指针移动的总次数最多为 n 次。
 * 空间复杂度：O(1)。
 */
fun twoSum2(numbers: IntArray, target: Int): IntArray {
    val index = IntArray(2)
    var l = 0
    var r = numbers.size - 1
    while (l <= r) {
        val tmp = numbers[l] + numbers[r]
        when {
            tmp == target -> {
                index[0] = l + 1
                index[1] = r + 1
                return index
            }
            tmp > target -> {
                r--
            }
            else -> {
                l++
            }
        }
    }
    return index
}