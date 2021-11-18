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
    companion object {
        lateinit var appViewModel: AppViewModel
    }

    override fun onCreate() {
        super.onCreate()
        MMKV.initialize(this.filesDir.absolutePath + "/mmkv")
        appViewModel = getAppViewModelProvider().get(AppViewModel::class.java)
        DoKit.Builder(this).build()
    }
}

val appViewModel: AppViewModel by lazy { Application.appViewModel }