package io.github.haodongling.kotlinmvvmdemo.ui.mine

import com.alibaba.android.arouter.facade.annotation.Autowired
import io.github.haodongling.kotlinmvvmdemo.R
import io.github.haodongling.kotlinmvvmdemo.databinding.ActivityUserpageBinding
import io.github.haodongling.lib.common.core.BaseVMActivity

/**
 * Author: tangyuan
 * Time : 2021/9/10
 * Description:
 */

class UserPageActivity : BaseVMActivity<ActivityUserpageBinding>() {
    @JvmField
    @Autowired(name ="userId")
    var userId:Int?=0;

    override fun setVariable() {

    }

    override fun initView() {
    }

    override fun initData() {
    }

    override fun startObserve() {
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_userpage
    }
}