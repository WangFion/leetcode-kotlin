package com.wf.leetcode.daily_02

/**
 * leetcode -> com.wf.leetcode.daily_02 -> `16、除数博弈`
 *
 * @Author: wf-pc
 * @Date: 2020-07-24 09:11
 * -------------------------
 * 1025题：https://leetcode-cn.com/problems/divisor-game/
 * 爱丽丝和鲍勃一起玩游戏，他们轮流行动。爱丽丝先手开局。
 * 最初，黑板上有一个数字 N 。在每个玩家的回合，玩家需要执行以下操作：
 * （1）选出任一 x，满足 0 < x < N 且 N % x == 0 。
 * （2）用 N - x 替换黑板上的数字 N 。
 * 如果玩家无法执行这些操作，就会输掉游戏。
 * 只有在爱丽丝在游戏中取得胜利时才返回 True，否则返回 false。假设两个玩家都以最佳状态参与游戏。
 *
 * 示例 1：
 * 输入：2
 * 输出：true
 * 解释：爱丽丝选择 1，鲍勃无法进行操作。
 *
 * 示例 2：
 * 输入：3
 * 输出：false
 * 解释：爱丽丝选择 1，鲍勃也选择 1，然后爱丽丝无法进行操作。
 *
 * 提示：1 <= N <= 1000
 */
fun main() {
    println(divisorGame(2))
    println(divisorGame(3))

    println(divisorGame2(2))
    println(divisorGame2(3))
}

/**
 * plan1：归纳法
 * 数字 N 如果是奇数，它的约数必然都是奇数；若为偶数，则其约数可奇可偶。
 * （1）无论N初始为多大的值，游戏最终只会进行到 N=2 时结束，那么谁轮到 N=2 时谁就会赢。
 * （2）因为爱丽丝先手，N初始若为偶数，爱丽丝则只需一直选 1，使鲍勃一直面临 N 为奇数的情况，这样爱丽丝稳赢；
 *      N初始若为奇数，那么爱丽丝第一次选完之后 N 必为偶数，那么鲍勃只需一直选 1 就会稳赢。
 * 综述，判断N是奇数还是偶数，即可得出最终结果！
 *
 * 时间复杂度：O(1)。
 * 空间复杂度：O(1)。
 */
fun divisorGame(N: Int): Boolean {
    return N % 2 == 0
}

/**
 * 动态规划
 *
 * 时间复杂度：O(n^2)。递推的时候一共有 n 个状态要计算，每个状态需要 O(n) 的时间枚举因数，因此总时间复杂度为 O(n^2)。
 * 空间复杂度：O(n)。我们需要 O(n) 的空间存储递推数组 fff 的值。
 */
fun divisorGame2(N: Int): Boolean {
    if (N == 1) {
        return false
    }
    //初始化数组
    val dp = BooleanArray(N + 1)
    dp[1] = false
    dp[2] = true
    for (i in 3..N) {
        //先初始化为false，符合条件再更新为true
        dp[i] = false
        //玩家都以最佳状态，即玩家操作i后的操作数i-x应尽可能使对手输，即dp[i-x]应尽可能为false
        //所以遍历x=1~i-1,寻找x的约数，使得dp[i-x]=false，那么dp[i]=true即当前操作数为i的玩家能获胜
        //如果找不到则为false，会输掉
        for (x in 1 until i) {
            if (i % x == 0 && !dp[i - x]) {
                dp[i] = true
                break
            }
        }
    }
    return dp[N]
}
 