package io.github.haodongling.lib.common.glide.progress

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import androidx.annotation.IntRange
import androidx.core.content.ContextCompat

/**
 * Author: tangyuan
 * Time : 2021/11/26
 * Description:
 */
class ProgressPlaceholderDrawable(
    private var context: Context, private var placeholderDrawable: Drawable? = null, placeHolderId: Int = 0
) : Drawable() {
    private var mProgress: Int = 0
    private var mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mStartAngle = 270F
    private val mPaintStrokeWidth = getDensity() * 1.5F
    private val mProgressPadding = getDensity() * 3F

    init {
        if (placeholderDrawable == null && placeHolderId != 0) {
            placeholderDrawable = ContextCompat.getDrawable(context, placeHolderId)
        }
        mPaint.color = Color.GRAY
        mPaint.strokeWidth = mPaintStrokeWidth
    }

    override fun setBounds(bounds: Rect) {
        super.setBounds(bounds)
        placeholderDrawable?.bounds = bounds
    }

    override fun setBounds(left: Int, top: Int, right: Int, bottom: Int) {
        super.setBounds(left, top, right, bottom)
        placeholderDrawable?.setBounds(left, top, right, bottom)
    }

    override fun setTint(tintColor: Int) {
        super.setTint(tintColor)
        mPaint.color = tintColor
    }

    fun setProgress(@IntRange(from = 0, to = 100) progress: Int) {
        mProgress = progress
        invalidateSelf()
    }

    override fun draw(canvas: Canvas) {
        placeholderDrawable?.draw(canvas)

        val centerX = (bounds.width() ushr 1).toFloat()
        val centerY = (bounds.height() ushr 1).toFloat()
        var radius = (bounds.width().coerceAtMost(bounds.height()) ushr 1).toFloat()
        // calc radius
        val dp30 = getDensity() * 30
        if (radius > dp30 * 1.25F) {
            radius = dp30
        } else {
            radius *= 0.8F
        }
        mPaint.style = Paint.Style.STROKE
        canvas.drawCircle(centerX, centerY, radius, mPaint)

        mPaint.style = Paint.Style.FILL
        val endAngle = (mProgress / 100f) * 360f
        val rect = RectF(
            centerX - radius + mProgressPadding,
            centerY - radius + mProgressPadding,
            centerX + radius - mProgressPadding,
            centerY + radius - mProgressPadding
        )
        canvas.drawArc(rect, mStartAngle, endAngle, true, mPaint)

    }

    override fun setAlpha(alpha: Int) {
        mPaint.alpha = alpha
        invalidateSelf()
    }


    override fun setColorFilter(colorFilter: ColorFilter?) {
        mPaint.colorFilter = colorFilter
        invalidateSelf()
    }

    override fun getOpacity() = PixelFormat.TRANSLUCENT

    private fun getDensity() = context.resources.displayMetrics.density


}