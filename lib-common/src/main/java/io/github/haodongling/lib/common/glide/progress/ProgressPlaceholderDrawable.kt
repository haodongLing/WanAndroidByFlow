package io.github.haodongling.lib.common.glide.progress

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import androidx.annotation.IntRange
import androidx.core.content.ContextCompat

/**
 * Author: tangyuan
 * Time : 2021/11/30
 * Description:
 */
class ProgressPlaceholderDrawable(
    private var context: Context, private var placeHolderDrawable: Drawable? = null, placeHolderId: Int = 0
) : Drawable() {
    constructor(context: Context, placeHolderDrawable: Drawable?) : this(context, placeHolderDrawable,0)
    constructor(context: Context, placeHolderId: Int = 0) : this(context, null,placeHolderId)

    private var mProgress: Int = 0;
    private var mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mStartAngle = 270f;
    private val mPaintStrokeWidth = getDensity() * 1.5f
    private val mProgressPadding = getDensity() * 3f

    init {
        if (placeHolderDrawable == null && placeHolderId != 0) {
            placeHolderDrawable = ContextCompat.getDrawable(context, placeHolderId)
        }
        mPaint.color = Color.GRAY
        mPaint.strokeWidth = mPaintStrokeWidth;

    }

    override fun setBounds(bounds: Rect) {
        super.setBounds(bounds)
        placeHolderDrawable?.bounds = bounds
    }

    override fun setBounds(left: Int, top: Int, right: Int, bottom: Int) {
        super.setBounds(left, top, right, bottom)
        placeHolderDrawable?.setBounds(left, top, right, bottom)
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
        // draw original placeholder
        placeHolderDrawable?.draw(canvas)

        // calc center point
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

        // draw outline circle
        mPaint.style = Paint.Style.STROKE
        canvas.drawCircle(centerX, centerY, radius, mPaint)

        // draw progress
        mPaint.style = Paint.Style.FILL
        val endAngle = (mProgress / 100F) * 360F
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