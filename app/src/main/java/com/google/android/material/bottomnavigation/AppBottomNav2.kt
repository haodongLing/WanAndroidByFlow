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


        //???????????????icon????????????
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
                //?????????????????? ?????????????????????
                itemView.setShifting(false)
                /**
                 * ?????????????????????????????????????????????????????????
                 * ???????????????????????????????????????????????????????????????????????????
                 *
                 * ???MainActivity?????????AppBottomBar???????????????????????????
                 * @style/active???@style/inActive ???style.xml???
                 * app:itemTextAppearanceActive="@style/active"
                 * app:itemTextAppearanceInactive="@style/inActive"
                 */
            }
            index++
        }

        //??????????????????????????????

        //??????????????????????????????
        if (config.selectTab != 0) {
            val selectTab = config.tabs[config.selectTab]
            if (selectTab.enable) {
                val itemId = getItemId(selectTab.pageUrl)
                //???????????????????????? ???????????????????????????tab
                //?????? ??????????????????????????????,??????NavGraphBuilder?????????????????????????????????
                //??????????????? ???????????????????????????????????????????????????????????????
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