package io.github.haodongling.lib.common.core

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.haodongling.lib.common.App

/**
 * Author: tangyuan
 * Time : 2021/8/14
 * Description:
 */
abstract class BaseVMFragment<T: ViewDataBinding>(@LayoutRes val layoutId: Int) : Fragment(layoutId) {

     lateinit var mContext: AppCompatActivity
    val mFragmentProvider: ViewModelProvider by lazy {
        ViewModelProvider(this)
    }

    val mActivityProvider: ViewModelProvider by lazy{
        ViewModelProvider(mContext)
    }
    val mApplicationProvider: ViewModelProvider by lazy {
        ViewModelProvider(mContext.applicationContext as App, getApplicationFactory(mContext))
    }
    lateinit var mRootView:View

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = activity as AppCompatActivity
    }

    // tip 1: DataBinding 严格模式（详见 DataBindingFragment - - - - - ）：
    // 将 DataBinding 实例限制于 base 页面中，默认不向子类暴露，
    // 通过这样的方式，来彻底解决 视图实例 null 安全的一致性问题，
    // 如此，视图实例 null 安全的安全性将和基于函数式编程思想的 Jetpack Compose 持平。
    // tip 2: Jetpack 通过 "工厂模式" 来实现 ViewModel 的作用域可控，
    //目前我们在项目中提供了 Application、Activity、Fragment 三个级别的作用域，
    //值得注意的是，通过不同作用域的 Provider 获得的 ViewModel 实例不是同一个，
    //所以如果 ViewModel 对状态信息的保留不符合预期，可以从这个角度出发去排查 是否眼前的 ViewModel 实例不是目标实例所致。
    protected open fun <T : ViewModel> getFragmentScopeViewModel(modelClass: Class<T>): T {
        return mFragmentProvider.get(modelClass)
    }

    protected open fun <T : ViewModel> getActivityScopeViewModel(modelClass: Class<T>): T {
        return mActivityProvider.get(modelClass)

    }
    protected open fun <T : ViewModel> getApplicationScopeViewModel(modelClass: Class<T>): T {

        return mApplicationProvider.get(modelClass)
    }

     open fun getApplicationFactory(activity: Activity): ViewModelProvider.Factory {
         activity.let {
             it.application.let { application->
                 return ViewModelProvider.AndroidViewModelFactory.getInstance(application)
             }
         }
    }

    lateinit var mBinding:T

    protected  fun < T : ViewDataBinding> binding(
            inflater: LayoutInflater,
            @LayoutRes layoutId: Int,
            container: ViewGroup?
    ): T =   DataBindingUtil.inflate<T>(inflater,layoutId, container,false).apply {
        lifecycleOwner = this@BaseVMFragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = binding(inflater,layoutId,container)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setVariable()
        initView()
        startObserve()
        initData()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
    abstract fun setVariable()
    abstract fun initView()
    abstract fun initData()
    abstract fun startObserve()
}