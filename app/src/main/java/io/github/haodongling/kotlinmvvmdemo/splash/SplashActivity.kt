package io.github.haodongling.kotlinmvvmdemo.splash

import android.os.CountDownTimer
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import io.github.haodongling.kotlinmvvmdemo.R
import io.github.haodongling.kotlinmvvmdemo.databinding.ActivitySplashBinding
import io.github.haodongling.kotlinmvvmdemo.util.CaCheUtil
import io.github.haodongling.lib.common.core.BaseVMActivity
import io.github.haodongling.lib.common.global.BizConst
import kotlinx.android.synthetic.main.activity_splash.*

/**
 * Author: tangyuan
 * Time : 2021/8/18
 * Description:
 */
@Route(path = BizConst.SPLASH)
class SplashActivity : BaseVMActivity<ActivitySplashBinding>() {
    var jumped = false;

    override fun initData() {
    }

    override fun initView() {


        mBinding.apply {
            val timer = object : CountDownTimer(3000, 1000) {
                override fun onTick(p0: Long) {
                    tv_time.text = "${p0 / 1000 + 1}s"
                }

                override fun onFinish() {
                    jump()
                }

            }
            timer.start()
            tvJump.setOnClickListener {
                timer.cancel()
                jump()
            }
        }
    }

    private fun jump() {
        if (jumped) {
            return
        }
        jumped = true
        if (CaCheUtil.isLogin()) {
            ARouter.getInstance().build(BizConst.MAIN).navigation(this@SplashActivity)
        } else {
            ARouter.getInstance().build(BizConst.LOGIN).navigation(this@SplashActivity)
        }
        finish()
    }

    override fun initBefore() {
        ARouter.getInstance().inject(this)
    }

    override fun startObserve() {
    }

    override fun setVariable() {
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }
}