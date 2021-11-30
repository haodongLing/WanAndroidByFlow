package io.github.haodongling.lib.common

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.alibaba.android.arouter.launcher.ARouter
import com.haodong.launch.dispatcher.TaskDispatcher
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.*
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import io.github.haodongling.lib.common.tasks.X5WebTask
import io.github.haodongling.lib.common.util.FFLog
import kotlin.properties.Delegates

/**
 * Author: tangyuan
 * Time : 2021/8/13
 * Description:
 */
open class App : Application(), ViewModelStoreOwner {
    init {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, _ -> ClassicsHeader(context) };
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, _ -> ClassicsFooter(context) };
    }

    private lateinit var mAppViewModelStore: ViewModelStore

    companion object {
        var CONTEXT: Context by Delegates.notNull()
        var APP: App by Delegates.notNull()

    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        CONTEXT = applicationContext
        APP = this
        mAppViewModelStore = ViewModelStore()

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
//        if (BuildConfig.DEBUG) {
//            ARouter.openLog()     // 打印日志
//            ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
//        }
//        ARouter.openLog()     // 打印日志
//        ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        ARouter.init(this@App)
        FFLog.i("ARouter -->> init complete")
        return "ARouter -->> init complete"
    }


    override fun getViewModelStore(): ViewModelStore {
        return mAppViewModelStore
    }
    private var mFactory: ViewModelProvider.Factory? = null
    /**
     * 获取一个全局的ViewModel
     */
    fun getAppViewModelProvider(): ViewModelProvider {
        return ViewModelProvider(this, this.getAppFactory())
    }
    private fun getAppFactory(): ViewModelProvider.Factory {
        if (mFactory == null) {
            mFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(this)
        }
        return mFactory as ViewModelProvider.Factory
    }

}