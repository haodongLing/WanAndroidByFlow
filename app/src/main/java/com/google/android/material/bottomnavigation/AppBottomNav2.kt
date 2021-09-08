package com.google.android.material.bottomnavigation

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Color
import android.text.TextUtils
import android.util.AttributeSet
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import io.github.haodongling.kotlinmvvmdemo.R
import io.github.haodongling.lib.common.model.bean.BottomBar
import io.github.haodongling.lib.common.model.bean.Destination
import io.github.haodongling.lib.common.util.AppConfig

/**
 * Author: tangyuan
 * Time : 2021/9/8
 * Description:
 */

@SuppressLint("WrongConstant", "RestrictedApi")
class AppBottomNav2 : BottomNavigationView {
    lateinit var icons: IntArray
    var config: BottomBar = AppConfig.sBottombar

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val typedArray: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.AppBottomNav)
        val arrayResId = typedArray.getResourceId(R.styleable.AppBottomNav_abn_icons, 0)
        if (arrayResId != 0) {
            val resArray: TypedArray = resources.obtainTypedArray(arrayResId)
            icons=IntArray(resArray.length())
            for (i: Int in 0 until resArray.length()) {
                icons[i] = resArray.getResourceId(i, 0)
            }
            resArray.recycle()
        }
        typedArray.recycle()
        val state = Array(2) { IntArray(2) }
        state[0] = IntArray(android.R.attr.state_selected)
        state[1] = IntArray(1)
        val colors = intArrayOf(Color.parseColor(config.activeColor), Color.parseColor(config.inActiveColor))
        val stateList = ColorStateList(state, colors)
        itemTextColor = stateList
        itemIconTintList = stateList
        labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED
        val tabs = config.tabs
        for (tab: BottomBar.Tab in tabs) {
            if (!tab.enable) {
                continue
            }
            val itemId = getItemId(tab.pageUrl)
            if (itemId < 0) {
                continue
            }
            val menuItem = menu.add(0, itemId, tab.index, tab.title)
            menuItem.setIcon(icons[tab.index])
        }


        //此处给按钮icon设置大小
        var index = 0
        for (tab in config.tabs) {
            if (!tab.enable) {
                continue
            }
            val itemId = getItemId(tab.pageUrl)
            if (itemId < 0) {
                continue
            }
            val iconSize: Int = dp2Px(tab.size)
            val menuView: BottomNavigationMenuView = getChildAt(0) as BottomNavigationMenuView
            val itemView: BottomNavigationItemView = menuView.getChildAt(index) as BottomNavigationItemView
            itemView.setIconSize(iconSize)
            if (TextUtils.isEmpty(tab.title)) {
                val tintColor =
                    if (TextUtils.isEmpty(tab.tintColor)) Color.parseColor("#ff678f") else Color.parseColor(tab.tintColor)
                itemView.setIconTintList(ColorStateList.valueOf(tintColor))
                //禁止掉点按时 上下浮动的效果
                itemView.setShifting(false)
                /**
                 * 如果想要禁止掉所有按钮的点击浮动效果。
                 * 那么还需要给选中和未选中的按钮配置一样大小的字号。
                 *
                 * 在MainActivity布局的AppBottomBar标签增加如下配置，
                 * @style/active，@style/inActive 在style.xml中
                 * app:itemTextAppearanceActive="@style/active"
                 * app:itemTextAppearanceInactive="@style/inActive"
                 */
            }
            index++
        }

        //底部导航栏默认选中项

        //底部导航栏默认选中项
        if (config.selectTab != 0) {
            val selectTab = config.tabs[config.selectTab]
            if (selectTab.enable) {
                val itemId = getItemId(selectTab.pageUrl)
                //这里需要延迟一下 再定位到默认选中的tab
                //因为 咱们需要等待内容区域,也就NavGraphBuilder解析数据并初始化完成，
                //否则会出现 底部按钮切换过去了，但内容区域还没切换过去
                post { selectedItemId = itemId }
            }
        }

    }

    fun getItemId(pageUrl: String): Int {
        val destination: Destination? = AppConfig.sDestConfig.get(pageUrl)
        return destination?.id ?: -1
    }

    private fun dp2Px(dpValue: Int): Int {
        val metrics = context.resources.displayMetrics
        return (metrics.density * dpValue + 0.5f).toInt()
    }
}