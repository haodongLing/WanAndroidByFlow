package io.github.haodongling.kotlinmvvmdemo.ui.knowledge

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.didichuxing.doraemonkit.util.ToastUtils
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import io.github.haodongling.kotlinmvvmdemo.R
import io.github.haodongling.kotlinmvvmdemo.databinding.FragmentKnowledgeBinding
import io.github.haodongling.kotlinmvvmdemo.ui.home.HomeDivider
import io.github.haodongling.lib.common.core.BaseVMFragment
import io.github.haodongling.lib.common.util.FFLog
import io.github.haodongling.lib.ui.MultiStateView

/**
 * Author: tangyuan
 * Time : 2021/9/3
 * Description:
 */
class KnowledgeFragment : BaseVMFragment<FragmentKnowledgeBinding>(R.layout.fragment_knowledge), OnRefreshListener {
    val viewModel by lazy { getFragmentScopeViewModel(KnowledgeViewModel::class.java) }
    override fun setVariable() {
    }

    val mAdapter: KnowledgeAdapter by lazy {
        KnowledgeAdapter()
    }

    override fun initView() {
        mBinding.let { it ->
            it.msv.viewState = MultiStateView.ViewState.LOADING
            it.rv.layoutManager = LinearLayoutManager(mContext)
            it.rv.adapter = mAdapter;
            it.rv.addItemDecoration(HomeDivider(mContext))
            it.refreshLayout.setEnableLoadMore(false)
            it.refreshLayout.setOnRefreshListener(this@KnowledgeFragment)
        }
    }

    override fun initData() {
        viewModel.getSystem()
    }

    override fun startObserve() {
        viewModel.systemData.observe(this, Observer {
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
        mBinding.refreshLayout.finishRefresh(800)
        viewModel.getSystem()
    }
}