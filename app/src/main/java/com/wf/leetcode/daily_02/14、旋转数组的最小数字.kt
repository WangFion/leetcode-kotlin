package com.wf.leetcode.daily_02

/**
 * leetcode -> com.wf.leetcode.daily_02 -> `14、旋转数组的最小数字`
 *
 * @Author: wf-pc
 * @Date: 2020-07-22 09:33
 * -------------------------
 * 第1546题：https://leetcode-cn.com/problems/xuan-zhuan-shu-zu-de-zui-xiao-shu-zi-lcof/
 * 把一个数组最开始的若干个元素搬到数组的末尾，我们称之为数组的旋转。
 * 输入一个递增排序的数组的一个旋转，输出旋转数组的最小元素。
 * 例如，数组 [3,4,5,1,2] 为 [1,2,3,4,5] 的一个旋转，该数组的最小值为1。
 *
 * 示例 1：
 * 输入：[3,4,5,1,2]
 * 输出：1
 *
 * 示例 2：
 * 输入：[2,2,2,0,1]
 * 输出：0
 */
fun main() {
    val numbers = intArrayOf(3, 4, 5, 1, 2)
    println(minArray(numbers))
    println(minArray2(numbers))
    println(minArray3(numbers))

    val numbers2 = intArrayOf(2, 2, 2, 0, 1)
    println(minArray(numbers2))
    println(minArray2(numbers2))
    println(minArray3(numbers2))

    val numbers3 = intArrayOf(6, 8, 9)
    println(minArray(numbers3))
    println(minArray2(numbers3))
    println(minArray3(numbers3))

    val numbers4 = intArrayOf(5, 5, 5)
    println(minArray(numbers4))
    println(minArray2(numbers4))
    println(minArray3(numbers4))

}

/**
 * plan1:暴力循环
 */
fun minArray(numbers: IntArray): Int {
    for (i in 1 until numbers.size) {
        if (numbers[i] < numbers[i - 1]) {
            return numbers[i]
        }
    }
    return numbers[0]
}

/**
 * plan2:双指针
 */
fun minArray2(numbers: IntArray): Int {
    var l = 0
    var r = numbers.size - 1
    while (l < r) {
        if (numbers[l] > numbers[l + 1]) {
            return numbers[l + 1]
        }
        if (numbers[r] < numbers[r - 1]) {
            return numbers[r]
        }
        l++
        r--
    }
    return numbers[0]
}

/**
 * plan:二分查找
 *
 * 时间复杂度：平均时间复杂度为O(logn)，如果数组中的元素完全相同，那么 while 循环就需要执行 n 次，
 *            次忽略区间的右端点，时间复杂度为 O(n)。
 * 空间复杂度：O(1)。
 */
fun minArray3(numbers: IntArray): Int {
    var l = 0
    var r = numbers.size - 1
    while (l < r) {
        val mid = (l + r) / 2
        when {
            numbers[mid] > numbers[r] -> {
                l = mid + 1
            }
            numbers[mid] < numbers[r] -> {
                r = mid
            }
            else -> {
                r--
            }
        }
    }
    return numbers[l]
}