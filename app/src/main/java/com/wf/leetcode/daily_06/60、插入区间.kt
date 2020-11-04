package com.wf.leetcode.daily_06

import kotlin.math.max
import kotlin.math.min

/**
 * leetcode -> com.wf.leetcode.daily_06 -> `60、插入区间`
 *
 * @Author: wf-pc
 * @Date: 2020-11-04 16:08
 * -------------------------
 *
 * 57题
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/insert-interval
 *
 * 给出一个无重叠的 ，按照区间起始端点排序的区间列表。
 * 在列表中插入一个新的区间，你需要确保列表中的区间仍然有序且不重叠（如果有必要的话，可以合并区间）。
 *
 * 示例 1：
 * 输入：intervals = [[1,3],[6,9]], newInterval = [2,5]
 * 输出：[[1,5],[6,9]]
 *
 * 示例 2：
 * 输入：intervals = [[1,2],[3,5],[6,7],[8,10],[12,16]], newInterval = [4,8]
 * 输出：[[1,2],[3,10],[12,16]]
 * 解释：这是因为新的区间 [4,8] 与 [3,5],[6,7],[8,10] 重叠。
 */
fun main() {
    val intervals = arrayOf(intArrayOf(1, 3), intArrayOf(6, 9))
    val newInterval = intArrayOf(2, 5)
    println(insert(intervals, newInterval).contentDeepToString())

    val intervals2 = arrayOf(
        intArrayOf(1, 2), intArrayOf(3, 5), intArrayOf(6, 7),
        intArrayOf(8, 10), intArrayOf(12, 16)
    )
    val newInterval2 = intArrayOf(4, 8)
    println(insert(intervals2, newInterval2).contentDeepToString())

    val intervals3 = arrayOf(intArrayOf(1, 5))
    val newInterval3 = intArrayOf(2, 3)
    println(insert(intervals3, newInterval3).contentDeepToString())
}

/**
 * plan1：模拟合并
 * 当我们遍历到区间 [li,ri] 时：
 * （1）如果 ri​<left，说明 [li,ri] 与 S 不重叠并且在其左侧，我们可以直接将 [li,ri] 加入答案；
 * （2）如果 li​>right，说明 [li,ri] 与 S 不重叠并且在其右侧，我们可以直接将 [li,ri] 加入答案；
 * （3）如果上面两种情况均不满足，说明 [li,ri] 与 S 重叠，我们无需将 [li,ri] 加入答案。
 *      此时，我们需要将 S 与 [li,ri] 合并，即将 S 更新为其与 [li,ri] 的并集。
 * 那么我们应当在什么时候将区间 S 加入答案呢？由于我们需要保证答案也是按照左端点排序的，
 * 因此当我们遇到第一个 满足 li​>right 的区间时，说明以后遍历到的区间不会与 S 重叠，
 * 并且它们左端点一定会大于 S 的左端点。此时我们就可以将 S 加入答案。
 * 特别地，如果不存在这样的区间，我们需要在遍历结束后，将 S 加入答案
 *
 * 时间复杂度：O(n)，其中 nnn 是数组 intervals 的长度，即给定的区间个数。
 * 空间复杂度：O(1)。除了存储返回答案的空间以外，我们只需要额外的常数空间即可。
 */
fun insert(intervals: Array<IntArray>, newInterval: IntArray): Array<IntArray> {
    var left = newInterval[0]
    var right = newInterval[1]
    val ansList = mutableListOf<IntArray>()
    var isInsert = false
    for (interval in intervals) {
        when {
            interval[0] > right -> {
                //当前区间的左边界比新区间的右边界大
                if (!isInsert) {
                    ansList.add(intArrayOf(left, right))
                    isInsert = true
                }
                ansList.add(interval)
            }
            interval[1] < left -> {
                //当前区间的有边界比新区间的左边界小
                ansList.add(interval)
            }
            else -> {
                //当前区间和新区间有交集
                left = min(left, interval[0])
                right = max(right, interval[1])
            }
        }
    }
    if (!isInsert) {
        //新区间在最右侧
        ansList.add(intArrayOf(left, right))
    }
    return ansList.toTypedArray()
}
 