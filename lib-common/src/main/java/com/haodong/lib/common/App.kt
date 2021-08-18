package com.haodong.lib.common

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.haodong.lib.common.model.bean.User
import kotlin.properties.Delegates

/**
 * Author: tangyuan
 * Time : 2021/8/13
 * Description:
 */
abstract class App:Application(), ViewModelStoreOwner{
    private lateinit var mAppViewModelStore: ViewModelStore
    companion object{
        var CONTEXT: Context by Delegates.notNull()
        lateinit var CURRENT_USER: User

    }

    override fun onCreate() {
        super.onCreate()

        mAppViewModelStore = ViewModelStore()
        CONTEXT=applicationContext
    }

    override fun getViewModelStore(): ViewModelStore {
       return mAppViewModelStore
    }
}