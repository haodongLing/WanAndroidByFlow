package io.github.haodongling.lib.common.behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.google.android.material.appbar.AppBarLayout;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;


/**
 * 解决AppBarLayout 下拉不流畅的问题，在support 包26版本有解决
 */

public final class AppFlingBehavior extends AppBarLayout.Behavior {

//    private static final int TOP_CHILD_FLING_THRESHOLD = 1;
//    private static final float OPTIMAL_FLING_VELOCITY = 10000;
//    private static final float MIN_FLING_VELOCITY = 10;

//    boolean shouldFling = false;
//    float flingVelocityY = 0;

    private static final int TOP_CHILD_FLING_THRESHOLD = 3;
    private boolean isPositive;

    public AppFlingBehavior() {
    }

    public AppFlingBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

//    @Override
//    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target,
//                                  int velocityX, int velocityY, int[] consumed) {
//
//        super.onNestedPreScroll(coordinatorLayout, child, target, velocityX, velocityY, consumed);
//
//        if (velocityY > MIN_FLING_VELOCITY) {
//            shouldFling = true;
//            flingVelocityY = velocityY;
//        } else {
//            shouldFling = false;
//        }
//    }

//    @Override
//    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout abl, View target) {
//        super.onStopNestedScroll(coordinatorLayout, abl, target);
////        if (shouldFling) {
////            onNestedFling(coordinatorLayout, abl, target, 0, flingVelocityY, true);
////        }
//    }


    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, int dx, int dy, int[] consumed, int type) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
//    }
//
//    @Override
//    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, int dx, int dy, int[] consumed) {
//        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
        isPositive = dy > 0;
    }

    @Override
    public boolean onNestedFling(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target,
                                 float velocityX, float velocityY, boolean consumed) {

//        if (target instanceof RecyclerView && velocityY < 0) {
//            final RecyclerView recyclerView = (RecyclerView) target;
//            final View firstChild = recyclerView.getChildAt(0);
//            final int childAdapterPosition = recyclerView.getChildAdapterPosition(firstChild);
//            consumed = childAdapterPosition > TOP_CHILD_FLING_THRESHOLD;
//        }
//
//        // prevent fling flickering when going up
//        if (target instanceof NestedScrollView && velocityY > 0) {
//            consumed = true;
//        }
//
//
//
//        if (Math.abs(velocityY) < OPTIMAL_FLING_VELOCITY) {
//            velocityY = OPTIMAL_FLING_VELOCITY * (velocityY < 0 ? -1 : 1);
//        }
//
//        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);


        if (velocityY > 0 && !isPositive || velocityY < 0 && isPositive) {
            velocityY = velocityY * -1;
        }
        if (target instanceof RecyclerView && velocityY < 0) {
            final RecyclerView recyclerView = (RecyclerView) target;
            final View firstChild = recyclerView.getChildAt(0);
            final int childAdapterPosition = recyclerView.getChildAdapterPosition(firstChild);
            consumed = childAdapterPosition > TOP_CHILD_FLING_THRESHOLD;
        }
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
    }
}
