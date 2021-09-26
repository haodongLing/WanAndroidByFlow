package io.github.haodongling.kotlinmvvmdemo.ui.mine

import android.widget.Toast
import androidx.lifecycle.Observer
import com.didichuxing.doraemonkit.extension.err
import io.github.haodongling.kotlinmvvmdemo.R
import io.github.haodongling.kotlinmvvmdemo.databinding.FragmentMineBinding
import io.github.haodongling.lib.common.core.BaseVMFragment
import io.github.haodongling.lib.common.ext.toast
import io.github.haodongling.lib.common.global.BizConst
import io.github.haodongling.lib.common.util.ImageLoader
import io.github.haodongling.lib.navannotation.FragmentDestination
import kotlinx.android.synthetic.main.fragment_mine.*

/**
 * Author: tangyuan
 * Time : 2021/8/16
 * Description:
 */
@FragmentDestination(pageUrl = BizConst.FRAGMENT_MINE, asStarter = false, needLogin = true)
class MineFragment : BaseVMFragment<FragmentMineBinding>(R.layout.fragment_mine) {
    val viewModel by lazy {
        getFragmentScopeViewModel(MineViewModel::class.java)
    }

    override fun initView() {
        mBinding.run {

        }
    }

    override fun initData() {
        viewModel.getUserDetail()
    }

    override fun startObserve() {
        viewModel.uiState.observe(this, Observer {
            it.showSuccess?.let {
                it.userInfo.let { userInfo ->
                    userInfo.icon.let {
                        ImageLoader.image(civ_user_icon,it)
                    }
                    userInfo.nickname.let { tv_user_name.text=it  }

                }
                it.coinInfo.let { coinInfo->
                    tv_user_level.text=coinInfo.level.toString();
                    tv_coin.text=coinInfo.coinCount.toString()
                }

            }
            it.showError?.let { str->
                toast(mContext, str,Toast.LENGTH_SHORT)
            }
        })
    }

    override fun setVariable() {

    }

}