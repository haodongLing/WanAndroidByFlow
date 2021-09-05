package io.github.haodongling.lib.ui.decoration

import io.github.haodongling.lib.ui.R
import io.github.haodongling.lib.utils.global.AppGlobals

/**
 * Author: tangyuan
 * Time : 2021/9/2
 * Description:
 */
class DividerBuilder(
    leftSideLine: SideLine?=null,
    topSideLine: SideLine?=null,
    rightSideLine: SideLine?=null,
    bottomSideLine: SideLine?=null
) {
    private val defaultSideLine =
        SideLine(false, AppGlobals.getApplication().resources.getColor(R.color.ui_transparent), 0, 0, 0)
    var leftSideLine = leftSideLine ?: defaultSideLine
    var topSideLine = topSideLine ?: defaultSideLine
    var rightSideLine = rightSideLine ?: defaultSideLine
    var bottomSideLine = bottomSideLine ?: defaultSideLine
    open fun create(): Divider {
        return Divider(leftSideLine, topSideLine, rightSideLine, bottomSideLine)
    }
}