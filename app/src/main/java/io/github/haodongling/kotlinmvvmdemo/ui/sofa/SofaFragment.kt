package io.github.haodongling.kotlinmvvmdemo.ui.sofa

import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.callback.NavCallback
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import io.github.haodongling.kotlinmvvmdemo.R
import io.github.haodongling.kotlinmvvmdemo.databinding.FragmentSofaBinding
import io.github.haodongling.kotlinmvvmdemo.ui.home.CollectViewModel
import io.github.haodongling.kotlinmvvmdemo.ui.home.HomeAdapter
import io.github.haodongling.kotlinmvvmdemo.ui.home.HomeDivider
import io.github.haodongling.lib.common.core.BaseVMFragment
import io.github.haodongling.lib.common.global.BizConst
import io.github.haodongling.lib.common.model.bean.Article
import io.github.haodongling.lib.common.util.FFLog
import io.github.haodongling.lib.common.util.Pref
import io.github.haodongling.lib.navannotation.FragmentDestination
import kotlinx.android.synthetic.main.fragment_home.*

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
    private val collectViewModel by lazy {
        getFragmentScopeViewModel(CollectViewModel::class.java)
    }
    lateinit var homeAdapter: HomeAdapter
    override fun initView() {
        mBinding.run {
            refreshLayout.setOnRefreshListener(this@SofaFragment)
            refreshLayout.setOnLoadMoreListener(this@SofaFragment)
            homeAdapter = HomeAdapter(R.layout.rv_item_article)
            homeAdapter.addChildClickViewIds(R.id.tv_chapter_name, R.id.tv_author, R.id.cv_collect)
            homeAdapter.let { it ->
                it.setOnItemClickListener(OnItemClickListener { adapter, _, position ->
                    val article = adapter.data[position] as Article
                    ARouter.getInstance().build(BizConst.ACTIVITY_ARTICLE).withString("url", article.link)
                        .withInt("articleId", article.id).withBoolean("collected", article.collect)
                        .navigation(mContext, object : NavCallback() {
                            override fun onArrival(postcard: Postcard?) {
                                FFLog.i()
                            }

                        })
                })
                it.setOnItemChildClickListener { adapter, view, position ->
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
                                homeAdapter.notifyItemChanged(position)
                            } else {
                                ARouter.getInstance().build(BizConst.LOGIN).navigation(mContext)
                            }

                        }
                    }
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
                    /*接口好像有问题，自己维护*/
                    if (currentPage < list.pageCount) {
                        currentPage++
                    }

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
        collectViewModel.collectEvent.observe(viewLifecycleOwner, { event ->
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
        })
    }

    override fun setVariable() {

    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        clicktoRefresh()
    }

    private fun clicktoRefresh() {
        isRefresh = true
        currentPage = 0
        mBinding.refreshLayout.finishRefresh(1000)
        sofaViewModel.getWendaList(currentPage, isRefresh)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        isRefresh = false
        sofaViewModel.getWendaList(currentPage, isRefresh)
    }
}