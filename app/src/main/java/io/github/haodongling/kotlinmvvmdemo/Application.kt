package io.github.haodongling.kotlinmvvmdemo

import com.didichuxing.doraemonkit.DoKit
import com.tencent.mmkv.MMKV
import io.github.haodongling.lib.common.App

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
        MMKV.initialize(this.filesDir.absolutePath + "/mmkv")
    }
}