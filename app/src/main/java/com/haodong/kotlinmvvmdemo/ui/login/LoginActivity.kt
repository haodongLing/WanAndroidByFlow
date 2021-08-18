package com.haodong.kotlinmvvmdemo.ui.login

import android.app.ProgressDialog
import com.haodong.kotlinmvvmdemo.R
import com.haodong.kotlinmvvmdemo.databinding.ActivityLoginBinding
import com.haodong.kotlinmvvmdemo.model.bean.Title
import com.haodong.lib.common.core.BaseVMActivity
import com.haodong.lib.common.ext.toast

/**
 * Author: tangyuan
 * Time : 2021/8/17
 * Description:
 */
class LoginActivity : BaseVMActivity<ActivityLoginBinding>() {

    val loginViewModel by lazy {
        getActivityScopeViewModel(LoginViewModel::class.java)
    }

    override fun initData() {
    }

    override fun initView() {
    }

    override fun startObserve() {
        loginViewModel.run {
            uiState.observe(this@LoginActivity,{
                if (it.isLoading){
                    showProgressDialog()
                }
                it.isSuccess?.let {
                    dismissProgressDialog()
                    finish()
                }
                it.isError?.let {
                    dismissProgressDialog()
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