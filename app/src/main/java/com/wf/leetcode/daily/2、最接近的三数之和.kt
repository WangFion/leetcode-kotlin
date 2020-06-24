package com.wf.leetcode.daily

import kotlin.math.abs

/**
 * leetcode -> com.wf.leetcode.daily -> `2、最接近的三数之和`
 *
 * @Author: wf-pc
 * @Date: 2020-06-24 09:10
 * -------------------------
 *
 * 给定一个包括 n 个整数的数组 nums 和 一个目标值 target。
 * 找出 nums 中的三个整数，使得它们的和与 target 最接近。
 * 返回这三个数的和。假定每组输入只存在唯一答案。
 *
 * 示例：
 *     输入：nums = [-1,2,1,-4], target = 1
 *     输出：2
 *     解释：与 target 最接近的和是 2 (-1 + 2 + 1 = 2) 。
 *
 * 提示：
 *     3 <= nums.length <= 10^3
 *     -10^3 <= nums[i] <= 10^3
 *     -10^4 <= target <= 10^4
 */
fun main() {
    val nums = intArrayOf(-1, 2, 1, -4)
    println(threeSumClosest(nums, 1))
    println(threeSumClosest2(nums, 1))

    val nums2 = intArrayOf(0, 1, 2)
    println(threeSumClosest(nums2, 0))
    println(threeSumClosest2(nums2, 0))

    val nums3 = intArrayOf(1, 1, -1, -1, 3)
    println(threeSumClosest(nums3, -1))
    println(threeSumClosest2(nums3, -1))
}

/**
 * plan1:暴力循环法
 *
 * 时间复杂度：O(n^3)，对于每个元素，通过遍历其余部分来寻找它所对应的目标元素。
 * 空间复杂度：O(1)。
 */
fun threeSumClosest(nums: IntArray, target: Int): Int {
    if (nums.size < 3) {
        throw IllegalArgumentException("Array length is less than 3")
    }
    var result = nums[0] + nums[1] + nums[2]
    if (result == target) {
        return target
    }
    for (i in 0..nums.size - 3) {
        for (j in (i + 1)..nums.size - 2) {
            for (k in (j + 1) until nums.size) {
                val sum = nums[i] + nums[j] + nums[k]
                if (sum == target) {
                    return target
                }
                if (abs(sum - target) <= abs(result - target)) {
                    result = sum
                }
            }
        }
    }
    return result
}

/**
 * plan2:一次循环+双指针
 *
 * 时间复杂度：O(N^2)，其中 N 是数组 nums 的长度。我们首先需要 O(NlogN) 的时间对数组进行排序，
 *           随后在枚举的过程中，使用一重循环 O(N) 枚举 i，双指针 O(N) 枚举 j 和 k，故一共是 O(N^2)。
 * 空间复杂度：O(logN)。排序需要使用 O(logN) 的空间。然而我们修改了输入的数组 nums，在实际情况下不一定允许，
 *           因此也可以看成使用了一个额外的数组存储了 nums 的副本并进行排序，此时空间复杂度为 O(N)。
 */
fun threeSumClosest2(nums: IntArray, target: Int): Int {
    if (nums.size < 3) {
        throw IllegalArgumentException("Array length is less than 3")
    }
    nums.sort()
    var result = nums[0] + nums[1] + nums[2]
    if (result == target) {
        return result
    }
    for (i in nums.indices) {
        if (i > 0 && nums[i] == nums[i - 1]) {
            continue
        }
        var j = i + 1
        var k = nums.size - 1
        while (j < k) {
            val sum = nums[i] + nums[j] + nums[k]
            // 如果sum和target相等则说明已经是最小的，直接返回
            if (sum == target) {
                return sum
            }
            // 修正结果
            if (abs(sum - target) <= abs(result - target)) {
                result = sum
            }
            if (sum < target) {
                // 和比目标小，说明需要向和大的方向移动，nums为升序，故移动j
                do {
                    ++j
                } while (j < k && nums[j] == nums[j - 1])
            } else {
                // 和比目标大，说明需要向和小的方向移动，nums为升序，故移动k
                do {
                    --k
                } while (k > j && nums[k] == nums[k + 1])
            }
        }
    }
    return result
}