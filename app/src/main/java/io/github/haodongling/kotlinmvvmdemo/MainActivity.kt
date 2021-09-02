package io.github.haodongling.kotlinmvvmdemo

import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import io.github.haodongling.kotlinmvvmdemo.databinding.ActivityMainBinding
import io.github.haodongling.lib.common.core.BaseVMActivity
import io.github.haodongling.lib.common.global.BizConst

/**
 * Author: tangyuan
 * Time : 2021/8/17
 * Description:
 */
@Route(path = BizConst.MAIN)
class MainActivity : BaseVMActivity<ActivityMainBinding>() {

    override fun initData() {

    }

    override fun initView() {
        mBinding.run {
            val navHostFragment: NavHostFragment =
                supportFragmentManager.findFragmentById(io.github.haodongling.kotlinmvvmdemo.R.id.nav_host_fragment) as NavHostFragment
            val navController: NavController = navHostFragment.navController;
            NavigationUI.setupWithNavController(navView, navController)
        }

    }

    override fun initBefore() {
        ARouter.getInstance().inject(this)
    }

    override fun startObserve() {
    }

    override fun setVariable() {
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(this, io.github.haodongling.kotlinmvvmdemo.R.id.nav_host_fragment).navigateUp()
    }

    override fun getLayoutId(): Int {
        return io.github.haodongling.kotlinmvvmdemo.R.layout.activity_main
    }


}