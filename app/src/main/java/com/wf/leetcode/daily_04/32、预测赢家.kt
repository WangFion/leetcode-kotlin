package com.wf.leetcode.daily_04

import kotlin.math.max

/**
 * leetcode -> com.wf.leetcode.daily_04 -> `32、预测赢家`
 *
 * @Author: wf-pc
 * @Date: 2020-09-01 09:04
 * -------------------------
 * 486题
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/predict-the-winner
 *
 * 给定一个表示分数的非负整数数组。 玩家 1 从数组任意一端拿取一个分数，随后玩家 2 继续从剩余数组任意一端
 * 拿取分数，然后玩家 1 拿，…… 。每次一个玩家只能拿取一个分数，分数被拿取之后不再可取。直到没有剩余分数
 * 可取时游戏结束。最终获得分数总和最多的玩家获胜。
 * 给定一个表示分数的数组，预测玩家1是否会成为赢家。你可以假设每个玩家的玩法都会使他的分数最大化。
 *
 * 示例 1：
 * 输入：[1, 5, 2]
 * 输出：False
 * 解释：一开始，玩家1可以从1和2中进行选择。
 * 如果他选择 2（或者 1 ），那么玩家 2 可以从 1（或者 2 ）和 5 中进行选择。如果玩家 2 选择了 5 ，那么玩家 1 则只剩下 1（或者 2 ）可选。
 * 所以，玩家 1 的最终分数为 1 + 2 = 3，而玩家 2 为 5 。
 * 因此，玩家 1 永远不会成为赢家，返回 False 。
 *
 * 示例 2：
 * 输入：[1, 5, 233, 7]
 * 输出：True
 * 解释：玩家 1 一开始选择 1 。然后玩家 2 必须从 5 和 7 中进行选择。无论玩家 2 选择了哪个，玩家 1 都可以选择 233 。
 * 最终，玩家 1（234 分）比玩家 2（12 分）获得更多的分数，所以返回 True，表示玩家 1 可以成为赢家。
 *
 * 提示：
 * 1 <= 给定的数组长度 <= 20.
 * 数组里所有分数都为非负数且不会大于 10000000 。
 * 如果最终两个玩家的分数相等，那么玩家 1 仍为赢家。
 */
fun main() {
    val nums1 = intArrayOf(1, 5, 2)
    println(predictTheWinner(nums1))
    println(predictTheWinner2(nums1))
    println(predictTheWinner3(nums1))

    val nums2 = intArrayOf(1, 5, 233, 7)
    println(predictTheWinner(nums2))
    println(predictTheWinner2(nums2))
    println(predictTheWinner3(nums2))
}

/**
 * plan1：递归
 * 为了判断哪个玩家可以获胜，需要计算一个总分，为先手得分与后手得分之差。当数组中的所有数字都被拿取时，
 * 如果总分大于或等于 0，则先手获胜，反之则后手获胜。
 *
 * 由于每次只能从数组的任意一端拿取数字，因此可以保证数组中剩下的部分一定是连续的。假设数组当前剩下的部分为
 * 下标 start 到下标 end，其中 0≤start≤end<nums.length。如果 start=end，则只剩一个数字，当前玩家只能拿
 * 取这个数字。如果 start<end，则当前玩家可以选择 nums[start] 或 nums[end]，然后轮到另一个玩家在数组剩下
 * 的部分选取数字。这是一个递归的过程。
 *
 * 计算总分时，需要记录当前玩家是先手还是后手，判断当前玩家的得分应该记为正还是负。当数组中剩下的数字多于 1
 * 个时，当前玩家会选择最优的方案，使得自己的分数最大化，因此对两种方案分别计算当前玩家可以得到的分数，其中
 * 的最大值为当前玩家最多可以得到的分数。
 *
 * 时间复杂度：O(2^n)，其中 n 是数组的长度。
 * 空间复杂度：O(n)，其中 n 是数组的长度。空间复杂度取决于递归使用的栈空间。
 */
fun predictTheWinner(nums: IntArray): Boolean {
    return score(nums, 0, nums.size - 1, 1) >= 0
}

// 其中turn用来记录当前是先手还是后手，先手则为正，后手则为负
fun score(nums: IntArray, start: Int, end: Int, turn: Int): Int {
    if (start == end) {
        return nums[start] * turn
    }
    val startScore = nums[start] * turn + score(nums, start + 1, end, -turn)
    val endScore = nums[end] * turn + score(nums, start, end - 1, -turn)
    return max(startScore * turn, endScore * turn) * turn
}

/**
 * plan2：动态规划
 *
 * 定义二维数组 dp，其行数和列数都等于数组的长度，dp[i][j] 表示当数组剩下的部分为下标 i 到下标 j 时，
 * 当前玩家与另一个玩家的分数之差的最大值，注意当前玩家不一定是先手。
 * 1、只有当 i≤j 时，数组剩下的部分才有意义，因此当 i>j 时，dp[i][j]=0。
 * 2、当 i=j 时，只剩一个数字，当前玩家只能拿取这个数字，因此对于所有 0≤i<nums.length，都有 dp[i][i]=nums[i]。
 * 3、当 i<j 时，当前玩家可以选择 nums[i] 或 nums[j]，然后轮到另一个玩家在数组剩下的部分选取数字。
 *    在两种方案中，当前玩家会选择最优的方案，使得自己的分数最大化。因此可以得到如下状态转移方程：
 *            dp[i][j]=max(nums[i]−dp[i+1][j], nums[j]−dp[i][j−1])
 * 最后判断 dp[0][nums.length−1] 的值，如果大于或等于 0，则先手得分大于或等于后手得分，因此先手成为赢家，否则后手成为赢家。
 *
 * 时间复杂度：O(n^2)，其中 n 是数组的长度。需要计算每个子数组对应的 dp 的值，共有 n(n+1)/2 个子数组。
 * 空间复杂度：O(n^2)，其中 n 是数组的长度。空间复杂度取决于额外创建的数组 dp。
 */
fun predictTheWinner2(nums: IntArray): Boolean {
    val dp = Array(nums.size) { IntArray(nums.size) }
    for (i in nums.indices) {
        dp[i][i] = nums[i]
    }
    for (i in nums.size - 2 downTo 0) {
        for (j in i + 1 until nums.size) {
            dp[i][j] = max(nums[i] - dp[i + 1][j], nums[j] - dp[i][j - 1])
        }
    }
    return dp[0][nums.size - 1] >= 0
}

/**
 * plan3：动态规划 + 滚动数组
 *
 * 上述代码中使用了二维数组 dp。分析状态转移方程可以看到，dp[i][j] 的值只和 dp[i+1][j] 与 dp[i][j−1] 有关，
 * 即在计算 dp 的第 i 行的值时，只需要使用到 dp 的第 i 行和第 i+1 行的值，因此可以使用一维数组代替二维数组，
 * 对空间进行优化。
 *
 * 时间复杂度：O(n^2)，其中 n 是数组的长度。需要计算每个子数组对应的 dp 的值，共有 n(n+1)/2 个子数组。
 * 空间复杂度：O(n)，其中 n 是数组的长度。空间复杂度取决于额外创建的数组 dp。
 */
fun predictTheWinner3(nums: IntArray): Boolean {
    val dp = IntArray(nums.size)
    for (i in nums.indices) {
        dp[i] = nums[i]
    }
    for (i in nums.size - 2 downTo 0) {
        for (j in i + 1 until nums.size) {
            dp[j] = max(nums[i] - dp[j], nums[j] - dp[j - 1])
        }
    }
    return dp[nums.size - 1] >= 0
}