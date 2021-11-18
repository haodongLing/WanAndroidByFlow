package io.github.haodongling.kotlinmvvmdemo.ui.login

import android.app.ProgressDialog
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import io.github.haodongling.kotlinmvvmdemo.Application
import io.github.haodongling.kotlinmvvmdemo.Application.Companion.appViewModel
import io.github.haodongling.kotlinmvvmdemo.R
import io.github.haodongling.kotlinmvvmdemo.databinding.ActivityLoginBinding
import io.github.haodongling.kotlinmvvmdemo.model.bean.Title
import io.github.haodongling.kotlinmvvmdemo.util.CaCheUtil
import io.github.haodongling.lib.common.core.BaseVMActivity
import io.github.haodongling.lib.common.ext.toast
import io.github.haodongling.lib.common.global.BizConst
import io.github.haodongling.lib.common.util.FFLog

/**
 * Author: tangyuan
 * Time : 2021/8/17
 * Description:
 */
@Route(path = BizConst.LOGIN)
class LoginActivity : BaseVMActivity<ActivityLoginBinding>() {

   private val loginViewModel by lazy {
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
            uiState.observe(this@LoginActivity, {
                if (it.isLoading) {
                    showProgressDialog()
                }
                it.isSuccess?.let { it->
                    dismissProgressDialog()
                    FFLog.i("success")
                   appViewModel.userInfo.value=it
                    CaCheUtil.setUser(it)
                    ARouter.getInstance().build(BizConst.MAIN).navigation(this@LoginActivity)
                    finish()
                }
                it.isError?.let {
                    dismissProgressDialog()
                    FFLog.w("error-->" + it)
                    toast(it)
                }
                if (it.needLogin) {
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
        if (progressDialog == null) progressDialog = ProgressDialog(this)
        progressDialog?.show()
    }

    private fun dismissProgressDialog() {
        progressDialog?.dismiss()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }


}