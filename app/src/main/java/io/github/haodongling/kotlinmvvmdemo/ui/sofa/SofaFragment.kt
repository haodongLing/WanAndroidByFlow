package io.github.haodongling.kotlinmvvmdemo.ui.sofa

import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.listener.OnItemClickListener
import io.github.haodongling.kotlinmvvmdemo.R
import io.github.haodongling.kotlinmvvmdemo.databinding.FragmentSofaBinding
import io.github.haodongling.kotlinmvvmdemo.ui.home.HomeAdapter
import io.github.haodongling.kotlinmvvmdemo.ui.home.HomeDivider
import io.github.haodongling.lib.common.core.BaseVMFragment
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import io.github.haodongling.lib.common.global.BizConst
import io.github.haodongling.lib.navannotation.FragmentDestination

/**
 * Author: tangyuan
 * Time : 2021/8/16
 * Description:
 */
@FragmentDestination(pageUrl = BizConst.FRAGMENT_SOFA, asStarter = false)
class SofaFragment : BaseVMFragment<FragmentSofaBinding>(R.layout.fragment_sofa), OnRefreshListener,
    OnLoadMoreListener {
    var currentPage = 0;
    var isRefresh: Boolean = true;
    val sofaViewModel by lazy {
        getFragmentScopeViewModel(SofaViewModel::class.java)
    }
    lateinit var homeAdapter: HomeAdapter
    override fun initView() {
        mBinding.run {
            refreshLayout.setOnRefreshListener(this@SofaFragment)
            refreshLayout.setOnLoadMoreListener(this@SofaFragment)
            homeAdapter = HomeAdapter(R.layout.rv_item_article)
            homeAdapter.addChildClickViewIds(R.id.tv_chapter_name, R.id.tv_author, R.id.cv_collect)
            homeAdapter.let { it ->
                it.setOnItemClickListener(OnItemClickListener { adapter, view, position ->

                })
                it.setOnItemChildClickListener { adapter, view, position ->

                }
            }
            recyclerview.let { it ->
                it.layoutManager = LinearLayoutManager(mContext)
                it.adapter = homeAdapter
                it.addItemDecoration(HomeDivider(mContext))
            }
        }
    }

    override fun initData() {
        mBinding.refreshLayout.autoRefresh()
    }

    override fun startObserve() {
        sofaViewModel.run {
            articleState.observe(this@SofaFragment, {
                it.showSuccess?.let { list ->
                    if (isRefresh) {
                        mBinding.refreshLayout.finishRefresh()
                        homeAdapter.data.clear()
                        homeAdapter.data.addAll(list.datas)
                    } else {
                        mBinding.refreshLayout.finishLoadMore()
                        homeAdapter.data.addAll(list.datas)
                    }
                    currentPage = list.curPage
                    homeAdapter.notifyDataSetChanged()
                    if (list.over) {
                        mBinding.refreshLayout.setEnableLoadMore(false)
                    } else {
                        mBinding.refreshLayout.setEnableLoadMore(true)
                    }

                }
                it.showError?.let {
                    if (isRefresh) {
                        mBinding.refreshLayout.finishRefresh()
                    } else {
                        mBinding.refreshLayout.finishLoadMore()
                    }
                }
            })
        }
    }

    override fun setVariable() {

    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        isRefresh = true
        currentPage = 0
        mBinding.refreshLayout.finishRefresh(1000)
        sofaViewModel.getArticleList(currentPage, isRefresh)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        isRefresh = false
        sofaViewModel.getArticleList(currentPage, isRefresh)
    }
}