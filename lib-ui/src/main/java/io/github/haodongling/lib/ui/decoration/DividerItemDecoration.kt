package io.github.haodongling.lib.ui.decoration

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView

/**
 * Author: tangyuan
 * Time : 2021/9/2
 * Description:
 */
abstract class DividerItemDecoration(context: Context) : RecyclerView.ItemDecoration() {
    val mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val mContext = context

    init {
        mPaint.style = Paint.Style.FILL
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
//        super.onDraw(c, parent, state)
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val itemPosition = (child.layoutParams as RecyclerView.LayoutParams).viewLayoutPosition

            val divider = getDivider(itemPosition) ?: DividerBuilder().create()
            if (divider.leftSideLine.isHave) {
                val lineWidthPx: Int = divider.leftSideLine.width
                val startPaddingPx: Int = divider.leftSideLine.startPadding
                val endPaddingPx: Int = divider.leftSideLine.endPadding
                drawChildLeftVertical(
                    child,
                    c,
                    parent,
                    divider.leftSideLine.color,
                    lineWidthPx,
                    startPaddingPx,
                    endPaddingPx
                )
            }
            if (divider.topSideLine.isHave) {
                val lineWidthPx: Int = divider.topSideLine.width
                val startPaddingPx: Int = divider.topSideLine.startPadding
                val endPaddingPx: Int = divider.topSideLine.endPadding
                drawChildTopHorizontal(
                    child,
                    c,
                    parent,
                    divider.topSideLine.color,
                    lineWidthPx,
                    startPaddingPx,
                    endPaddingPx
                )
            }
            if (divider.rightSideLine.isHave) {
                val lineWidthPx: Int = divider.rightSideLine.width
                val startPaddingPx: Int = divider.rightSideLine.startPadding
                val endPaddingPx: Int = divider.rightSideLine.endPadding
                drawChildRightVertical(
                    child,
                    c,
                    parent,
                    divider.rightSideLine.color,
                    lineWidthPx,
                    startPaddingPx,
                    endPaddingPx
                )
            }
            if (divider.bottomSideLine.isHave) {
                val lineWidthPx: Int = divider.bottomSideLine.width
                val startPaddingPx: Int = divider.bottomSideLine.startPadding
                val endPaddingPx: Int = divider.bottomSideLine.endPadding
                drawChildBottomHorizontal(
                    child,
                    c,
                    parent,
                    divider.bottomSideLine.color,
                    lineWidthPx,
                    startPaddingPx,
                    endPaddingPx
                )
            }
        }

    }

    private fun drawChildBottomHorizontal(
        child: View,
        c: Canvas,
        parent: RecyclerView,
        @ColorInt color: Int,
        lineWidthPx: Int,
        startPaddingPx: Int,
        endPaddingPx: Int
    ) {
        var leftPadding = 0
        var rightPadding = 0
        leftPadding = if (startPaddingPx <= 0) -lineWidthPx else startPaddingPx
        rightPadding = if (endPaddingPx <= 0) lineWidthPx else -endPaddingPx
        val params = (child.layoutParams as RecyclerView.LayoutParams)
        val left = child.left - params.leftMargin + leftPadding
        val right = child.right + params.rightMargin + rightPadding
        val top = child.bottom + params.bottomMargin
        val bottom = top + lineWidthPx
        mPaint.color = color
        c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), mPaint)
    }

    private fun drawChildTopHorizontal(
        child: View,
        c: Canvas,
        parent: RecyclerView,
        @ColorInt color: Int,
        lineWidthPx: Int,
        startPaddingPx: Int,
        endPaddingPx: Int
    ) {
        var leftPadding = 0
        var rightPadding = 0
        leftPadding = if (startPaddingPx <= 0) {
            //padding<0??????==0??????
            //????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
            -lineWidthPx
        } else {
            startPaddingPx
        }
        rightPadding = if (endPaddingPx <= 0) {
            lineWidthPx
        } else {
            -endPaddingPx
        }
        val params = child
            .layoutParams as RecyclerView.LayoutParams
        val left = child.left - params.leftMargin + leftPadding
        val right = child.right + params.rightMargin + rightPadding
        val bottom = child.top - params.topMargin
        val top = bottom - lineWidthPx
        mPaint.color = color
        c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), mPaint)
    }

    private fun drawChildLeftVertical(
        child: View,
        c: Canvas,
        parent: RecyclerView,
        @ColorInt color: Int,
        lineWidthPx: Int,
        startPaddingPx: Int,
        endPaddingPx: Int
    ) {
        var topPadding = 0
        var bottomPadding = 0
        topPadding = if (startPaddingPx <= 0) {
            //padding<0??????==0??????
            //????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
            -lineWidthPx
        } else {
            startPaddingPx
        }
        bottomPadding = if (endPaddingPx <= 0) {
            lineWidthPx
        } else {
            -endPaddingPx
        }
        val params = child
            .layoutParams as RecyclerView.LayoutParams
        val top = child.top - params.topMargin + topPadding
        val bottom = child.bottom + params.bottomMargin + bottomPadding
        val right = child.left - params.leftMargin
        val left = right - lineWidthPx
        mPaint.color = color
        c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), mPaint)
    }

    private fun drawChildRightVertical(
        child: View,
        c: Canvas,
        parent: RecyclerView,
        @ColorInt color: Int,
        lineWidthPx: Int,
        startPaddingPx: Int,
        endPaddingPx: Int
    ) {
        var topPadding = 0
        var bottomPadding = 0
        topPadding = if (startPaddingPx <= 0) {
            //padding<0??????==0??????
            //????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
            -lineWidthPx
        } else {
            startPaddingPx
        }
        bottomPadding = if (endPaddingPx <= 0) {
            lineWidthPx
        } else {
            -endPaddingPx
        }
        val params = child
            .layoutParams as RecyclerView.LayoutParams
        val top = child.top - params.topMargin + topPadding
        val bottom = child.bottom + params.bottomMargin + bottomPadding
        val left = child.right + params.rightMargin
        val right = left + lineWidthPx
        mPaint.color = color
        c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), mPaint)
    }


    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
//        super.getItemOffsets(outRect, view, parent, state)


        //outRect ??????????????????????????????Rect?????????outRect?????????????????????left,right,top,bottom???????????????,
        //????????????left,right,top,bottom?????????????????????
        val itemPosition = (view.layoutParams as RecyclerView.LayoutParams).viewLayoutPosition

        var divider: Divider = getDivider(itemPosition) ?: DividerBuilder().create()

        val left = if (divider.leftSideLine.isHave) divider.leftSideLine.width else 0
        val top = if (divider.topSideLine.isHave) divider.leftSideLine.width else 0
        val right = if (divider.rightSideLine.isHave) divider.leftSideLine.width else 0
        val bottom = if (divider.bottomSideLine.isHave) divider.leftSideLine.width else 0
        outRect[left, top, right] = bottom
    }

    abstract fun getDivider(itemPosition: Int): Divider?


}