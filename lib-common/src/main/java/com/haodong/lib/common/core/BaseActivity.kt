package com.haodong.lib.common.core

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Author: tangyuan
 * Time : 2021/8/14
 * Description:
 */
abstract class BaseActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResId())
        initView()
        initData()
    }

    abstract fun getLayoutResId(): Int
    abstract fun initView()
    abstract fun initData()

}