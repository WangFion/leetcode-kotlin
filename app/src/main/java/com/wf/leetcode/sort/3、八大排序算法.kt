package com.wf.leetcode.sort

import kotlin.math.max
import kotlin.math.min

/**
 * leetcode -> com.wf.leetcode.sort -> `3、八大排序算法`
 *
 * @Author: wf-pc
 * @Date: 2020-07-08 11:23
 * -------------------------
 * 冒泡排序、选择排序、插入排序、希尔排序、快速排序、归并排序、基数排序、堆排序
 */
fun main() {
    val array1 = intArrayOf(9, 5, 16, 10, 24, 2, 78, 50, -9, 5, -20, 100)
    println("bubbleSort:${bubbleSort(array1).contentToString()}")

    val array2 = intArrayOf(9, 5, 16, 10, 24, 2, 78, 50, -9, 5, -20, 100)
    println("selectSort:${selectSort(array2).contentToString()}")

    val array3 = intArrayOf(9, 5, 16, 10, 24, 2, 78, 50, -9, 5, -20, 100)
    println("insertSort:${insertSort(array3).contentToString()}")

    val array4 = intArrayOf(9, 5, 16, 10, 24, 2, 78, 50, -9, 5, -20, 100)
    println("shellSort: ${shellSort(array4).contentToString()}")

    val array5 = intArrayOf(9, 5, 16, 10, 24, 2, 78, 50, -9, 5, -20, 100)
    println("quickSort: ${quickSort(array5).contentToString()}")

    val array6 = intArrayOf(9, 5, 16, 10, 24, 2, 78, 50, -9, 5, -20, 100)
    println("mergeSort: ${mergeSort(array6).contentToString()}")

    val array7 = intArrayOf(9, 5, 16, 10, 24, 2, 78, 50, -9, 5, -20, 100)
    println("radixSort: ${radixSort(array7).contentToString()}")

    val array8 = intArrayOf(9, 5, 16, 10, 24, 2, 78, 50, -9, 5, -20, 100)
    println("heapSort:  ${heapSort(array8).contentToString()}")
}


/**
 * 1、冒泡排序
 *
 * 它重复地走访过要排序的数列，一次比较两个元素，如果它们的顺序错误就把它们交换过来。
 * 以此类推，直到所有元素均排序完毕。
 * 时间复杂度：O(n^2)
 * 空间复杂度：O(1)
 */
fun bubbleSort(array: IntArray): IntArray {
    for (i in array.indices) {
        for (j in 0 until array.size - 1 - i) {
            if (array[j] > array[j + 1]) {
                swap(array, j, j + 1)
            }
        }
    }
    return array
}

/**
 * 2、选择排序
 *
 * 首先在未排序序列中找到最小（大）元素，存放到起始（末尾）位置，然后再从剩余未排序元素中继续寻找最小（大）元素，
 * 然后放到已排序序列的末尾。以此类推，直到所有元素均排序完毕。
 * 时间复杂度：O(n^2)
 * 空间复杂度：O(1)
 */
fun selectSort(array: IntArray): IntArray {
    for (i in 0..array.size - 2) {
        var minIndex = i
        for (j in i + 1 until array.size) {
            if (array[minIndex] > array[j]) {
                minIndex = j
            }
        }
        if (i != minIndex) {
            swap(array, i, minIndex)
        }
    }
    return array
}

/**
 * 3、插入排序（增量为1的希尔排序）
 *
 * 它的工作原理是通过构建有序序列，对于未排序数据，在已排序序列中从后向前扫描，找到相应位置并插入。
 * 时间复杂度：O(n^2)
 * 空间复杂度：O(1)
 */
fun insertSort(array: IntArray): IntArray {
    for (i in 1 until array.size) {
        var preIndex = i - 1
        val current = array[i]
        while (preIndex >= 0 && array[preIndex] > current) {
            array[preIndex + 1] = array[preIndex]
            preIndex--
        }
        array[preIndex + 1] = current
    }
    return array
}

/**
 * 4、希尔排序
 *
 * 也称递减增量排序算法，是插入排序的一种更高效的改进版本。但希尔排序是非稳定排序算法。
 * 基本思想是：先确定一个增量将整个待排序的序列分割成为若干子序列（逻辑子序列），分别进行直接插入排序；
 *            然后不断缩小增量，待整个序列中的记录“基本有序”时，再对全体记录进行依次直接插入排序。
 * 时间复杂度：O(n^(1.3-2))，希尔排序的复杂度和增量序列是相关的
 *     {1,2,4,8,...}是O(n^2)
 *     Hibbard提出了另一个增量序列{1,3,7，...,2^k-1}为O(n^1.5)
 *     Sedgewick提出了几种增量序列，其最坏情形运行时间为O（n^1.3）,其中最好的一个序列是{1,5,19,41,109,...}
 * 空间复杂度：O(1)
 */
