package io.github.haodongling.lib.ui.decoration

import androidx.annotation.ColorInt

/**
 * Author: tangyuan
 * Time : 2021/9/2
 * Description:
 */
class SideLine(isHave: Boolean, @ColorInt color: Int, width: Int, startPadding: Int, endPadding: Int) {
    var isHave = false
    var color: Int
    var width: Int
    var startPadding: Int
    var endPadding: Int

    init {
        this.isHave = isHave
        this.color = color
        this.width = width
        this.startPadding = startPadding
        this.endPadding = endPadding
    }
}