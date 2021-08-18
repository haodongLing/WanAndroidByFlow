package com.haodong.kotlinmvvmdemo.ui.login

import android.app.ProgressDialog
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.haodong.kotlinmvvmdemo.R
import com.haodong.kotlinmvvmdemo.databinding.ActivityLoginBinding
import com.haodong.kotlinmvvmdemo.model.bean.Title
import com.haodong.lib.common.core.BaseVMActivity
import com.haodong.lib.common.ext.toast
import com.haodong.lib.common.global.BizConst
import com.haodong.lib.common.util.FFLog

/**
 * Author: tangyuan
 * Time : 2021/8/17
 * Description:
 */
@Route(path = BizConst.LOGIN)
class LoginActivity : BaseVMActivity<ActivityLoginBinding>() {

    val loginViewModel by lazy {
        getActivityScopeViewModel(LoginViewModel::class.java)
    }

    override fun initData() {
    }

    override fun initView() {

    }

    override fun initBefore() {
        ARouter.getInstance().inject(this)
    }

    override fun startObserve() {
        loginViewModel.run {
            uiState.observe(this@LoginActivity,{
                if (it.isLoading){
                    showProgressDialog()
                }
                it.isSuccess?.let {
                    dismissProgressDialog()
//                    finish()
                    FFLog.i("success")
                }
                it.isError?.let {
                    dismissProgressDialog()
                    FFLog.w("error-->"+it)
                   toast(it)
                }
                if (it.needLogin){
                    loginViewModel.login()
                }
            })

        }
    }

    override fun setVariable() {
        mBinding.run {
            viewModel = loginViewModel
            title = Title(R.string.login, R.drawable.arrow_back, { onBackPressed() })
        }
    }
    private var progressDialog: ProgressDialog? = null
    private fun showProgressDialog() {
        if (progressDialog == null)
            progressDialog = ProgressDialog(this)
        progressDialog?.show()
    }

    private fun dismissProgressDialog() {
        progressDialog?.dismiss()
    }

    override fun getLayoutId(): Int {
       return R.layout.activity_login
    }

}