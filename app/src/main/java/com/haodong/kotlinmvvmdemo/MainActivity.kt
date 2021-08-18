package com.haodong.kotlinmvvmdemo

import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.haodong.kotlinmvvmdemo.databinding.ActivityMainBinding
import com.haodong.lib.common.core.BaseVMActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Author: tangyuan
 * Time : 2021/8/17
 * Description:
 */
class MainActivity : BaseVMActivity<ActivityMainBinding>() {
    init {
        useBinding=true
    }

    override fun initData() {

    }

    override fun initView() {
        mBinding.run {
            val navHostFragment: NavHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            val navController: NavController = navHostFragment.navController;
            NavigationUI.setupWithNavController(navView, navController)
        }

    }

    override fun startObserve() {
    }

    override fun setVariable() {
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(this, R.id.nav_host_fragment).navigateUp()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }


}