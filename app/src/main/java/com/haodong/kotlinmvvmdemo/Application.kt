package com.haodong.kotlinmvvmdemo

import com.didichuxing.doraemonkit.DoKit
import com.haodong.lib.common.App

/**
 * Author: tangyuan
 * Time : 2021/8/27
 * Description:
 */
class Application : App() {
    override fun onCreate() {
        super.onCreate()
        DoKit.Builder(this)
            .build()
    }
}