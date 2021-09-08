package io.github.haodongling.kotlinmvvmdemo.ui.mine

import io.github.haodongling.kotlinmvvmdemo.R
import io.github.haodongling.kotlinmvvmdemo.databinding.FragmentMineBinding
import io.github.haodongling.lib.common.core.BaseVMFragment
import io.github.haodongling.lib.common.global.BizConst
import io.github.haodongling.lib.navannotation.FragmentDestination

/**
 * Author: tangyuan
 * Time : 2021/8/16
 * Description:
 */
@FragmentDestination(pageUrl = BizConst.FRAGMENT_MINE, asStarter = false,needLogin = true)
class MineFragment : BaseVMFragment<FragmentMineBinding>(R.layout.fragment_mine) {
    override fun initView() {
    }

    override fun initData() {
    }

    override fun startObserve() {
    }
    override fun setVariable() {

    }

}