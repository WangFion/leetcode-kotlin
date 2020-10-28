package com.wf.leetcode.daily_06

/**
 * leetcode -> com.wf.leetcode.daily_06 -> `55、独一无二的出现次数`
 *
 * @Author: wf-pc
 * @Date: 2020-10-28 09:24
 * -------------------------
 *
 * 1207题
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/unique-number-of-occurrences
 *
 * 给你一个整数数组 arr，请你帮忙统计数组中每个数的出现次数。
 * 如果每个数的出现次数都是独一无二的，就返回 true；否则返回 false。
 *
 * 示例 1：
 * 输入：arr = [1,2,2,1,1,3]
 * 输出：true
 * 解释：在该数组中，1 出现了 3 次，2 出现了 2 次，3 只出现了 1 次。没有两个数的出现次数相同。
 *
 * 示例 2：
 * 输入：arr = [1,2]
 * 输出：false
 *
 * 示例 3：
 * 输入：arr = [-3,0,1,-3,1,1,1,-3,10,0]
 * 输出：true
 *
 * 提示：
 * 1 <= arr.length <= 1000
 * -1000 <= arr[i] <= 1000
 */
fun main() {
    val arr1 = intArrayOf(1, 2, 2, 1, 1, 3)
    val arr2 = intArrayOf(1, 2)
    val arr3 = intArrayOf(-3, 0, 1, -3, 1, 1, 1, -3, 10, 0)

    println(uniqueOccurrences(arr1))
    println(uniqueOccurrences(arr2))
    println(uniqueOccurrences(arr3))

    println(uniqueOccurrences2(arr1))
    println(uniqueOccurrences2(arr2))
    println(uniqueOccurrences2(arr3))
}

/**
 * plan1：hash表
 *
 * 时间复杂度：O(N)，其中 N 为数组的长度。遍历原始数组需要 O(N) 时间，而遍历中间过程产生的哈希表又需要 O(N) 的时间。
 * 空间复杂度：O(N)。
 */
fun uniqueOccurrences(arr: IntArray): Boolean {
    val map = mutableMapOf<Int, Int>()
    for (v in arr) {
        map[v] = map[v]?.plus(1) ?: 1
    }
    val set = mutableSetOf<Int>()
    for (v in map.values) {
        set.add(v)
    }
    return map.size == set.size
}


/**
 * plan2：hash表 + 提前返回
 *
 * 时间复杂度：O(N)，其中 N 为数组的长度。遍历原始数组需要 O(N) 时间，而遍历中间过程产生的哈希表又需要 O(N) 的时间。
 * 空间复杂度：O(N)。
 */
fun uniqueOccurrences2(arr: IntArray): Boolean {
    val map = mutableMapOf<Int, Int>()
    for (v in arr) {
        map[v] = map[v]?.plus(1) ?: 1
    }
    val set = mutableSetOf<Int>()
    for (v in map.values) {
        if (!set.add(v)) {
            return false
        }
    }
    return true
}