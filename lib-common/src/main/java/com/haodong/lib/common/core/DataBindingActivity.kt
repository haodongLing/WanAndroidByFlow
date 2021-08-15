package com.haodong.lib.common.core

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * Author: tangyuan
 * Time : 2021/8/14
 * Description:
 */
public abstract class DataBindingActivity :AppCompatActivity() {
    protected inline fun <reified T : ViewDataBinding> binding(
        @LayoutRes resId: Int
    ): Lazy<T> = lazy { DataBindingUtil.setContentView<T>(this, resId).apply {
        lifecycleOwner = this@DataBindingActivity
    } }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setVariable()
        initView()
        startObserve()
        initData()
    }

    abstract fun initData()

    abstract fun initView()

    abstract fun startObserve()

    abstract fun setVariable()

}