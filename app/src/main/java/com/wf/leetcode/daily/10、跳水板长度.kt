package com.wf.leetcode.daily

/**
 * leetcode -> com.wf.leetcode.daily.entity -> `10、跳水板长度`
 *
 * @Author: wf-pc
 * @Date: 2020-07-08 09:29
 * -------------------------
 *
 * 1666题：https://leetcode-cn.com/problems/diving-board-lcci/
 * 你正在使用一堆木板建造跳水板。有两种类型的木板，其中长度较短的木板长度为shorter，长度较长的木板长度为longer。
 * 你必须正好使用 k 块木板。编写一个方法，生成跳水板所有可能的长度。返回的长度需要从小到大排列。
 *
 * 示例：
 * 输入：
 * shorter = 1
 * longer = 2
 * k = 3
 * 输出： {3,4,5,6}
 *
 * 提示：
 * 0 < shorter <= longer
 * 0 <= k <= 100000
 */
fun main() {
    println(divingBoard(1, 2, 3).contentToString())
}

/**
 * plan1:数学
 * 共使用 k 块木板，共有 k+1 种组合，首先考虑边界值 0 块木板和木板长度一样，然后再考虑不同的组合
 *
 * 时间复杂度：O(k)，其中 k 是木板数量。短木板和长木板一共使用 k 块，一共有 k+1 种组合，对于每种组合都要计算跳水板的长度。
 * 空间复杂度：O(1)。除了返回值以外，额外使用的空间复杂度为常数。
 */
fun divingBoard(shorter: Int, longer: Int, k: Int): IntArray {
    if (k <= 0) {
        return IntArray(0)
    }
    if (shorter == longer) {
        return intArrayOf(shorter * k)
    }
    val arr = IntArray(k + 1)
    for (i in 0..k) {
        arr[i] = shorter * (k - i) + longer * i
    }
    return arr
}