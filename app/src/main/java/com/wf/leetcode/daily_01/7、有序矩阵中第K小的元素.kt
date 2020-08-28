package com.wf.leetcode.daily_01

/**
 * leetcode -> com.wf.leetcode.daily_01 -> `7、有序矩阵中第K小的元素`
 *
 * @Author: wf-pc
 * @Date: 2020-07-02 09:05
 * -------------------------
 *
 * 378题：https://leetcode-cn.com/problems/kth-smallest-element-in-a-sorted-matrix/
 * 给定一个 n x n 矩阵，其中每行和每列元素均按升序排序，找到矩阵中第 k 小的元素。
 * 请注意，它是排序后的第 k 小元素，而不是第 k 个不同的元素。
 *
 * 示例：
 * matrix = [
 *     [ 1,  5,  9],
 *     [10, 11, 13],
 *     [12, 13, 15]
 * ],
 * k = 8,
 * 返回 13。
 *
 * 提示：
 * 你可以假设 k 的值永远是有效的，1 ≤ k ≤ n2 。
 */
fun main() {
    val matrix = arrayOf(
        intArrayOf(1, 5, 9),
        intArrayOf(10, 11, 13),
        intArrayOf(12, 13, 15)
    )
    println(kthSmallest(matrix, 8))
    println(kthSmallest2(matrix, 8))

    val matrix2 = arrayOf(
        intArrayOf(1, 2),
        intArrayOf(3, 3)
    )
    println(kthSmallest(matrix2, 2))
    println(kthSmallest2(matrix2, 2))

    val matrix3 = arrayOf(
        intArrayOf(1, 2),
        intArrayOf(1, 3)
    )
    println(kthSmallest(matrix3, 2))
    println(kthSmallest2(matrix3, 2))
}

/**
 * plan1:直接排序
 *
 * 时间复杂度：O(N^2logN)，对 N^2 个数排序。
 * 空间复杂度：O(N^2)，一维数组需要存储这 N^2 个数。
 */
fun kthSmallest(matrix: Array<IntArray>, k: Int): Int {
    val arr = IntArray(matrix.size * matrix[0].size)
    var index = 0
    for (i in matrix.indices) {
        for (j in matrix[i].indices) {
            arr[index] = matrix[i][j]
            index++
        }
    }
    arr.sort()
    return arr[k - 1]
}

/**
 * plan3:二分查找（app/src/main/img/有序矩阵中第k小的元素--二分查找.png）
 * 时间复杂度：O(nlog(r−l))，二分查找进行次数为 O(log(r−l))，每次操作时间复杂度为 O(n)。
 * 空间复杂度：O(1)。
 *
 * 矩阵内的元素是从左上到右下递增的，整个二维数组中 matrix[0][0] 为最小值，matrix[n−1][n−1] 为最大值
 * | 1  | 5  | 9  |
 * | 10 | 11 | 13 |
 * | 12 | 13 | 15 |
 * 可以这样描述走法：
 * (1)初始位置在 matrix[n−1][0]（即左下角）；
 * (2)设当前位置为 matrix[i][j]。若 midmatrix[i][j]≤mid，则将当前所在列的不大于 mid 的数的数量
 *   （即 i+1）累加到答案中，并向右移动，否则向上移动；
 * (3)不断移动直到走出格子为止。
 *
 * 即我们可以线性计算对于任意一个 mid，矩阵中有多少数不大于它。这满足了二分查找的性质。
 * 不妨假设答案为 x，那么可以知道 l≤x≤r，这样就确定了二分查找的上下界。
 * 每次对于「猜测」的答案 mid，计算矩阵中有多少数不大于 mid ：
 * (1)如果数量不少于 k，那么说明最终答案 x <= mid；
 * (2)如果数量少于 k，那么说明最终答案 x > mid。
 * 这样我们就可以计算出最终的结果 x 了。
 */
fun kthSmallest2(matrix: Array<IntArray>, k: Int): Int {
    val n = matrix.size
    var left = matrix[0][0]
    var right = matrix[n - 1][n - 1]
    while (left < right) {
        val mid = (left + right) / 2
        if (smallestNum(matrix, mid) >= k) {
            // 个数比k大，说明在左上角，故调整最大上限
            right = mid
        } else {
            // 个数比k小，说明在右上角，故调整最小下限
            left = mid + 1
        }
    }
    return left
}

/**
 * 计算值比mid小的个数
 */
fun smallestNum(matrix: Array<IntArray>, mid: Int): Int {
    var i = matrix.size - 1
    var j = 0
    var num = 0
    while (i >= 0 && j < matrix.size) {
        if (matrix[i][j] <= mid) {
            num += i + 1
            j++
        } else {
            i--
        }
    }
    return num
}
 