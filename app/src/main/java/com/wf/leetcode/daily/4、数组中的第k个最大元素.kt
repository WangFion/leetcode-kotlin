package com.wf.leetcode.daily

import java.util.*

/**
 * leetcode -> com.wf.leetcode.daily -> `4、数组中的第k个最大元素`
 *
 * @Author: wf-pc
 * @Date: 2020-06-29 09:32
 * -------------------------
 * 215题
 * 在未排序的数组中找到第 k 个最大的元素。请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。
 *
 * 示例 1:
 * 输入: [3,2,1,5,6,4] 和 k = 2
 * 输出: 5
 *
 * 示例 2:
 * 输入: [3,2,3,1,2,4,5,5,6] 和 k = 4
 * 输出: 4
 *
 * 说明:
 * 你可以假设 k 总是有效的，且 1 ≤ k ≤ 数组的长度。
 */
fun main() {
    val arr1 = intArrayOf(3, 2, 1, 5, 6, 4)
    println(findKthLargest(arr1, 2))
    println(findKthLargest2(arr1, 2))
    println(findKthLargest3(arr1, 2))

    val arr2 = intArrayOf(3, 2, 3, 1, 2, 4, 5, 5, 6)
    println(findKthLargest(arr2, 4))
    println(findKthLargest2(arr2, 4))
    println(findKthLargest3(arr2, 4))
}

/**
 * plan1:排序再查找
 *
 * 时间复杂度：O(NlogN)，这里 N 是数组的长度，算法的性能消耗主要在排序，JDK 默认使用快速排序。
 * 空间复杂度：O(1)，这里是原地排序，没有借助额外的辅助空间。
 */
fun findKthLargest(nums: IntArray, k: Int): Int {
    Arrays.sort(nums)
    return nums[nums.size - k]
}

/**
 * plan2:基于快速排序的选择方法
 *
 * 「快速选择」算法：
 * 在分解的过程当中，我们会对子数组进行划分，如果划分得到的 q 正好就是我们需要的下标，就直接返回 a[q]；
 * 否则，如果 q 比目标下标小，就递归右子区间，否则递归左子区间。
 * 这样就可以把原来递归两个区间变成只递归一个区间，提高了时间效率。
 *
 * 时间复杂度：O(N)，如上文所述，证明过程可以参考「《算法导论》9.2：期望为线性的选择算法」。
 * 空间复杂度：O(logN)，递归使用栈空间的空间代价的期望为 O(logN)。
 */
fun findKthLargest2(nums: IntArray, k: Int): Int {
    return quickSelect(nums, 0, nums.size - 1, nums.size - k);
}

fun quickSelect(nums: IntArray, left: Int, right: Int, index: Int): Int {
    val q = randomPartition(nums, left, right)
    return when {
        q == index -> {
            nums[q]
        }
        q < index -> {
            quickSelect(nums, q + 1, right, index)
        }
        else -> {
            quickSelect(nums, left, q - 1, index)
        }
    }
}

fun randomPartition(a: IntArray, left: Int, right: Int): Int {
    //1.在范围内随机一个基准点
    val point = Random().nextInt(right - left + 1) + left
    //2.将基准点调整到末尾
    swap(a, point, right)
    //3.返回区域划分后的基准点下标
    return partition(a, left, right)
}

fun partition(a: IntArray, left: Int, right: Int): Int {
    val pivot = a[right]
    var i = left
    for (j in left until right) {
        if (a[j] <= pivot) {
            swap(a, i++, j)
        }
    }
    swap(a, i, right)
    return i
}

/**
 * plan2:基于堆排序的选择方法
 *
 * 建立一个大根堆，做 k−1 次删除操作后堆顶元素就是我们要找的答案。
 * 时间复杂度：O(NlogN)，建堆的时间代价是 O(n)，删除的总代价是 O(KlogN)，因为 K<N，故渐进时间复杂为 O(N+KlogN)=O(NlogN)。
 * 空间复杂度：O(logN)，即递归使用栈空间的空间代价。
 */
fun findKthLargest3(nums: IntArray, k: Int): Int {
    var heapSize = nums.size
    buildMaxHeap(nums, heapSize)
    for (i in nums.size - 1 downTo nums.size - k + 1) {
        swap(nums, 0, i);
        --heapSize
        maxHeapify(nums, 0, heapSize)
    }
    return nums[0]
}

fun buildMaxHeap(a: IntArray, heapSize: Int) {
    for (i in heapSize / 2 downTo 0) {
        maxHeapify(a, i, heapSize)
    }
}

fun maxHeapify(a: IntArray, i: Int, heapSize: Int) {
    val l = i * 2 + 1
    val r = i * 2 + 2
    var largest = i
    if (l < heapSize && a[l] > a[largest]) {
        largest = l;
    }
    if (r < heapSize && a[r] > a[largest]) {
        largest = r;
    }
    if (largest != i) {
        swap(a, i, largest);
        maxHeapify(a, largest, heapSize);
    }
}


/**
 * 数据交换
 */
fun swap(nums: IntArray, i: Int, j: Int) {
    val temp = nums[i]
    nums[i] = nums[j]
    nums[j] = temp
}



