package com.wf.leetcode.daily_05

/**
 * leetcode -> com.wf.leetcode.daily_05 -> `41、解数独`
 *
 * @Author: wf-pc
 * @Date: 2020-09-15 09:14
 * -------------------------
 * 37题
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/sudoku-solver
 *
 * 编写一个程序，通过已填充的空格来解决数独问题。
 * 一个数独的解法需遵循如下规则：
 *     数字 1-9 在每一行只能出现一次。
 *     数字 1-9 在每一列只能出现一次。
 *     数字 1-9 在每一个以粗实线分隔的 3x3 宫内只能出现一次。
 * 空白格用 '.' 表示。
 *
 * 一个数独：
 * 5  3  .  .  7  .  .  .  .
 * 6  .  .  1  9  5  .  .  .
 * .  9  8  .  .  .  .  6  .
 * 8  .  .  .  6  .  .  .  3
 * 4  .  .  8  .  3  .  .  1
 * 7  .  .  .  2  .  .  .  6
 * .  6  .  .  .  .  2  8  .
 * .  .  .  4  1  9  .  .  5
 * .  .  .  .  8  .  .  7  9
 *
 * 一个解：
 * 5  3  4  6  7  8  9  1  2
 * 6  7  2  1  9  5  3  4  8
 * 1  9  8  3  4  2  5  6  7
 * 8  5  9  7  6  1  4  2  3
 * 4  2  6  8  5  3  7  9  1
 * 7  1  3  9  2  4  8  5  6
 * 9  6  1  5  3  7  2  8  4
 * 2  8  7  4  1  9  6  3  5
 * 3  4  5  2  8  6  1  7  9
 *
 * Note:
 *     给定的数独序列只包含数字 1-9 和字符 '.' 。
 *     你可以假设给定的数独只有唯一解。
 *     给定数独永远是 9x9 形式的。
 */
fun main() {
    val board1 = arrayOf(
        charArrayOf('5', '3', '.', '.', '7', '.', '.', '.', '.'),
        charArrayOf('6', '.', '.', '1', '9', '5', '.', '.', '.'),
        charArrayOf('.', '9', '8', '.', '.', '.', '.', '6', '.'),
        charArrayOf('8', '.', '.', '.', '6', '.', '.', '.', '3'),
        charArrayOf('4', '.', '.', '8', '.', '3', '.', '.', '1'),
        charArrayOf('7', '.', '.', '.', '2', '.', '.', '.', '6'),
        charArrayOf('.', '6', '.', '.', '.', '.', '2', '8', '.'),
        charArrayOf('.', '.', '.', '4', '1', '9', '.', '.', '5'),
        charArrayOf('.', '.', '.', '.', '8', '.', '.', '7', '9')
    )
    val board2 = board1.copyOf()

    solveSudoku(board1)
    for (arr in board1) {
        println(arr.contentToString())
    }
    println()

    solveSudoku2(board2)
    for (arr in board2) {
        println(arr.contentToString())
    }
}

/**
 * plan1：回溯
 * 定义三个数组分别记录某个数字已经在行、列和九宫格中出现的位置
 * row[1][3]=true：表示数字 4（3+1） 在第 2 行已经出现过
 * col[1][3]=true：表示数字 4（3+1） 在第 2 列已经出现过
 * block[1][1][3]：表示数字 4（3+1） 在第 2 行，第 2 列的九宫格中已经出现过
 */
fun solveSudoku(board: Array<CharArray>) {
    val row = Array(9) { BooleanArray(9) }
    val col = Array(9) { BooleanArray(9) }
    val block = Array(3) { Array(3) { BooleanArray(9) } }
    val space = ArrayList<Pair<Int, Int>>()
    for (i in 0 until 9) {
        for (j in 0 until 9) {
            if (board[i][j] == '.') {
                space.add(Pair(i, j))
            } else {
                //digit为 0-8，使用的时候需要+1
                val digit = board[i][j] - '0' - 1
                row[i][digit] = true
                col[j][digit] = true
                block[i / 3][j / 3][digit] = true
            }
        }
    }
    dfs(board, 0, row, col, block, space)
}

fun dfs(
    board: Array<CharArray>,
    pos: Int,
    row: Array<BooleanArray>,
    col: Array<BooleanArray>,
    block: Array<Array<BooleanArray>>,
    space: ArrayList<Pair<Int, Int>>
): Boolean {
    if (pos == space.size) {
        return true
    }
    val i = space[pos].first
    val j = space[pos].second
    for (digit in 0 until 9) {
        //在行、列、九宫格中均未出现过，则可以填入
        if (!row[i][digit] && !col[j][digit] && !block[i / 3][j / 3][digit]) {
            row[i][digit] = true
            col[j][digit] = true
            block[i / 3][j / 3][digit] = true
            board[i][j] = (digit + 1 + '0'.toInt()).toChar()

            //找到一个满足的解，则结束执行
            if (dfs(board, pos + 1, row, col, block, space)) {
                return true
            }

            //回溯
            board[i][j] = '.'
            row[i][digit] = false
            col[j][digit] = false
            block[i / 3][j / 3][digit] = false
        }
    }
    //1-9 都不满足要求，则回溯到上一层，然后进行下一个数字探测
    return false
}

/**
 * plan：位运算优化
 * 在方法一中，我们使用了长度为 9 的数组表示每个数字是否出现过。
 * 我们同样也可以借助位运算，仅使用一个整数表示每个数字是否出现过。
 */
fun solveSudoku2(board: Array<CharArray>) {
    val row = IntArray(9)
    val col = IntArray(9)
    val block = Array(3) { IntArray(3) }
    val space = ArrayList<Pair<Int, Int>>()
    for (i in 0 until 9) {
        for (j in 0 until 9) {
            if (board[i][j] == '.') {
                space.add(Pair(i, j))
            } else {
                //digit为 0-8，使用的时候需要+1
                val digit = board[i][j] - '0' - 1
                flip(i, j, digit, row, col, block)
            }
        }
    }
    dfs2(board, 0, row, col, block, space)
}

fun dfs2(
    board: Array<CharArray>,
    pos: Int,
    row: IntArray,
    col: IntArray,
    block: Array<IntArray>,
    space: ArrayList<Pair<Int, Int>>
): Boolean {
    if (pos == space.size) {
        return true
    }

    val i = space[pos].first
    val j = space[pos].second
    var mask = (row[i] or col[j] or block[i / 3][j / 3]).inv() and 0x1ff
    while (mask != 0) {
        val digitMask = mask and (-mask)
        val digit = Integer.bitCount(digitMask - 1)

        //试探
        flip(i, j, digit, row, col, block)
        board[i][j] = (digit + 1 + '0'.toInt()).toChar()

        //递归
        if (dfs2(board, pos + 1, row, col, block, space)) {
            return true
        }

        //回溯
        board[i][j] = '.'
        flip(i, j, digit, row, col, block);

        mask = mask and (mask - 1)
    }
    return false
}

fun flip(
    i: Int,
    j: Int,
    digit: Int,
    row: IntArray,
    col: IntArray,
    block: Array<IntArray>
) {
    row[i] = row[i] xor (1 shl digit)
    col[j] = col[j] xor (1 shl digit)
    block[i / 3][j / 3] = block[i / 3][j / 3] xor (1 shl digit)
}
