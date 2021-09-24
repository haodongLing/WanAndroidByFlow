package io.github.haodongling.kotlinmvvmdemo.ui.knowledge

import android.content.Context
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.didichuxing.doraemonkit.util.ToastUtils
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import io.github.haodongling.kotlinmvvmdemo.R
import io.github.haodongling.kotlinmvvmdemo.databinding.FragmentKnowledgeArticleBinding
import io.github.haodongling.kotlinmvvmdemo.ui.home.HomeDivider
import io.github.haodongling.lib.common.core.BaseVMFragment
import io.github.haodongling.lib.common.model.bean.SystemChild
import io.github.haodongling.lib.common.util.FFLog
import io.github.haodongling.lib.ui.MultiStateView
import kotlinx.android.synthetic.main.fragment_knowledge_article.*

/**
 * Author: tangyuan
 * Time : 2021/9/22
 * Description:
 */
class KnowledgeArticleFragment : BaseVMFragment<FragmentKnowledgeArticleBinding>(R.layout.fragment_knowledge_article),
    OnRefreshListener, OnLoadMoreListener {
    companion object {
        @JvmStatic
        fun getInstance(systemChild: SystemChild): KnowledgeArticleFragment {
            val knowledgeArticleFragment = KnowledgeArticleFragment()
            val bundle = Bundle();
            bundle.putParcelable("systemChild", systemChild);
            knowledgeArticleFragment.arguments = bundle
            return knowledgeArticleFragment
        }
    }

    //    var systemChild: SystemChild? = null;
    var currPage = 0;
    var isRefresh = false
    val articleAdapter by lazy {
        ArticleAdapter()
    }
    var systemChild: SystemChild? = null
    var cid = 0
    override fun onAttach(context: Context) {
        super.onAttach(context)
        FFLog.i()
        arguments?.let {
            systemChild = it.getParcelable("systemChild")
        }
    }

    val viewModel: KnowledgeArticleViewModel by lazy {
        getActivityScopeViewModel(KnowledgeArticleViewModel::class.java)
    }

    override fun setVariable() {
    }

    override fun initView() {
        msv.viewState = MultiStateView.ViewState.LOADING
        mBinding.run {
            refreshLayout.setOnLoadMoreListener(this@KnowledgeArticleFragment)
            refreshLayout.setOnRefreshListener(this@KnowledgeArticleFragment)
            rv.adapter = articleAdapter;
            rv.layoutManager = LinearLayoutManager(mContext)
            rv.addItemDecoration(HomeDivider(mContext))
        }

    }

    override fun initData() {
        isRefresh=true
        if (systemChild == null) {
            msv.viewState = MultiStateView.ViewState.EMPTY
        } else {
            systemChild?.let {
                cid = it.id
                viewModel.getSystemTypeDetail(true, currPage, it.id)
            }
        }
    }

    override fun startObserve() {
        viewModel.articleListData.observe(this, {
            it.showSuccess?.let { articleList ->
                if (isRefresh) {
                    mBinding.refreshLayout.finishRefresh()
                    articleAdapter.setNewInstance(articleList.datas.toMutableList())
                } else {
                    mBinding.refreshLayout.finishLoadMore()
                    articleAdapter.data.addAll(articleAdapter.data.toMutableList())
                    articleAdapter.notifyDataSetChanged()
                }

                currPage = articleList.curPage
                if (articleList.over) {
                    mBinding.refreshLayout.setEnableLoadMore(false)
                } else {
                    mBinding.refreshLayout.setEnableAutoLoadMore(true)
                }
                mBinding.msv.viewState = MultiStateView.ViewState.CONTENT
            }
            it.showError?.let {
                ToastUtils.showShort(it)
                mBinding.msv.viewState = MultiStateView.ViewState.ERROR
            }

        })
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        currPage = 0
        isRefresh = true
        viewModel.getSystemTypeDetail(isRefresh, currPage, cid)

    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        isRefresh = false
        viewModel.getSystemTypeDetail(isRefresh, currPage, cid)
    }
}