package io.github.haodongling.kotlinmvvmdemo.ui.article

import android.view.KeyEvent
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.ycbjie.webviewlib.inter.InterWebListener
import com.ycbjie.webviewlib.utils.ToastUtils
import com.ycbjie.webviewlib.utils.X5WebUtils
import io.github.haodongling.kotlinmvvmdemo.R
import io.github.haodongling.kotlinmvvmdemo.databinding.ActivityArticleBinding
import io.github.haodongling.lib.common.core.BaseVMActivity
import io.github.haodongling.lib.common.global.BizConst

/**
 * Author: tangyuan
 * Time : 2021/9/10
 * Description:
 */
@Route(path = BizConst.ACTIVITY_ARTICLE)
class ArticleActivity : BaseVMActivity<ActivityArticleBinding>(), View.OnClickListener {
    @Autowired(name = "url")
    @JvmField
    var url: String? = ""

    @Autowired
    @JvmField
    var articleId: Int? = 0

    @Autowired
    @JvmField
    var collected: Boolean = false

    //    @Autowired
//    @JvmField
//    var user_name:String?=null
//    @Autowired
//    @JvmField
//    var user_id:String?=null
    var hide = false


    override fun setVariable() {

    }

    override fun initView() {
        ARouter.getInstance().inject(this)
        mBinding.pb.max = 100
        val interWebListener = object : InterWebListener {
            override fun hindProgressBar() {
            }

            override fun showErrorView(type: Int) {
            }

            override fun startProgress(newProgress: Int) {
                mBinding.pb.progress = newProgress
            }

            override fun showTitle(title: String?) {
            }

        }
        mBinding.let { it ->
            url?.let {
                mBinding.webview.loadUrl(url)
            }
            it.webview.x5WebChromeClient.setWebListener(interWebListener)
            it.slBack.setOnClickListener(this@ArticleActivity)
        }
        if (!X5WebUtils.isConnected(this)) {
            ToastUtils.showRoundRectToast("请先连接上网络")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.webview.destroy()
    }

    override fun onResume() {
        super.onResume()
        mBinding.webview.onResume()
    }

    override fun onStop() {
        super.onStop()
        mBinding.webview.onStop()
    }

    override fun initData() {
    }

    override fun startObserve() {
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_article
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (event.keyCode ==
            KeyEvent.KEYCODE_BACK && event.repeatCount == 0
        ) {
            if (mBinding.webview.pageCanGoBack()) {
                //退出网页
                return mBinding.webview.pageGoBack()
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onClick(p0: View?) {
        p0?.let {
            when (p0.id) {
                R.id.sl_back -> onBackPressed()

            }
        }
    }

}