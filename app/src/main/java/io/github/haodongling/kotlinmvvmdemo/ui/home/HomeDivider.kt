package io.github.haodongling.kotlinmvvmdemo.ui.home

import android.content.Context
import io.github.haodongling.kotlinmvvmdemo.R
import io.github.haodongling.lib.common.App
import io.github.haodongling.lib.ui.decoration.Divider
import io.github.haodongling.lib.ui.decoration.DividerBuilder
import io.github.haodongling.lib.ui.decoration.DividerItemDecoration
import io.github.haodongling.lib.ui.decoration.SideLine

import io.github.haodongling.lib.utils.UIUtils

/**
 * Author: tangyuan
 * Time : 2021/9/2
 * Description:
 */
class HomeDivider(context: Context) : DividerItemDecoration(context) {
    override fun getDivider(itemPosition: Int): Divider? {
        return DividerBuilder(
            bottomSideLine = SideLine(
                true,
                App.APP.resources.getColor(R.color.light_gray),
                1,
                UIUtils.dp2px(mContext, 15F),
                UIUtils.dp2px(mContext, 15F)
            )
        ).create()
    }
}