package com.wf.leetcode.daily_04

/**
 * leetcode -> com.wf.leetcode.daily_04 -> `34、N皇后`
 *
 * @Author: wf-pc
 * @Date: 2020-09-03 10:22
 * -------------------------
 * 51题
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/n-queens
 * n 皇后问题研究的是如何将 n 个皇后放置在 n×n 的棋盘上，并且使皇后彼此之间不能相互攻击。
 * 提示：皇后彼此不能相互攻击，也就是说：任何两个皇后都不能处于同一条横行、纵行或斜线上。
 * 给定一个整数 n，返回所有不同的 n 皇后问题的解决方案。
 * 每一种解法包含一个明确的 n 皇后问题的棋子放置方案，该方案中 'Q' 和 '.' 分别代表了皇后和空位。
 *
 * 示例：
 * 输入：4
 * 输出：
 * [
 *   [
 *     ".Q..",  // 解法 1
 *     "...Q",
 *     "Q...",
 *     "..Q."
 *   ],
 *   [
 *     "..Q.",  // 解法 2
 *     "Q...",
 *     "...Q",
 *     ".Q.."
 *   ]
 * ]
 * 解释: 4 皇后问题存在两个不同的解法。
 */
fun main() {
    val result = solveNQueens(4)
    println(result.size)
    for (list in result) {
        for (str in list) {
            println(str)
        }
        println()
    }
}

/**
 * plan1：回溯算法
 *
 * 时间复杂度：O(N!)，其中 N 是皇后数量。
 * 空间复杂度：O(N)，其中 N 是皇后数量。由于使用位运算表示，因此存储皇后信息的空间复杂度是 O(1)，空间复杂度
 *           主要取决于递归调用层数和记录每行放置的皇后的列下标的数组，递归调用层数不会超过 N，数组的长度为 N。
 */
fun solveNQueens(n: Int): List<List<String>> {
    val chess = Array(n) { CharArray(n) }
    for (i in 0 until n) {
        for (j in 0 until n) {
            chess[i][j] = '.'
        }
    }
    val result = ArrayList<ArrayList<String>>()
    solve(result, chess, 0)
    return result
}

fun solve(result: ArrayList<ArrayList<String>>, chess: Array<CharArray>, row: Int) {
    if (row == chess.size) {
        result.add(arrToList(chess))
        return
    }
    for (col in chess.indices) {
        if (valid(chess, row, col)) {
            chess[row][col] = 'Q'
            solve(result, chess, row + 1)
            chess[row][col] = '.'
        }
    }
}

fun arrToList(chess: Array<CharArray>): ArrayList<String> {
    val result = ArrayList<String>()
    for (c in chess) {
        result.add(String(c))
    }
    return result
}

fun valid(chess: Array<CharArray>, row: Int, col: Int): Boolean {
    //检查当前列有没有皇后
    for (i in 0 until row) {
        if (chess[i][col] == 'Q') {
            return false
        }
    }

    //检查左上对角线有没有皇后
    var i = row - 1
    var j = col - 1
    while (i >= 0 && j >= 0) {
        if (chess[i][j] == 'Q') {
            return false
        }
        i--
        j--
    }

    //检查右上对角线有没有皇后
    i = row - 1
    j = col + 1
    while (i >= 0 && j < chess.size) {
        if (chess[i][j] == 'Q') {
            return false
        }
        i--
        j++
    }
    return true
}