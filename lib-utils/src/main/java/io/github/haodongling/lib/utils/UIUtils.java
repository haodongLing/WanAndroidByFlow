package io.github.haodongling.lib.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Method;


/**
 * UI Util类
 */
public class UIUtils {

    public static final <E extends TextView> String getViewText(Activity activity, int id) {
        return ((E) activity.findViewById(id)).getText().toString().trim();
    }

    public static final <E extends TextView> String getViewText(E view) {
        try {
            return view.getText().toString().trim();
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return "";
    }

//    public static final <E extends TextView> boolean isViewTextEmpty(E view)
//    {
//        return TextUtils.isEmpty(getViewText(view));
//    }

    public static final <E extends View> E findView(Activity activity, int id) {
        return findView(activity.getWindow().getDecorView(), id);
    }

    @SuppressWarnings("unchecked")
    public static final <E extends View> E findView(View view, int id) {
        try {
            return (E) view.findViewById(id);
        } catch (ClassCastException e) {
            throw e;
        }
    }


    @SuppressWarnings("deprecation")
    public static int getScreenWidth(Activity ctx) {
        int width;
        Display display = ctx.getWindowManager().getDefaultDisplay();
        try {
            Method mGetRawW = Display.class.getMethod("getRawWidth");
            width = (Integer) mGetRawW.invoke(display);
        } catch (Exception e) {

            width = display.getWidth();
        }
        return width;
    }

    @SuppressWarnings("deprecation")
    public static int getScreenHeight(Activity ctx) {
        int height;
        Display display = ctx.getWindowManager().getDefaultDisplay();
        try {
            Method mGetRawH = Display.class.getMethod("getRawHeight");
            height = (Integer) mGetRawH.invoke(display);
        } catch (Exception e) {
            height = display.getHeight();
        }
        return height;
    }



    /**
     * dip转px
     *
     * @param ctx
     * @param dp
     * @return
     */
    public static int dipToPx(final Context ctx, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, ctx.getApplicationContext().getResources().getDisplayMetrics());
    }

    /**
     * convert dp to its equivalent px
     * <p>
     * 将dp转换为与之相等的px
     */
    public static int dp2px(final Context ctx,float dipValue) {
        final float scale = ctx.getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
    /**
     * convert px to its equivalent dp
     * <p>
     * 将px转换为与之相等的dp
     */
    public static int px2dp(final Context ctx,float pxValue) {
        final float scale =ctx.getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static float sp2px(final Context context,float sp) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return sp * scale;
    }

    /**
     * px转dip
     *
     * @param ctx
     * @param px
     * @return
     */
    public static int pxTodip(final Context ctx, float px)
    {
        float scale = ctx.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    /**
     * 计算ListView高度
     *
     * @param listView
     */
    public static int setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return 0;
        }

        int totalHeight = 0;
        int count = listAdapter.getCount();
        for (int i = 0; i < count; i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        //return  params;
        return totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
    }


    /**
     * @param mContext
     * @param listView
     * @param padding  listview item 的距离左右边距
     * @return
     */
    public static int setListViewHeightBasedOnChildren(Activity mContext, ListView listView, int padding) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return 0;
        }

//        DisplayMetrics dm =getResources().getDisplayMetrics();
//        int w_screen = dm.widthPixels;

        int totalHeight = 0;
        int listViewWidth = UIUtils.getScreenWidth(mContext) - UIUtils.dipToPx(mContext, padding);                                         //listView在布局时的宽度
        int widthSpec = View.MeasureSpec.makeMeasureSpec(listViewWidth, View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(widthSpec, 0);

            int itemHeight = listItem.getMeasuredHeight();
            totalHeight += itemHeight;
        }
        // 减掉底部分割线的高度
        int historyHeight = totalHeight
                + (listView.getDividerHeight() * listAdapter.getCount() - 1);

        return historyHeight;

    }




    public static SpannableString multiSizeString(String format, int textSize, int start, int end, int defaultSize) {
        SpannableString title = new SpannableString(format);
        title.setSpan(new AbsoluteSizeSpan(defaultSize, true), 0, format.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        title.setSpan(new AbsoluteSizeSpan(textSize, true), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return title;
    }



    public static SpannableString colorString(String format, int start, int end, int color) {
        SpannableString title = new SpannableString(format);
        title.setSpan(new ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return title;
    }




    public static boolean checkBits(int status, int checkBit) {
        return (status & checkBit) == checkBit;
    }



    public static int getImageHeight(float imageWidth, float height, float width) {
        int imageHeight = (int) (imageWidth * (height / width));
        return imageHeight;
    }



    public static int getTextWidth(Context Context, String text, int textSize) {
        TextPaint paint = new TextPaint();
        float scaledDensity = Context.getResources().getDisplayMetrics().scaledDensity;
        paint.setTextSize(scaledDensity * textSize);
        return (int) paint.measureText(text);
    }



    /**
     * 获取状态栏高度
     * ！！这个方法来自StatusBarUtil,因为作者将之设为private，所以直接copy出来
     *
     * @param context context
     * @return 状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        // 获得状态栏高度
        int resourceId = context.getApplicationContext()
                .getResources().getIdentifier(
                        "status_bar_height", "dimen",
                        "android");
        return context.getApplicationContext()
                .getResources().getDimensionPixelSize(resourceId);
    }

    public static Typeface getTypeFace(Context context, String fileName) {
        Typeface typeFace = Typeface.createFromAsset(
                context.getApplicationContext().getResources().getAssets(),
                "fonts/" + fileName);
        return typeFace;

    }

    public static int getLines(Context Context, int textSize, String text, int arrowWidth) {
        TextPaint paint = new TextPaint();
        float scaledDensity = Context.getResources().getDisplayMetrics().scaledDensity;
        paint.setTextSize(scaledDensity * textSize);
        float textLength = paint.measureText(text);
        return textLength % arrowWidth > 0 ? (int)textLength / arrowWidth + 1 : (int)textLength / arrowWidth;

    }
    /**
     * 给color添加透明度
     * @param alpha 透明度 0f～1f
     * @param baseColor 基本颜色
     * @return
     */
    public static int getColorWithAlpha(float alpha, int baseColor) {
        int a = Math.min(255, Math.max(0, (int) (alpha * 255))) << 24;
        int rgb = 0x00ffffff & baseColor;
        return a + rgb;
    }

}
