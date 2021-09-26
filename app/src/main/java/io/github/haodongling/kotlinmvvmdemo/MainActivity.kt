package io.github.haodongling.kotlinmvvmdemo

import android.text.TextUtils
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.github.haodongling.kotlinmvvmdemo.databinding.ActivityMainBinding
import io.github.haodongling.lib.common.core.BaseVMActivity
import io.github.haodongling.lib.common.global.BizConst
import io.github.haodongling.lib.common.model.bean.Destination
import io.github.haodongling.lib.common.util.AppConfig
import io.github.haodongling.lib.common.util.NavGraphBuilder
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

/**
 * Author: tangyuan
 * Time : 2021/8/17
 * Description:
 */
@Route(path = BizConst.MAIN)
class MainActivity : BaseVMActivity<ActivityMainBinding>(), BottomNavigationView.OnNavigationItemSelectedListener {
    lateinit var navController: NavController;

    override fun initData() {

    }

    override fun initView() {
        mBinding.run {
            val navHostFragment: NavHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            navController = navHostFragment.findNavController();
//            navController.handleDeepLink()
            NavGraphBuilder.build(
                this@MainActivity,
                navHostFragment.childFragmentManager,
                navController,
                navHostFragment.id
            )
//            NavigationUI.setupWithNavController(navView, navController)
//            navView.setupWithNavController(navController)
            navView.setOnNavigationItemSelectedListener(this@MainActivity)
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
        return findNavController(this, R.id.nav_host_fragment).navigateUp()
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        val currentPageId: Int = navController.currentDestination?.id ?: -1;
        val homeDestId = navController.graph.startDestination
        if (currentPageId != homeDestId) {
            navView.selectedItemId = homeDestId
            return
        }
        finish()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val destConfig: HashMap<String, Destination> = AppConfig.sDestConfig
        val iterator: Iterator<Map.Entry<String, Destination>> = destConfig.entries.iterator()
        //遍历 target destination 是否需要登录拦截
//        while (iterator.hasNext()) {
//            val entry: Map.Entry<String, Destination> = iterator.next()
//            val value: Destination = entry.value
//            if (value != null && !(PreferenceUtil(PreferenceUtil.IS_LOGIN, false) as Boolean) && value.needLogin && value.id == item.getItemId()) {
//                ARouter.getInstance().build(BizConst.LOGIN).navigation(this@MainActivity)
//                return false
//            }
//        }
        navController.navigate(item.itemId)
        return !TextUtils.isEmpty(item.title)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }


}