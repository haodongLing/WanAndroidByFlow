package io.github.haodongling.lib.common.core

import android.app.Activity
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.haodongling.lib.common.App
import qiu.niorgai.StatusBarCompat

/**
 * Author: tangyuan
 * Time : 2021/8/14
 * Description:
 */
 abstract class BaseVMActivity<T:ViewDataBinding> :AppCompatActivity() {

    lateinit var  mBinding: T
    val mActivityProvider: ViewModelProvider by lazy{
        ViewModelProvider(this)
    }
    val mApplicationProvider: ViewModelProvider by lazy {
        ViewModelProvider(this.applicationContext as App, getApplicationFactory(this))
    }
    open fun getApplicationFactory(activity: Activity): ViewModelProvider.Factory {
        activity.let {
            it.application.let { application->
                return ViewModelProvider.AndroidViewModelFactory.getInstance(application)
            }
        }
    }

    protected open fun <T : ViewModel> getActivityScopeViewModel(modelClass: Class<T>): T {
        return mActivityProvider.get(modelClass)

    }
    protected open fun <T : ViewModel> getApplicationScopeViewModel(modelClass: Class<T>): T {

        return mApplicationProvider.get(modelClass)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        initBefore()
        super.onCreate(savedInstanceState)
        mBinding=  DataBindingUtil.setContentView<T>(this, getLayoutId()).apply {
            lifecycleOwner = this@BaseVMActivity
        }
        setStatusBar()
        setVariable()
        initView()
        startObserve()
        initData()
    }
   open fun initBefore(){}

    abstract fun getLayoutId():Int
    abstract fun setVariable()
    abstract fun initView()
    abstract fun initData()

    abstract fun startObserve()
    open fun setStatusBar(){
        //透明状态栏
        StatusBarCompat.translucentStatusBar(this@BaseVMActivity);
    }


}