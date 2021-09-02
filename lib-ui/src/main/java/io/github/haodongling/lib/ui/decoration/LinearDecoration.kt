package io.github.haodongling.lib.ui.decoration

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import io.github.haodongling.lib.utils.UIUtils

/**
 * Author: tangyuan
 * Time : 2021/9/1
 * Description:
 */
class LinearDecoration() :
    RecyclerView.ItemDecoration() {

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)

    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom = 1
        outRect.left = UIUtils.dp2px(view.context, 15F)
        outRect.right = UIUtils.dp2px(view.context, 15F)
    }
}