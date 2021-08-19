package com.google.android.material.appbar;

import android.content.Context;
import android.util.AttributeSet;

public class FixedCollapsingToolbarLayout extends CollapsingToolbarLayout {

    public FixedCollapsingToolbarLayout(Context context) {
        this(context, null);
    }

    public FixedCollapsingToolbarLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FixedCollapsingToolbarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        final int mode = MeasureSpec.getMode(heightMeasureSpec);
        final int topInset = lastInsets != null ? lastInsets.getSystemWindowInsetTop() : 0;
        if (mode == MeasureSpec.UNSPECIFIED && topInset > 0) {
            // fix the bottom empty padding
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(
                    getMeasuredHeight() - topInset, MeasureSpec.EXACTLY);
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
