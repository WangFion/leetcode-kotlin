package com.wf.leetcode.daily_06

/**
 * leetcode -> com.wf.leetcode.daily_06 -> `57、岛屿的周长`
 *
 * @Author: wf-pc
 * @Date: 2020-10-30 09:37
 * -------------------------
 *
 * 463题
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/island-perimeter
 *
 * 给定一个包含 0 和 1 的二维网格地图，其中 1 表示陆地 0 表示水域。
 * 网格中的格子水平和垂直方向相连（对角线方向不相连）。
 * 整个网格被水完全包围，但其中恰好有一个岛屿（或者说，一个或多个表示陆地的格子相连组成的岛屿）。
 * 岛屿中没有“湖”（“湖” 指水域在岛屿内部且不和岛屿周围的水相连）。
 * 格子是边长为 1 的正方形。网格为长方形，且宽度和高度均不超过 100 。
 * 计算这个岛屿的周长。
 *
 * 示例 :
 * 输入:
 * [[0, 1, 0, 0],
 *  [1, 1, 1, 0],
 *  [0, 1, 0, 0],
 *  [1, 1, 0, 0]]
 * 输出: 16
 */
fun main() {
    val grid = arrayOf(
        intArrayOf(0, 1, 0, 0),
        intArrayOf(1, 1, 1, 0),
        intArrayOf(0, 1, 0, 0),
        intArrayOf(1, 1, 0, 0)
    )
    println(islandPerimeter(grid))
    println(islandPerimeter2(grid))
}

/**
 * plan1：迭代
 * 遍历每个陆地格子，看其四个方向是否为边界或者水域，如果是，将这条边的贡献（即1）加入答案 ans 中即可。
 *
 * 时间复杂度：O(nm)，其中 n 为网格的高度，m 为网格的宽度。我们需要遍历每个格子，
 *           每个格子要看其周围 4 个格子是否为岛屿，因此总时间复杂度为 O(4nm)=O(nm)。
 * 空间复杂度：O(1)。只需要常数空间存放若干变量。
 */
fun islandPerimeter(grid: Array<IntArray>): Int {
    val dx = intArrayOf(-1, 0, 1, 0)
    val dy = intArrayOf(0, -1, 0, 1)
    var ans = 0
    for (i in grid.indices) {
        for (j in grid[0].indices) {
            if (grid[i][j] == 0) {
                continue
            }
            for (k in 0..3) {
                val x = i + dx[k]
                val y = j + dy[k]
                if (x < 0 || x >= grid.size
                    || y < 0 || y >= grid[0].size
                    || grid[x][y] == 0
                ) {
                    ans++
                }
            }
        }
    }
    return ans
}

/**
 * plan2：dfs
 * 我们也可以将方法一改成深度优先搜索遍历的方式，此时遍历的方式可扩展至统计多个岛屿各自的周长。
 * 需要注意的是为了防止陆地格子在深度优先搜索中被重复遍历导致死循环，我们需要将遍历过的陆地格子
 * 标记为已经遍历过，下面的代码中我们设定值为 2 的格子为已经遍历过的陆地格子。
 *
 * 时间复杂度：O(nm)，其中 n 为网格的高度，m 为网格的宽度。每个格子至多会被遍历一次，因此总时间复杂度为 O(nm)。
 * 空间复杂度：O(nm)。深度优先搜索复杂度取决于递归的栈空间，而栈空间最坏情况下会达到 O(nm)。
 */
fun islandPerimeter2(grid: Array<IntArray>): Int {
    for (i in grid.indices) {
        for (j in grid[0].indices) {
            if (grid[i][j] == 1) {
                //题目描述只有一个岛屿
                return dfs(grid, i, j)
            }
        }
    }
    return 0
}

fun dfs(grid: Array<IntArray>, r: Int, c: Int): Int {
    //从一个岛屿走向网格边界或者水域，周长加1
    if (
        r < 0 || r >= grid.size
        || c < 0 || c >= grid[0].size
        || grid[r][c] == 0
    ) {
        return 1
    }
    //遍历过的网格则不再遍历
    if (grid[r][c] == 2) {
        return 0
    }
    grid[r][c] = 2
    val left = dfs(grid, r, c - 1)  //左
    val up = dfs(grid, r - 1, c)    //上
    val right = dfs(grid, r, c + 1) //右
    val down = dfs(grid, r + 1, c)  //下
    return left + up + right + down
}