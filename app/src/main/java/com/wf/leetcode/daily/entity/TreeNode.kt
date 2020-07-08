package com.wf.leetcode.daily.entity

/**
 * leetcode -> com.wf.leetcode.daily.entity -> TreeNode
 *
 * @Author: wf-pc
 * @Date: 2020-07-07 16:03
 */
class TreeNode(var value: Int, var left: TreeNode?, var right: TreeNode?) {

    constructor(value: Int) : this(value, null, null)
}