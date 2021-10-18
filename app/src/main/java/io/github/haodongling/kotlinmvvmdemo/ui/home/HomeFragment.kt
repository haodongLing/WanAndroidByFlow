package io.github.haodongling.kotlinmvvmdemo.ui.home

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.callback.NavCallback
import com.alibaba.android.arouter.launcher.ARouter
import com.google.android.material.appbar.AppBarLayout
import io.github.haodongling.kotlinmvvmdemo.R
import io.github.haodongling.kotlinmvvmdemo.databinding.FragmentHomeBinding
import io.github.haodongling.kotlinmvvmdemo.widget.GlideImageLoader
import io.github.haodongling.lib.common.core.BaseVMFragment
import io.github.haodongling.lib.common.model.DTOResult
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import io.github.haodongling.lib.common.global.BizConst
import io.github.haodongling.lib.common.model.bean.Article
import io.github.haodongling.lib.common.sharedpre.PreferenceExt
import io.github.haodongling.lib.common.util.FFLog
import io.github.haodongling.lib.common.util.Pref
import io.github.haodongling.lib.navannotation.FragmentDestination
import io.github.haodongling.lib.utils.UIUtils
import kotlin.math.abs

/**
 * Author: tangyuan
 * Time : 2021/8/16
 * Description:
 */
@FragmentDestination(pageUrl = BizConst.FRAGMENT_HOME, asStarter = true)
class HomeFragment : BaseVMFragment<FragmentHomeBinding>(R.layout.fragment_home), OnRefreshListener,
    OnLoadMoreListener {
    var currentPage: Int = 0
    var isRefresh: Boolean = true;
    var hasbanner = false
    private val homeViewModel by lazy {
        getFragmentScopeViewModel(HomeViewModel::class.java)
    }
    lateinit var homeAdapter: HomeAdapter
    var lastVerticalOffset: Int = -1
    var statusColor: Int = 0 // 状态栏透明度
    var totalScrollY: Int = 0
    var offsetHeight: Int = 0;

    override fun initView() {
        mBinding.refreshLayout.setOnRefreshListener(this)
        mBinding.refreshLayout.setOnLoadMoreListener(this)
        homeAdapter = HomeAdapter(R.layout.rv_item_article)
        homeAdapter.addChildClickViewIds(R.id.tv_chapter_name, R.id.tv_author, R.id.cv_collect)
        homeAdapter.run {
            setOnItemClickListener { adapter, view, position ->
                val article = adapter.data.get(position) as Article
                ARouter.getInstance().build(BizConst.ACTIVITY_ARTICLE)
                    .withString("url", article.link)
                    .withInt("articleId", article.id)
                    .withBoolean("collected", article.collect)
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
//                        val hasLogin by Pref<Boolean>(Pref.IS_LOGIN,false)
//                        if (hasLogin){
//                            val collected=!article.collect;
//                            homeViewModel.collectArticle(article.id,collected)
//                            article.collect=collected;
//                            adapter.data[position] = collected;
//                        }else{
//                            ARouter.getInstance().build(BizConst.LOGIN).navigation(mContext)
//                        }

                    }
                }

            }
        }

        mBinding.recyclerView.run {
            layoutManager = LinearLayoutManager(mContext)
            adapter = homeAdapter
            addItemDecoration(HomeDivider(mContext))
        }
        mBinding.banner.run {
            val topBannerWidth = UIUtils.getScreenWidth(mContext)
            val topBannerHeight = (topBannerWidth * (180 / 375f)).toInt()
            offsetHeight = UIUtils.dp2px(mContext, 76F);
            setImageLoader(GlideImageLoader())
            val params = layoutParams
            params.height = topBannerHeight
            params.width = topBannerWidth
        }

        mBinding.run {
//            StatusBarUtil.setTranslucentForImageViewInFragment(mContext,appBarLayout)
            appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
                lastVerticalOffset = verticalOffset
                if (-verticalOffset > offsetHeight) {
                    layoutToolbar.setBackgroundColor(mContext.resources.getColor(R.color.colorPrimary))
                    if (statusColor != 255) {
                        statusColor = 255
                    }
                    statusColor = 255
                } else {
                    totalScrollY = if (abs(verticalOffset) <= offsetHeight) {
                        abs(verticalOffset)
                    } else {
                        0
                    }

                    val heightAlpha: Float = Math.abs(totalScrollY * 1.0f / offsetHeight)
                    layoutToolbar.setBackgroundColor(
                        UIUtils.getColorWithAlpha(
                            heightAlpha,
                            mContext.resources.getColor(R.color.colorPrimary)
                        )
                    )
                }
            })

        }
    }

    override fun initData() {
        homeViewModel.getbanner()
        homeViewModel.getArticleList(currentPage, isRefresh)

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
            collectState.observe(this@HomeFragment,{
                it.isSuccess?.let {
                    FFLog.i("isSuccess")
                }
                it.isError?.let  {str->
                    FFLog.i("isError-->{$str}")
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