package com.haodong.kotlinmvvmdemo.ui.home

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import com.haodong.kotlinmvvmdemo.R
import com.haodong.kotlinmvvmdemo.databinding.FragmentHomeBinding
import com.haodong.kotlinmvvmdemo.model.repository.HomeViewModel
import com.haodong.kotlinmvvmdemo.widget.GlideImageLoader
import com.haodong.lib.common.core.BaseVMFragment
import com.haodong.lib.common.model.DTOResult
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import io.github.haodongling.lib.utils.UIUtils

/**
 * Author: tangyuan
 * Time : 2021/8/16
 * Description:
 */
class HomeFragment : BaseVMFragment<FragmentHomeBinding>(R.layout.fragment_home), OnRefreshListener,
    OnLoadMoreListener {
    var currentPage: Int = 0
    var isRefresh: Boolean = true;
    var hasbanner = false
    val homeViewModel by lazy {
        getFragmentScopeViewModel(HomeViewModel::class.java)
    }
    lateinit var homeAdapter: HomeAdapter

    override fun initView() {
        mBinding.refreshLayout.setOnRefreshListener(this)
        mBinding.refreshLayout.setOnLoadMoreListener(this)
        homeAdapter = HomeAdapter(R.layout.rv_item_article)
        mBinding.recyclerView.run {
            layoutManager = LinearLayoutManager(mContext)
            adapter = homeAdapter
        }
        mBinding.banner.run {
          val  topBannerWidth = UIUtils.getScreenWidth(mContext)
           val topBannerHeight = (topBannerWidth * (180 / 375f)).toInt()
            setImageLoader(GlideImageLoader())
            val params = layoutParams
            params.height = topBannerHeight
            params.width = topBannerWidth
        }
    }

    override fun initData() {
        homeViewModel.getbanner()
        homeViewModel.getArticleList(currentPage, isRefresh)
        homeAdapter.run {
            setOnItemClickListener { adapter, view, position ->

            }
            setOnItemChildClickListener { adapter, view, position ->

            }
        }
    }

    override fun startObserve() {
        homeViewModel.run {
            articleState.observe(this@HomeFragment, Observer {

                it.showSuccess?.let { list ->
                    if (isRefresh) {
                        mBinding.refreshLayout.finishRefresh()
                        homeAdapter.data.clear()
                        homeAdapter.data.addAll(list.datas)
                    } else {
                        mBinding.refreshLayout.finishLoadMore()
                        homeAdapter.data.addAll(list.datas)
                    }
                    currentPage=list.curPage
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
            bannerState.observe(this@HomeFragment, Observer {
                when (it) {
                    is DTOResult.Success -> {
                        val imgs = ArrayList<String>()
                        it.data.forEach {
                            imgs.add(it.imagePath)
                        }
                        mBinding.banner.update(imgs)

                    }
                    else -> {

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
        homeViewModel.getbanner()
        homeViewModel.getArticleList(currentPage, isRefresh)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        isRefresh = false
        homeViewModel.getArticleList(currentPage, isRefresh)
    }

}