fun shellSort(array: IntArray): IntArray {
    // 增量每次缩小一半
    var gap = array.size
    while (gap > 0) {
        for (i in gap until array.size) {
            //对逻辑分组进行插入排序
            insertShell(array, gap, i)
        }
        gap /= 2
    }
    return array
}

fun insertShell(array: IntArray, gap: Int, index: Int) {
    val current = array[index]
    var preIndex = index - gap
    while (preIndex >= 0 && array[preIndex] > current) {
        array[preIndex + gap] = array[preIndex]
        preIndex -= gap
    }
    array[preIndex + gap] = current
}

/**
 * 5、快速排序
 *
 * 选择一个基准点，通过一趟排序将待排序序列分隔成独立的两部分，比基准点小的放到它前面，比基准点大的放到它后面，
 * 然后分别对这两部分继续进行排序，直到整个序列有序。
 * 算法步骤：
 * （1）从数列中挑出一个元素，称为 “基准”（pivot）
 * （2）将所有比基准值小的元素摆放在基准值前面，所有比基准值大的元素摆在基准值的后面（相同的数可以到任一边）
 * （3）递归地（recursive）把小于基准值元素的子数列和大于基准值元素的子数列排序
 *
 * 时间复杂度：O(nlogn)
 * 空间复杂度：O(logn)
 */
fun quickSort(array: IntArray): IntArray {
    quick(array, 0, array.size - 1)
    return array
}

fun quick(array: IntArray, start: Int, end: Int) {
    if (start > end) return
    val base = array[start]
    var i = start
    var j = end
    while (i != j) {
        //从后向前找到比基准点小的
        while (j > i && array[j] >= base) {
            j--
        }
        //从前向后找到比基准点大的
        while (i < j && array[i] <= base) {
            i++
        }
        if (i < j) {
            swap(array, i, j)
        }
    }
    swap(array, start, i)
    quick(array, 0, i - 1)
    quick(array, i + 1, end)
}

/**
 * 6、归并排序
 *
 * 先把一个未排序的序列从中间分割成2部分，再把2部分分成4部分，依次分割下去，直到分割成一个一个的数据，
 * 再把这些数据两两归并到一起，使之有序，不停的归并，最后成为一个排好序的序列。
 * 三个步骤：
 * 分解（Divide）：将 n 个元素分成个含 n/2 个元素的子序列。
 * 解决（Conquer）：用合并排序法对两个子序列递归的排序。
 * 合并（Combine）：合并两个已排序的子序列已得到排序结果。
 *
 *    [6 4 8 2]
 *    /      \
 * [6 4]   [8 2]
 *   |       |
 * [4 6]   [2 8]
 *   \      /
 *  [2 4 6 8]
 *
 * 平均时间复杂度：O(nlogn)
 * 最佳时间复杂度：O(n)
 * 最差时间复杂度：O(nlogn)
 * 空间复杂度：O(n)
 */
fun mergeSort(array: IntArray): IntArray {
    split(array, 0, array.size - 1)
    return array
}

//拆分
fun split(array: IntArray, start: Int, end: Int) {
    if (start == end) {
        return
    }
    val center = (start + end) / 2
    split(array, start, center)
    split(array, center + 1, end)
    merge(array, start, end)
}

//合并
fun merge(array: IntArray, start: Int, end: Int) {
    val tmpArr = IntArray(end - start + 1)
    val center = (start + end) / 2
    var l = start
    var r = center + 1
    var i = 0
    //才分成两半，进行归并
    while (l <= center && r <= end) {
        if (array[l] <= array[r]) {
            tmpArr[i++] = array[l++]
        } else {
            tmpArr[i++] = array[r++]
        }
    }
    //长度为奇数，则必有一方有剩余，合并剩余元素
    while (l <= center) {
        tmpArr[i++] = array[l++]
    }
    while (r <= end) {
        tmpArr[i++] = array[r++]
    }
    //将排序好的结果放回原数组
    for (j in tmpArr.indices) {
        array[start + j] = tmpArr[j]
    }
}

