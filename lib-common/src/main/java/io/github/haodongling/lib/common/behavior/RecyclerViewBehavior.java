package io.github.haodongling.lib.common.behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.appbar.AppBarLayout;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Author: tangyuan
 * Time : 2021/6/8
 * Description:
 */
public class RecyclerViewBehavior extends AppBarLayout.ScrollingViewBehavior {
    RecyclerView recyclerView;

    public RecyclerViewBehavior() {
    }

    public RecyclerViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onInterceptTouchEvent(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!onTouchedSelf(child, ev)) {
                    if (recyclerView == null) {
                        recyclerView = (RecyclerView) getNestedScrollingChild(child);
                    }
                    if (recyclerView != null) {
//                        FFLog.i();
                        recyclerView.stopNestedScroll();
                        recyclerView.stopScroll();
                    }
                }
                break;
        }
        return super.onInterceptTouchEvent(parent, child, ev);


    }

    private boolean onTouchedSelf(View view, MotionEvent ev) {
        int[] selfRect = new int[2];
        view.getLocationInWindow(selfRect);
        return ev.getRawY() > selfRect[1];
    }

    private RecyclerView getNestedScrollingChild(View parentView) {
        if (parentView != null) {
            if (parentView instanceof RecyclerView) {
                return (RecyclerView) parentView;
            } else if (parentView instanceof ViewGroup) {
                for (int i = 0; i < ((ViewGroup) parentView).getChildCount(); i++) {
                    if (((ViewGroup) parentView).getChildAt(i) instanceof ViewGroup) {
                        return getNestedScrollingChild(((ViewGroup) parentView).getChildAt(i));
                    }
                }
                return null;
            }
        }
        return null;
    }

}
