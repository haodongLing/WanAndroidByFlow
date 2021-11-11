package io.github.haodongling.kotlinmvvmdemo.ui.search

import android.text.TextUtils
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.callback.NavCallback
import com.alibaba.android.arouter.launcher.ARouter
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import io.github.haodongling.kotlinmvvmdemo.R
import io.github.haodongling.kotlinmvvmdemo.databinding.FragmentSearchResultBinding
import io.github.haodongling.kotlinmvvmdemo.model.event.CollectEvent
import io.github.haodongling.kotlinmvvmdemo.ui.home.CollectViewModel
import io.github.haodongling.kotlinmvvmdemo.ui.home.HomeAdapter
import io.github.haodongling.kotlinmvvmdemo.ui.home.HomeDivider
import io.github.haodongling.lib.common.core.BaseVMFragment
import io.github.haodongling.lib.common.extention.LiveDataBus
import io.github.haodongling.lib.common.global.BizConst
import io.github.haodongling.lib.common.model.bean.Article
import io.github.haodongling.lib.common.util.FFLog
import io.github.haodongling.lib.common.util.Pref
import kotlinx.android.synthetic.main.fragment_search_result.*

/**
 * Author: tangyuan
 * Time : 2021/11/11
 * Description:
 */
class SearchResultFragment : BaseVMFragment<FragmentSearchResultBinding>(R.layout.fragment_search_result),
    OnRefreshListener, OnLoadMoreListener {
    private var currPage: Int = 0
    lateinit var homeAdapter: HomeAdapter
    val viewModel by lazy { getActivityScopeViewModel(SearchViewModel::class.java) }
    val collectViewModel by lazy { getApplicationScopeViewModel(CollectViewModel::class.java) }
    var isRefresh: Boolean = true;

    private var mKey: String = ""

    companion object {
        @JvmStatic
        fun create(): SearchResultFragment {
            return SearchResultFragment()
        }
    }

    override fun setVariable() {
    }

    override fun initView() {
        homeAdapter = HomeAdapter(R.layout.rv_item_article)
        homeAdapter.addChildClickViewIds(R.id.tv_chapter_name, R.id.tv_author, R.id.cv_collect)
        homeAdapter.run {
            setOnItemClickListener { adapter, view, position ->
                val article = adapter.data.get(position) as Article
                ARouter.getInstance().build(BizConst.ACTIVITY_ARTICLE).withString("url", article.link)
                    .withInt("articleId", article.id).withBoolean("collected", article.collect)
                    .navigation(mContext, object : NavCallback() {
                        override fun onArrival(postcard: Postcard?) {
                            FFLog.i()
                        }

                    })
            }
            setOnItemChildClickListener { adapter, view, position ->
                val article = adapter.data.get(position) as Article
                when (view.id) {
                    R.id.tv_chapter_name -> {

                    }
                    R.id.tv_author -> {

                    }
                    R.id.cv_collect -> {
                        val hasLogin by Pref<Boolean>(Pref.IS_LOGIN, false)
                        if (hasLogin) {
                            val collected = !article.collect;
                            collectViewModel.collectArticle(article.id, collected, position)
                            article.collect = collected;
                            adapter.data[position] = article;
                            notifyItemChanged(position)
                        } else {
                            ARouter.getInstance().build(BizConst.LOGIN).navigation(mContext)
                        }

                    }
                }

            }
        }

        mBinding.rv.run {
            layoutManager = LinearLayoutManager(mContext)
            adapter = homeAdapter
            addItemDecoration(HomeDivider(mContext))
        }
        srl.setOnLoadMoreListener(this@SearchResultFragment)
        srl.setOnRefreshListener(this@SearchResultFragment)
    }

    override fun initData() {

    }

    override fun startObserve() {
        viewModel.searchKey.observe(mContext, Observer {
            mKey = it;
            srl.autoRefresh()
        })
        viewModel.searchResultState.observe(mContext, {
            it.isSuccess?.let { list ->
                if (isRefresh) {
                    mBinding.srl.finishRefresh()
                    homeAdapter.data.clear()
                    homeAdapter.data.addAll(list.datas)
                } else {
                    mBinding.srl.finishLoadMore()
                    homeAdapter.data.addAll(list.datas)
                }
                currPage = list.curPage
                homeAdapter.notifyDataSetChanged()
                if (list.over) {
                    mBinding.srl.setEnableLoadMore(false)
                } else {
                    mBinding.srl.setEnableLoadMore(true)
                }

            }
            it.isError?.let {
                if (isRefresh) {
                    mBinding.srl.finishRefresh()
                } else {
                    mBinding.srl.finishLoadMore()
                }
            }
        })

        LiveDataBus.get().with(BizConst.COLLECT_ARTICLE).observerSticky(mContext, object : Observer<CollectEvent> {
            override fun onChanged(event: CollectEvent) {
                FFLog.i("event-->+$event")
                if (homeAdapter.data.size > event.position && homeAdapter.data.get(event.position).id == event.id) {
                    homeAdapter.data.get(event.position).collect = event.collect;
                    homeAdapter.notifyItemChanged(event.position)
                } else {
                    if (homeAdapter.data.size > 0) {
                        for (i in 0 until homeAdapter.data.size) {

                            if (homeAdapter.data.get(i).id == event.id) {
                                homeAdapter.data.get(i).collect = event.collect
                                homeAdapter.notifyDataSetChanged()
                            }
                        }


                    }

                }
            }

        }, true)
    }

    open fun search(key: String) {
        if (!key.isNullOrEmpty() && !TextUtils.equals(key, mKey)) {
            mKey = key
            viewModel.getSearchDataByKey(currPage, key)
        }
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        currPage = 0;
        isRefresh = true
        viewModel.getSearchDataByKey(currPage, mKey)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        isRefresh = false
        viewModel.getSearchDataByKey(currPage, mKey)
    }


}