/**
 * 7、基数排序
 *
 * 基数排序的思想就是先排好个位，然后排好个位的基础上排十位，以此类推，直到遍历最高位次，排序结束（仔细理解最后一句话）
 * 基数排序不是比较排序，而是通过分配和收集的过程来实现排序，基数排序只能排正数，如有负数可以修正为正数，排序完成后再还原回去。
 * （1）初始化 10 个桶(固定的)，桶下标为 0-9
 * （2）通过得到待排序数字的个十百等位的数字，把这个数字对应的item放到对应的桶中
 * （3）基数排序有两种排序方式：LSD 和 MSD，最小位优先(从右边开始)和最大位优先(从左边开始)
 *
 * 时间复杂度：平均、最好、最坏都为 O(k*n),其中 k 为常数，n 为元素个数
 * 空间复杂度：O(n+k)
 * 稳定性：稳定
 */
fun radixSort(array: IntArray): IntArray {
    //如果数组中有负数，将整个数组修正为正数
    val minValue = findMinValue(array)
    if (minValue < 0) {
        correctArray(array, -minValue)
    }
    //获取数组中元素的最大位数
    val maxDigit = findMaxDigit(array)

    val bucket = Array(10) { IntArray(array.size) }
    val capacity = IntArray(10)//用来记录每一个桶中有多少元素
    var divisor = 1//1，10，100...
    for (i in 1..maxDigit) {
        //按每位数的值分别放入桶中
        for (value in array) {
            val radix = (value / divisor) % 10
            bucket[radix][capacity[radix]] = value
            capacity[radix]++
        }
        divisor *= 10
        //还原回原数组
        var index = 0
        for (j in capacity.indices) {
            if (capacity[j] != 0) {//表示桶中有数据
                for (k in 0 until capacity[j]) {
                    array[index] = bucket[j][k]
                    index++
                }
                capacity[j] = 0
            }
        }
    }
    //如果数组中有负数，则还原修正值
    if (minValue < 0) {
        correctArray(array, minValue)
    }
    return array
}

//查找数组中最大数的位数
fun findMaxDigit(array: IntArray): Int {
    var maxDigit = 0
    for (value in array) {
        maxDigit = max(maxDigit, value.toString().length)
    }
    return maxDigit
}

//寻找数组中的最小值
fun findMinValue(array: IntArray): Int {
    var minValue = 0
    for (value in array) {
        minValue = min(minValue, value)
    }
    return minValue
}

//修正数组
fun correctArray(array: IntArray, correct: Int): IntArray {
    for (i in array.indices) {
        array[i] += correct
    }
    return array
}

/**
 * 8、堆排序
 *
 * 堆是具有以下性质的完全二叉树：
 * 每个结点的值都大于或等于其左右孩子结点的值，称为大顶堆，大顶堆用于升序排序；
 * 或者每个结点的值都小于或等于其左右孩子结点的值，称为小顶堆，小顶堆用于降序排序。
 * 算法步骤：
 * （1）创建一个堆 H[0……n-1]；
 * （2）把堆首（最大值）和堆尾互换；
 * （3）把堆的尺寸缩小 1，并重新构建成堆；
 * （4）重复步骤 2，直到堆的尺寸为 1 则排序完成。
 *
 * 时间复杂度：O(nlogn)
 * 空间复杂度：O(1)
 */
fun heapSort(array: IntArray): IntArray {
    //将无序数组构建成堆，从最后一个非叶子节点开始
    for (i in array.size / 2 - 1 downTo 0) {
        buildHeap(array, i, array.size)
    }
    //交换堆顶数据，并重新构建成堆，size逐渐减小
    for (j in array.size - 1 downTo 0) {
        swap(array, 0, j)
        buildHeap(array, 0, j)
    }
    return array
}

fun buildHeap(array: IntArray, index: Int, size: Int) {
    val left = 2 * index + 1
    val right = 2 * index + 2
    var maxIndex = index
    //找出当前节点和左右子节点中的最大值
    if (left < size && array[maxIndex] < array[left]) {
        maxIndex = left
    }
    if (right < size && array[maxIndex] < array[right]) {
        maxIndex = right
    }
    if (maxIndex != index) {
        //交换最大值，使当前节点成为子大顶堆
        swap(array, maxIndex, index)
        //交换后可能会影响子节点，递归的将子堆调整为大顶堆
        buildHeap(array, maxIndex, size)
    }
}

fun swap(array: IntArray, x: Int, y: Int) {
    val temp = array[x]
    array[x] = array[y]
    array[y] = temp
}
 