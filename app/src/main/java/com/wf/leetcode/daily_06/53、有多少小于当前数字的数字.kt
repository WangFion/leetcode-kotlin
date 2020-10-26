package com.wf.leetcode.daily_06

import java.util.*

/**
 * leetcode -> com.wf.leetcode.daily_06 -> `53、有多少小于当前数字的数字`
 *
 * @Author: wf-pc
 * @Date: 2020-10-26 09:49
 * -------------------------
 * 1365题
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/how-many-numbers-are-smaller-than-the-current-number
 *
 * 给你一个数组 nums，对于其中每个元素 nums[i]，请你统计数组中比它小的所有数字的数目。
 * 换而言之，对于每个 nums[i] 你必须计算出有效的 j 的数量，其中 j 满足 j != i 且 nums[j] < nums[i] 。
 * 以数组形式返回答案。
 *
 * 示例 1：
 * 输入：nums = [8,1,2,2,3]
 * 输出：[4,0,1,1,3]
 * 解释：
 * 对于 nums[0]=8 存在四个比它小的数字：（1，2，2 和 3）。
 * 对于 nums[1]=1 不存在比它小的数字。
 * 对于 nums[2]=2 存在一个比它小的数字：（1）。
 * 对于 nums[3]=2 存在一个比它小的数字：（1）。
 * 对于 nums[4]=3 存在三个比它小的数字：（1，2 和 2）。
 *
 * 示例 2：
 * 输入：nums = [6,5,4,8]
 * 输出：[2,1,0,3]
 *
 * 示例 3：
 * 输入：nums = [7,7,7,7]
 * 输出：[0,0,0,0]
 *
 * 提示：
 * 2 <= nums.length <= 500
 * 0 <= nums[i] <= 100
 */
fun main() {
    val arr = intArrayOf(8, 1, 2, 2, 3)
    println(smallerNumbersThanCurrent(arr).contentToString())
    println(smallerNumbersThanCurrent2(arr).contentToString())
    println(smallerNumbersThanCurrent3(arr).contentToString())
}

/**
 * plan1：暴力法
 *
 * 时间复杂度：O(N^2)，其中 N 为数组的长度。
 * 空间复杂度：O(1)。注意我们不计算答案数组的空间占用。
 */
fun smallerNumbersThanCurrent(nums: IntArray): IntArray {
    val result = IntArray(nums.size)
    for ((i, current) in nums.withIndex()) {
        var count = 0
        for (value in nums) {
            if (value < current) {
                count++
            }
        }
        result[i] = count
    }
    return result
}

/**
 * plan2：排序计算
 *
 * 时间复杂度：O(NlogN)，其中 N 为数组的长度。排序需要的时间视算法而定，快排为O(NlogN)，随后需要 O(N) 时间来遍历。
 * 空间复杂度：O(N)。因为要额外开辟一个数组。
 */
fun smallerNumbersThanCurrent2(nums: IntArray): IntArray {
    val result = IntArray(nums.size)
    val mapArr = Array(nums.size) { IntArray(2) }
    for ((i, v) in nums.withIndex()) {
        mapArr[i][0] = v
        mapArr[i][1] = i
    }
    Arrays.sort(mapArr) { o1, o2 -> o1[0] - o2[0] }
    var pre = -1
    for (i in nums.indices) {
        if (pre == -1 || mapArr[i][0] != mapArr[i - 1][0]) {
            pre = i
        }
        result[mapArr[i][1]] = pre
    }
    return result
}

/**
 * plan3：计数排序
 * 注意到数组元素的值域为 [0,100]，所以可以考虑建立一个频次数组 cnt ，cnt[i] 表示数字 i 出现的次数。
 * 那么对于数字 i 而言，小于它的数目就为 cnt[0...i−1] 的总和。
 *
 * 时间复杂度：O(N+K)，其中 K 为值域大小。需要遍历两次原数组，同时遍历一次频次数组 cnt 找出前缀和。
 * 空间复杂度：O(K)。因为要额外开辟一个值域大小的数组。
 */
fun smallerNumbersThanCurrent3(nums: IntArray): IntArray {
    val cnt = IntArray(101)
    // 1、根据原始数组初始化计数数组
    for (i in nums.indices) {
        cnt[nums[i]]++
    }
    // 2、累加比但前值小的计数
    for (i in 1..100) {
        cnt[i] += cnt[i - 1]
    }
    // 3、遍历求解
    val result = IntArray(nums.size)
    for (i in nums.indices) {
        result[i] = if (nums[i] == 0) 0 else cnt[nums[i] - 1]
    }
    return result
}