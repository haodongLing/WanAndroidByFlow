package com.haodong.lib.common

import android.app.Application
import android.content.Context
import androidx.databinding.library.BuildConfig
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.alibaba.android.arouter.launcher.ARouter
import com.didichuxing.doraemonkit.DoKit
import com.haodong.lib.common.model.bean.User
import com.haodong.lib.common.tasks.X5WebTask
import com.haodong.lib.dispatcher.launcher.TaskDispatcher
import kotlin.properties.Delegates

/**
 * Author: tangyuan
 * Time : 2021/8/13
 * Description:
 */
class App : Application(), ViewModelStoreOwner {
    private lateinit var mAppViewModelStore: ViewModelStore

    companion object {
        var CONTEXT: Context by Delegates.notNull()
        var APP: App by Delegates.notNull()
        lateinit var CURRENT_USER: User
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        CONTEXT = applicationContext
        APP = this
        mAppViewModelStore = ViewModelStore()
        DoKit.Builder(this)
            .build()

        initARouter()
        TaskDispatcher.init(this)
        val dispatcher = TaskDispatcher.createInstance()
        dispatcher
            .addTask(X5WebTask())
            .start()
        dispatcher.await()

    }

    /**
     * 阿里路由 ARouter 初始化
     */
    private fun initARouter(): String {
        // 测试环境下打开ARouter的日志和调试模式 正式环境需要关闭
        if (BuildConfig.DEBUG) {
            ARouter.openLog()     // 打印日志
            ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this@App)
        return "ARouter -->> init complete"
    }


    override fun getViewModelStore(): ViewModelStore {
        return mAppViewModelStore
    }
}