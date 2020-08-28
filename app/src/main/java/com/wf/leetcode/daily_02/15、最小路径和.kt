package com.wf.leetcode.daily_02

import kotlin.math.min

/**
 * leetcode -> com.wf.leetcode.daily_02 -> `15、最小路径和`
 *
 * @Author: wf-pc
 * @Date: 2020-07-23 09:41
 * -------------------------
 * 64题：https://leetcode-cn.com/problems/minimum-path-sum/
 * 给定一个包含非负整数的 m x n 网格，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。
 * 说明：每次只能向下或者向右移动一步。
 *
 * 示例:
 * 输入:
 *     [
 *         [1,3,1],
 *         [1,5,1],
 *         [4,2,1]
 *     ]
 * 输出: 7
 * 解释: 因为路径 1→3→1→1→1 的总和最小。
 */
fun main() {
    val grid = Array(3) { IntArray(3) }
    grid[0] = intArrayOf(1, 3, 1)
    grid[1] = intArrayOf(1, 5, 1)
    grid[2] = intArrayOf(4, 2, 1)
    println(minPathSum(grid))
    println(minPathSum2(grid))
    println(minPathSum3(grid))
}

/**
 * 动态规划
 * 创建二维数组 dp，与原始网格的大小相同，dp[i][j]表示从左上角出发到 (i,j) 位置的最小路径和。
 * 对于 dp 中的元素，通过以下状态转移方程计算元素值。
 * 当 i=0 且 j=0 时，dp[i][j] = grid[0][0]
 * 当 i>0 且 j>0 时，dp[i][j] = min(dp[i−1][j], dp[i][j−1]) + grid[i][j]
 * 当 i>0 且 j=0 时，dp[i][j] = dp[i−1][j] + grid[i][j]
 * 当 i=0 且 j>0 时，dp[i][j] = dp[i][j−1] + grid[i][j]
 * 最后得到 dp[m−1][n−1] 的值即为从网格左上角到网格右下角的最小路径和。
 *
 * 时间复杂度：O(mn)，其中 m 和 n 分别是网格的行数和列数。
 * 空间复杂度：O(mn)，其中 m和 n 分别是网格的行数和列数。创建一个二维数组 dp，和网格大小相同。
 */
fun minPathSum(grid: Array<IntArray>): Int {
    val dp = Array(grid.size) { IntArray(grid[0].size) }
    for (i in grid.indices) {
        for (j in grid[i].indices) {
            when {
                i == 0 && j == 0 -> {
                    dp[i][j] = grid[i][j]
                }
                i != 0 && j != 0 -> {
                    dp[i][j] = min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j]
                }
                i == 0 && j != 0 -> {
                    dp[i][j] = dp[i][j - 1] + grid[i][j]
                }
                i != 0 && j == 0 -> {
                    dp[i][j] = dp[i - 1][j] + grid[i][j]
                }
            }
        }
    }
    return dp[grid.size - 1][grid[0].size - 1]
}

/**
 * 一维滚动数组
 * 时间复杂度：O(mn)，其中 m 和 n 分别是网格的行数和列数。
 * 空间复杂度：O(n)，其中 m和 n 分别是网格的行数和列数。只保留上一行的状态。
 */
fun minPathSum2(grid: Array<IntArray>): Int {
    val dp = IntArray(grid[0].size)
    for (i in grid.indices) {
        for (j in grid[i].indices) {
            when {
                i == 0 && j == 0 -> {
                    dp[j] = grid[i][j]
                }
                i != 0 && j != 0 -> {
                    dp[j] = min(dp[j], dp[j - 1]) + grid[i][j]
                }
                i == 0 && j != 0 -> {
                    dp[j] = dp[j - 1] + grid[i][j]
                }
                i != 0 && j == 0 -> {
                    dp[j] += grid[i][j]
                }
            }
        }
    }
    return dp[grid[0].size - 1]
}

/**
 * 直接利用原数组
 */
fun minPathSum3(grid: Array<IntArray>): Int {
    for (i in grid.indices) {
        for (j in grid[i].indices) {
            when {
                i == 0 && j == 0 -> {
                    //continue
                }
                i != 0 && j != 0 -> {
                    grid[i][j] += min(grid[i - 1][j], grid[i][j - 1])
                }
                i == 0 && j != 0 -> {
                    grid[i][j] += grid[i][j - 1]
                }
                i != 0 && j == 0 -> {
                    grid[i][j] += grid[i - 1][j]
                }
            }
        }
    }
    return grid[grid.size - 1][grid[0].size - 1]
}

