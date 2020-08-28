package com.wf.leetcode.daily_02

import java.util.*

/**
 * leetcode -> com.wf.leetcode.daily_02 -> `20、被围绕的区域`
 *
 * @Author: wf-pc
 * @Date: 2020-08-11 11:50
 * -------------------------
 * 130题：https://leetcode-cn.com/problems/surrounded-regions/
 * 给定一个二维的矩阵，包含 'X' 和 'O'（字母 O）。
 * 找到所有被 'X' 围绕的区域，并将这些区域里所有的 'O' 用 'X' 填充。
 *
 * 示例:
 * X X X X
 * X O O X
 * X X O X
 * X O X X
 *
 * 运行你的函数后，矩阵变为：
 * X X X X
 * X X X X
 * X X X X
 * X O X X
 *
 * 解释:
 * 被围绕的区间不会存在于边界上，换句话说，任何边界上的 'O' 都不会被填充为 'X'。
 * 任何不在边界上，或不与边界上的 'O' 相连的 'O' 最终都会被填充为 'X'。
 * 如果两个元素在水平或垂直方向相邻，则称它们是“相连”的。
 */
fun main() {
    val area = arrayOf(
        charArrayOf('X', 'X', 'X', 'X'),
        charArrayOf('X', 'O', 'O', 'X'),
        charArrayOf('X', 'X', 'O', 'X'),
        charArrayOf('X', 'O', 'X', 'X')
    )
    //solve(area)
    solve2(area)
    for (i in area.indices) {
        for (j in area[i].indices) {
            print("${area[i][j]} ")
        }
        println()
    }
}

/**
 * plan1：深度优先
 * 本题给定的矩阵中有三种元素：
 * 1、字母 X；
 * 2、被字母 X 包围的字母 O；
 * 3、没有被字母 X 包围的字母 O。
 *
 * 本题要求将所有被字母 X 包围的字母 O都变为字母 X ，但很难判断哪些 O 是被包围的，哪些 O 不是被包围的。
 * 注意到题目解释中提到：任何边界上的 O 都不会被填充为 X。 我们可以想到，所有的不被包围的 O 都直接或间接
 * 与边界上的 O 相连。我们可以利用这个性质判断 O 是否在边界上，具体地说：
 *     1、对于每一个边界上的 O，我们以它为起点，标记所有与它直接或间接相连的字母 O，标记为 #；
 *     2、最后我们遍历这个矩阵，对于每一个字母：
 *         （1）如果该字母被标记过，则该字母没有被字母 X 包围，我们将其还原为字母 O；
 *         （2）如果该字母没有被标记过，则该字母被字母 X 包围，我们将其修改为字母 X。
 *
 * 时间复杂度：O(n×m)，其中 n 和 m 分别为矩阵的行数和列数。深度优先搜索过程中，每一个点至多只会被标记一次。
 * 空间复杂度：O(n×m)，其中 n 和 m 分别为矩阵的行数和列数。主要为深度优先搜索的栈的开销。
 */
fun solve(board: Array<CharArray>) {
    if (board.isEmpty() || board[0].isEmpty()) {
        return
    }
    for (i in board.indices) {
        dfs(board, i, 0)//左边界
        dfs(board, i, board[0].size - 1)//右边界
    }
    for (i in board[0].indices) {
        dfs(board, 0, i)//上边界
        dfs(board, board.size - 1, i)//下边界
    }
    for (i in board.indices) {
        for (j in board[i].indices) {
            if (board[i][j] == '#') {
                board[i][j] = 'O'
            } else if (board[i][j] == 'O') {
                board[i][j] = 'X'
            }
        }
    }
}

fun dfs(board: Array<CharArray>, x: Int, y: Int) {
    if (x < 0 || x >= board.size || y < 0 || y >= board[0].size || board[x][y] != 'O') {
        return
    }
    board[x][y] = '#'
    dfs(board, x - 1, y)//上
    dfs(board, x + 1, y)//下
    dfs(board, x, y - 1)//左
    dfs(board, x, y + 1)//右
}

/**
 * plan2：广度优先
 * 时间复杂度：O(n×m)，其中 n 和 m 分别为矩阵的行数和列数。广度优先搜索过程中，每一个点至多只会被标记一次。
 * 空间复杂度：O(n×m)，其中 n 和 m 分别为矩阵的行数和列数。主要为广度优先搜索的队列的开销。
 */
fun solve2(board: Array<CharArray>) {
    if (board.isEmpty() || board[0].isEmpty()) {
        return
    }
    bfs(board)
    for (i in board.indices) {
        for (j in board[i].indices) {
            if (board[i][j] == '#') {
                board[i][j] = 'O'
            } else if (board[i][j] == 'O') {
                board[i][j] = 'X'
            }
        }
    }
}

fun bfs(board: Array<CharArray>) {
    val queue = LinkedList<Pair<Int, Int>>()
    for (i in board.indices) {
        if (board[i][0] == 'O') {
            queue.offer(Pair(i, 0))//左边界
        }
        if (board[i][board[i].size - 1] == 'O') {
            queue.offer(Pair(i, board[i].size - 1))//右边界
        }
    }

    for (i in board[0].indices) {
        if (board[0][i] == 'O') {
            queue.offer(Pair(0, i))//上边界
        }
        if (board[board.size - 1][i] == 'O') {
            queue.offer(Pair(board.size - 1, i))//下边界
        }
    }

    while (queue.isNotEmpty()) {
        val pair = queue.poll()
        board[pair.first][pair.second] = '#'
        //上
        var x = pair.first - 1
        var y = pair.second
        if (x >= 0 && x < board.size && y >= 0 && y < board[0].size && board[x][y] == 'O') {
            queue.offer(Pair(x, y))
        }
        //下
        x = pair.first + 1
        y = pair.second
        if (x >= 0 && x < board.size && y >= 0 && y < board[0].size && board[x][y] == 'O') {
            queue.offer(Pair(x, y))
        }
        //左
        x = pair.first
        y = pair.second - 1
        if (x >= 0 && x < board.size && y >= 0 && y < board[0].size && board[x][y] == 'O') {
            queue.offer(Pair(x, y))
        }
        //右
        x = pair.first
        y = pair.second + 1
        if (x >= 0 && x < board.size && y >= 0 && y < board[0].size && board[x][y] == 'O') {
            queue.offer(Pair(x, y))
        }
    }
}
