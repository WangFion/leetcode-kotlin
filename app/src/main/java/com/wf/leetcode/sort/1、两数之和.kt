package com.wf.leetcode.sort

/**
 * leetcode -> com.wf.leetcode.sort -> `1、两数之和`
 * @Author: wf-pc
 * @Date: 2020-06-20 16:43
 * ---------------------------------------
 *
 * 给定一个整数数组 num 和一个目标值 target，请你在该数组中找出和为目标值的那两个整数，并返回他们的数组下标。
 * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。
 * 示例:
 *     给定 num = [2, 7, 11, 15], target = 9
 *     因为 num[0] + num[1] = 2 + 7 = 9
 *     所以返回 [0, 1]
 */
fun main() {
    val num: Array<Int> = arrayOf(2, 7, 11, 15)
    val target = 9
    val index1 = planFor(num, target)
    println("[${index1[0]}, ${index1[1]}]")

    val index2 = planHash(num, target)
    println("[${index2[0]}, ${index2[1]}]")

    val arr = intArrayOf(2, 7, 11, 15)
    val index3 = planHash2(arr, target)
    println("[${index3[0]}, ${index3[1]}]")
}

/**
 * plan1:暴力循环法
 *
 * 时间复杂度：O(n^2)，对于每个元素，通过遍历其余部分来寻找它所对应的目标元素。
 * 空间复杂度：O(1)。
 */
fun planFor(num: Array<Int>, target: Int): Array<Int> {
    for (i in num.indices) {
        for (j in (i + 1) until num.size) {
            if (target == num[i] + num[j]) {
                return arrayOf(i, j)
            }
        }
    }
    throw IllegalArgumentException("No two sum solution")
}

/**
 * plan2:一遍哈希表
 *
 * 时间复杂度：O(n)，我们只遍历了包含有 n 个元素的列表一次，在表中进行的每次查找只花费 O(1)。
 * 空间复杂度：O(n)，所需的额外空间取决于哈希表中存储的元素数量，该表最多需要存储 n 个元素。
 */
fun planHash(num: Array<Int>, target: Int): Array<Int> {
    val map = HashMap<Int, Int>()
    for (index in num.indices) {
        val sub = target - num[index]
        if (map.containsKey(sub)) {
            return arrayOf(map[sub] ?: throw IllegalArgumentException("No two sum solution"), index)
        }
        map[num[index]] = index
    }
    throw IllegalArgumentException("No two sum solution")
}

fun planHash2(num: IntArray, target: Int): IntArray {
    val map = mutableMapOf<Int, Int>()
    for((index, value) in num.withIndex()){
        val sub = target - value
        if(map.containsKey(sub)){
            /*val result = IntArray(2)
            result[0] = map[sub] ?: throw IllegalArgumentException("No two sum solution")
            result[1] = index
            return result*/
            return intArrayOf(map[sub] ?: throw IllegalArgumentException("No two sum solution"), index)
        }
        map[value] = index
    }
    throw IllegalArgumentException("No two sum solution")
}
 