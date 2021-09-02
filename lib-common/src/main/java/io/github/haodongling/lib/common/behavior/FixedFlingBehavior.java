package io.github.haodongling.lib.common.behavior;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.OverScroller;

import com.google.android.material.appbar.AppBarLayout;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

/**
 * created by tangyuan on 2021/5/19
 * description: CoordinatorLayout  嵌套recyclerView 滑动动画抖动
 */
public class FixedFlingBehavior extends AppBarLayout.Behavior {
    private OverScroller mOverScroller;

    public FixedFlingBehavior() {
        super();
    }

    public FixedFlingBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        setDragCallback(new DragCallback() {
            @Override
            public boolean canDrag(@NonNull @NotNull AppBarLayout appBarLayout) {
//                FFLog.i("appBarLayout");
                return true;
            }
        });
    }

    @Override
    public void onAttachedToLayoutParams(@NonNull CoordinatorLayout.LayoutParams params) {
        super.onAttachedToLayoutParams(params);
    }

    @Override
    public void onDetachedFromLayoutParams() {
        super.onDetachedFromLayoutParams();
    }

    @Override
    public boolean onTouchEvent(CoordinatorLayout parent, AppBarLayout child, MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            reflectOverScroller();
        }
        return super.onTouchEvent(parent, child, ev);
    }

    /**
     *
     */
    public void stopFling() {
        if (mOverScroller != null) {
            mOverScroller.abortAnimation();
        }
    }

    /**
     * 解决AppbarLayout在fling的时候，再主动滑动RecyclerView导致的动画错误的问题
     */
    private void reflectOverScroller() {
        if (mOverScroller == null) {
            Field field = null;
            try {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
                    field = getClass().getSuperclass()
                            .getSuperclass().getDeclaredField("mScroller");
                    field.setAccessible(true);
                    Object object = field.get(this);
                    mOverScroller = (OverScroller) object;
                } else {
                    field = getClass().getSuperclass().getSuperclass().getSuperclass().getDeclaredField("scroller");
                    field.setAccessible(true);
                    Object object = field.get(this);
                    mOverScroller = (OverScroller) object;
                }

            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
