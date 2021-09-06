package io.github.haodongling.kotlinmvvmdemo.ui.knowledge

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.didichuxing.doraemonkit.util.ToastUtils
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import io.github.haodongling.kotlinmvvmdemo.R
import io.github.haodongling.kotlinmvvmdemo.databinding.FragmentNavBinding
import io.github.haodongling.kotlinmvvmdemo.ui.home.HomeDivider
import io.github.haodongling.lib.common.core.BaseVMFragment
import io.github.haodongling.lib.ui.MultiStateView

/**
 * Author: tangyuan
 * Time : 2021/9/3
 * Description:
 */
class NavFragment : BaseVMFragment<FragmentNavBinding>(R.layout.fragment_nav), OnRefreshListener {
    val mAdapter: NaviAdapter by lazy {
        NaviAdapter()
    }
    val viewModel: KnowledgeViewModel by lazy {
        getFragmentScopeViewModel(KnowledgeViewModel::class.java)
    }

    override fun setVariable() {
    }

    override fun initView() {
        mBinding.let { it ->
            it.msv.viewState = MultiStateView.ViewState.LOADING
            it.rv.run {
                layoutManager = LinearLayoutManager(mContext)
                adapter = mAdapter;
                addItemDecoration(HomeDivider(mContext))
            }
            it.refreshLayout.setEnableLoadMore(false)
            it.refreshLayout.setOnRefreshListener(this@NavFragment)
        }
    }

    override fun initData() {
        viewModel.getNavigation()
    }

    override fun startObserve() {
        viewModel.navigationData.observe(this, Observer {
            it.showSuccess?.let { list ->
                if (list.isEmpty()) {
                    mBinding.msv.viewState = MultiStateView.ViewState.EMPTY
                } else {
                    mAdapter.setNewInstance(list.toMutableList())
                    mBinding.msv.viewState = MultiStateView.ViewState.CONTENT
                }
            }
            it.showError?.let { str ->
                ToastUtils.showShort(str)
                mBinding.msv.viewState = MultiStateView.ViewState.ERROR
            }
        })
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        viewModel.getNavigation()
        mBinding.refreshLayout.finishRefresh(800)
    }
}