package com.wf.leetcode.daily_04

import android.os.Build
import androidx.annotation.RequiresApi
import java.util.*
import kotlin.collections.HashMap

/**
 * leetcode -> com.wf.leetcode.daily_04 -> `35、前K个高频元素`
 *
 * @Author: wf-pc
 * @Date: 2020-09-07 09:43
 * -------------------------
 * 347题
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/top-k-frequent-elements
 * 给定一个非空的整数数组，返回其中出现频率前 k 高的元素。
 *
 * 示例 1:
 * 输入: nums = [1,1,1,2,2,3], k = 2
 * 输出: [1,2]
 *
 * 示例 2:
 * 输入: nums = [1], k = 1
 * 输出: [1]
 *
 * 提示：
 * 你可以假设给定的 k 总是合理的，且 1 ≤ k ≤ 数组中不相同的元素的个数。
 * 你的算法的时间复杂度必须优于 O(n log n) , n 是数组的大小。
 * 题目数据保证答案唯一，换句话说，数组中前 k 个高频元素的集合是唯一的。
 * 你可以按任意顺序返回答案。
 */
@RequiresApi(Build.VERSION_CODES.N)
fun main() {
    val arr1 = intArrayOf(1, 1, 1, 2, 2, 3)
    val arr2 = intArrayOf(1)
    val arr3 = intArrayOf(4, 1, -1, 2, -1, 2, 3)
    println(topKFrequent(arr1, 2).contentToString())
    println(topKFrequent(arr2, 1).contentToString())
    println(topKFrequent(arr3, 2).contentToString())
}

/**
 * plan1：小顶堆（也可基于其他排序算法来实现，例如快排、桶排等）
 *
 * 时间复杂度：O(Nlogk)，其中 N 为数组的长度。我们首先遍历原数组，并使用哈希表记录出现次数，每个元素需要 O(1)
 *           的时间，共需 O(N) 的时间。随后，我们遍历「出现次数数组」，由于堆的大小至多为 k，因此每次堆操作
 *           需要 O(logk) 的时间，共需 O(Nlogk) 的时间。
 * 空间复杂度：O(N)。最坏情况下（每个元素都不同），map 需要存储 n 个键值对，优先队列需要存储 k 个元素，因此，空间复杂度是O(n)。
 */
@RequiresApi(Build.VERSION_CODES.N)
fun topKFrequent(nums: IntArray, k: Int): IntArray {
    val map = HashMap<Int, Int>()
    for (num in nums) {
        if (map.containsKey(num)) {
            map[num] = map[num]?.plus(1) ?: 1
        } else {
            map[num] = 1
        }
    }
    val queue = PriorityQueue<Int> { o1, o2 -> map[o1]!! - map[o2]!! }
    for ((key, value) in map) {
        if (queue.size < k) {
            queue.offer(key)
        } else if (queue.peek() != null && value > map[queue.peek()!!]!!) {
            queue.poll()
            queue.offer(value)
        }
    }
    return queue.toIntArray()
}