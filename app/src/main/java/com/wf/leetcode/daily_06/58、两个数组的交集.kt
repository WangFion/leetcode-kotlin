package com.wf.leetcode.daily_06

import java.util.*
import kotlin.collections.HashSet

/**
 * leetcode -> com.wf.leetcode.daily_06 -> `58、两个数组的交集`
 *
 * @Author: wf-pc
 * @Date: 2020-11-02 09:42
 * -------------------------
 *
 * 349题
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/intersection-of-two-arrays
 *
 * 给定两个数组，编写一个函数来计算它们的交集。
 *
 * 示例 1：
 * 输入：nums1 = [1,2,2,1], nums2 = [2,2]
 * 输出：[2]
 *
 * 示例 2：
 * 输入：nums1 = [4,9,5], nums2 = [9,4,9,8,4]
 * 输出：[9,4]
 *
 * 说明：
 * 输出结果中的每个元素一定是唯一的。
 * 我们可以不考虑输出结果的顺序。
 */

fun main() {
    val nums1 = intArrayOf(1, 2, 2, 1)
    val nums2 = intArrayOf(2, 2)
    println(intersection(nums1, nums2).contentToString())
    println(intersection2(nums1, nums2).contentToString())

    val nums3 = intArrayOf(4, 9, 5)
    val nums4 = intArrayOf(9, 4, 9, 8, 4)
    println(intersection(nums3, nums4).contentToString())
    println(intersection2(nums3, nums4).contentToString())
}

/**
 * plan1：迭代 + 哈希
 *
 * 时间复杂度：O(m+n)，其中 m 和 n 分别是两个数组的长度。，遍历较小的集合并判断元素是否在另一个集合中
 *           需要 O(min(m,n)) 的时间，因此总时间复杂度是 O(m+n)。
 * 空间复杂度：O(m+n)，其中 m 和 n 分别是两个数组的长度。空间复杂度主要取决于两个集合。
 */
fun intersection(nums1: IntArray, nums2: IntArray): IntArray {
    val arr = if (nums1.size < nums2.size) nums1 else nums2
    val contain = if (nums1.size < nums2.size) nums2 else nums1
    val set = HashSet<Int>()
    for (v in arr) {
        if (contain.contains(v)) {
            set.add(v)
        }
    }
    return set.toIntArray()
}

/**
 * plan2：排序 + 双指针
 * 初始时，两个指针分别指向两个数组的头部。每次比较两个指针指向的两个数组中的数字，
 * 如果两个数字不相等，则将指向较小数字的指针右移一位，
 * 如果两个数字相等，且该数字不等于前一个共有的数字 ，将该数字添加到答案，同时将两个指针都右移一位。
 * 当至少有一个指针超出数组范围时，遍历结束。
 *
 * 时间复杂度：O(mlogm+nlogn)，其中 m 和 n 分别是两个数组的长度。对两个数组排序的时间复杂度分别是 O(mlogm)
 *           和 O(nlogn)，双指针寻找交集元素的时间复杂度是 O(m+n)，因此总时间复杂度是 O(mlogm+nlogn)。
 * 空间复杂度：O(logm+logn)，其中 m 和 n 分别是两个数组的长度。空间复杂度主要取决于排序使用的额外空间。
 */
fun intersection2(nums1: IntArray, nums2: IntArray): IntArray {
    Arrays.sort(nums1)
    Arrays.sort(nums2)
    var i = 0
    var j = 0
    val arr = mutableListOf<Int>()
    while (i < nums1.size && j < nums2.size) {
        when {
            nums1[i] == nums2[j] -> {
                if (arr.isEmpty() || nums1[i] != arr.last()) {
                    arr.add(nums1[i])
                }
                i++
                j++
            }
            nums1[i] < nums2[j] -> {
                i++
            }
            else -> {
                j++
            }
        }
    }
    return arr.toIntArray()
}
 