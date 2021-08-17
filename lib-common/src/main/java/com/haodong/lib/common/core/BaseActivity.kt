package com.haodong.lib.common.core

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.haodong.lib.common.util.StatusBarFontUtils
import com.haodong.lib.common.util.StatusBarUtil

/**
 * Author: tangyuan
 * Time : 2021/8/14
 * Description:
 */
abstract class BaseActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        beforeSetContentView(savedInstanceState)
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResId())
        initView()
        initData()
    }

    abstract fun getLayoutResId(): Int
    abstract fun initView()
    abstract fun initData()
    var isFull=true
    open fun beforeSetContentView(savedInstanceState: Bundle?){
        setFull()
        initStatus()
    }
    open fun setFull(){

    }
     open fun initStatus() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val decoderView = window.decorView
            val option = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                    or View.SYSTEM_UI_FLAG_IMMERSIVE)
            decoderView.systemUiVisibility = option
            window.statusBarColor = Color.TRANSPARENT
        } else {
            window.addFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
            )
        }
        if (isFull) {
            StatusBarUtil.setTransparent(this)
        }
        StatusBarFontUtils.setStatusBarMode(this, true)
    }


}