package com.wf.leetcode.daily

import java.util.*
import kotlin.math.min


/**
 * leetcode -> com.wf.leetcode.daily -> `3、长度最小的子数组`
 *
 * @Author: wf-pc
 * @Date: 2020-06-28 16:17
 * -------------------------
 *
 * 给定一个含有 n 个正整数的数组和一个正整数 s ，找出该数组中满足其和 ≥ s 的长度最小的连续子数组，并返回其长度。
 * 如果不存在符合条件的连续子数组，返回 0。
 *
 * 示例:
 * 输入: s = 7, nums = [2,3,1,2,4,3]
 * 输出: 2
 * 解释: 子数组 [4,3] 是该条件下的长度最小的连续子数组。
 *
 * 进阶:
 * 如果你已经完成了O(n) 时间复杂度的解法, 请尝试 O(n log n) 时间复杂度的解法。
 */
fun main() {
    val s = 7
    val nums = intArrayOf(2, 3, 1, 2, 4, 3)
    println(minSubArrayLen(s, nums))
    println(minSubArrayLen2(s, nums))
    println(minSubArrayLen3(s, nums))
}


/**
 * plan1:暴力法
 *
 * 时间复杂度：O(n^2)，其中 n 是数组的长度，需要遍历每个下标作为子数组的开始下标，
 *            对于每个开始下标，需要遍历其后面的下标得到长度最小的子数组。
 * 空间复杂度：O(1)。
 */
fun minSubArrayLen(s: Int, nums: IntArray): Int {
    var result = Int.MAX_VALUE
    for (i in nums.indices) {
        var sum = 0
        for (j in i until nums.size) {
            sum += nums[j]
            if (sum >= s) {
                result = min(result, j - i + 1)
                break
            }
        }
    }
    return if (result == Int.MAX_VALUE) 0 else result
}


/**
 * plan2:双指针/滑动窗口
 *
 * 时间复杂度：O(n)，其中 n 是数组的长度。指针 start 和 end 最多各移动 n 次。
 * 空间复杂度：O(1)。
 */
fun minSubArrayLen2(s: Int, nums: IntArray): Int {
    var result = Int.MAX_VALUE
    var start = 0
    var end = 0
    var sum = 0
    while (end < nums.size) {
        sum += nums[end]
        while (sum >= s) {
            result = min(result, end - start + 1)
            sum -= nums[start]
            ++start
        }
        ++end
    }
    return if (result == Int.MAX_VALUE) 0 else result
}


/**
 * plan3:前缀+二分查找
 *
 * 我们申请一个临时数组sums，其中sums[i]表示的是原数组nums前i个元素的和，
 * 题中说了“给定一个含有 n 个正整数的数组”，既然是正整数，那么相加的和会越来越大，也就是sums数组中的元素是递增的。
 * 我们只需要找到sums[k]-sums[j]>=s，那么k-j就是满足的连续子数组，但不一定是最小的，所以我们要继续找，直到找到最小的为止。
 * 怎么找呢，我们可以使用两个for循环来枚举，但这又和第一种暴力求解一样了，
 * 所以我们可以换种思路，求sums[k]-sums[j]>=s我们可以求sums[j]+s<=sums[k]，
 * 那这样就好办了，因为数组sums中的元素是递增的，也就是排序的，我们只需要求出sum[j]+s的值，然后使用二分法查找即可找到这个k。
 *
 * 注意:这里的函数Arrays.binarySearch(sums, target);如果找到就会返回值的下标，
 *      如果没找到就会返回一个负数，这个负数取反之后就是查找的值应该在数组中的位置
 */
fun minSubArrayLen3(s: Int, nums: IntArray): Int {
    var result = Int.MAX_VALUE
    val sums = IntArray(nums.size)
    for (index in nums.indices) {
        if (index == 0) {
            sums[index] = nums[index]
        } else {
            sums[index] = nums[index] + sums[index - 1]
        }
        //println("sums[${index}] = ${sums[index]}")
    }
    for (i in nums.indices) {
        val target = s + sums[i]
        var bound = Arrays.binarySearch(sums, target)
        if (bound < 0) {
            //bound = -bound - 1
            bound = bound.inv()//取反
        }
        if (bound < nums.size) {
            result = min(result, bound - i)
        }
    }
    return if (result == Int.MAX_VALUE) 0 else result
}


