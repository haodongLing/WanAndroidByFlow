package io.github.haodongling.kotlinmvvmdemo.splash

import android.net.Uri
import android.os.CountDownTimer
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import io.github.haodongling.kotlinmvvmdemo.R
import io.github.haodongling.kotlinmvvmdemo.databinding.ActivitySplashBinding
import io.github.haodongling.lib.common.core.BaseVMActivity
import io.github.haodongling.lib.common.global.BizConst
import io.github.haodongling.lib.common.util.PreferenceUtil
import kotlinx.android.synthetic.main.activity_splash.*

/**
 * Author: tangyuan
 * Time : 2021/8/18
 * Description:
 */
@Route(path =BizConst.SPLASH )
class SplashActivity : BaseVMActivity<ActivitySplashBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun initData() {
    }

    override fun initView() {


        mBinding.apply {
            val timer = object : CountDownTimer(3000, 1000) {
                override fun onTick(p0: Long) {
                    tv_time.text = "${p0 / 1000+1}s"
                }

                override fun onFinish() {
                    var isLogin by PreferenceUtil(PreferenceUtil.IS_LOGIN, false)
                    if (isLogin) {
                        io.github.haodongling.lib.common.util.FFLog.i("uri.parse-->"+Uri.parse("haodong:mvvm/demo/main"))
//                        startActivity(Intent(this@SplashActivity, MainActivity::class.java))

                        ARouter.getInstance().build(BizConst.MAIN).navigation(this@SplashActivity)
                    } else {
                        io.github.haodongling.lib.common.util.FFLog.i("uri.parse-->"+Uri.parse("haodong:mvvm/demo/login"))
//                        startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                        ARouter.getInstance().build(BizConst.LOGIN).navigation(this@SplashActivity)
                    }
                    finish()
                }

            }.start()
            tvJump.setOnClickListener {
                ARouter.getInstance().build(BizConst.MAIN).navigation(this@SplashActivity)
            }
        }
    }

    override fun initBefore() {
        ARouter.getInstance().inject(this)
    }

    override fun startObserve() {
    }

    override fun setVariable() {
    }
}