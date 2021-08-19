package com.haodong.kotlinmvvmdemo.ui.home

import androidx.lifecycle.Observer
import com.haodong.kotlinmvvmdemo.R
import com.haodong.kotlinmvvmdemo.databinding.FragmentHomeBinding
import com.haodong.kotlinmvvmdemo.model.repository.HomeViewModel
import com.haodong.lib.common.core.BaseVMFragment
import com.haodong.lib.common.model.DTOResult
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener

/**
 * Author: tangyuan
 * Time : 2021/8/16
 * Description:
 */
class HomeFragment : BaseVMFragment<FragmentHomeBinding>(R.layout.fragment_home), OnRefreshListener,
    OnLoadMoreListener {
    var currentPage: Int = 0
    var isRefresh: Boolean = true;
    var hasbanner=false
    val homeViewModel by lazy {
        getFragmentScopeViewModel(HomeViewModel::class.java)
    }

    override fun initView() {
        mBinding.refreshLayout.setOnRefreshListener(this)
        mBinding.refreshLayout.setOnLoadMoreListener(this)

    }

    override fun initData() {
        homeViewModel.getbanner()
        homeViewModel.getArticleList(currentPage,isRefresh)
    }

    override fun startObserve() {
        homeViewModel.run {
            articleState.observe(this@HomeFragment, Observer {

            })
            bannerState.observe(this@HomeFragment, Observer {
                when(it){
                     is DTOResult.Success->{
                         val imgs=ArrayList<String>()
                         it.data.forEach {
                             imgs.add(it.imagePath)
                         }
                         mBinding.banner.setImages(imgs)
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
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        isRefresh = false
    }

}