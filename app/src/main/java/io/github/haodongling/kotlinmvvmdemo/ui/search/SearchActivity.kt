package io.github.haodongling.kotlinmvvmdemo.ui.search

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import io.github.haodongling.kotlinmvvmdemo.R
import io.github.haodongling.kotlinmvvmdemo.databinding.ActivitySearchBinding
import io.github.haodongling.kotlinmvvmdemo.util.CaCheUtil
import io.github.haodongling.lib.common.core.BaseVMActivity
import io.github.haodongling.lib.common.ext.toJson
import io.github.haodongling.lib.common.global.BizConst
import kotlinx.android.synthetic.main.activity_search.*

/**
 * Author: tangyuan
 * Time : 2021/11/10
 * Description:
 */
@Route(path =BizConst.ACTIVITY_SEARCH )
class SearchActivity : BaseVMActivity<ActivitySearchBinding>() {
    val historyAdapter:SearchHistoryAdapter by lazy { SearchHistoryAdapter(arrayListOf()) }
    val hotAdapter:SearchHotAdapter by lazy { SearchHotAdapter(arrayListOf()) }
    val requestSeaViewModel:SearchViewModel by lazy { SearchViewModel() }
    override fun getLayoutId(): Int {
       return R.layout.activity_search
    }

    override fun setVariable() {
    }

    override fun initView() {
        //初始化搜搜历史Recyclerview
//        search_historyRv.init(LinearLayoutManager(context), historyAdapter, false)
        search_historyRv.run {
            layoutManager= LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter=historyAdapter
            isNestedScrollingEnabled=false
        }
        //初始化热门Recyclerview
        val layoutManager = FlexboxLayoutManager(this)
        //方向 主轴为水平方向，起点在左端
        layoutManager.flexDirection = FlexDirection.ROW
        //左对齐
        layoutManager.justifyContent = JustifyContent.FLEX_START
//        search_hotRv.init(layoutManager, hotAdapter, false)
        search_hotRv.run {
            this.layoutManager=layoutManager
            setHasFixedSize(true)
            adapter=hotAdapter
            isNestedScrollingEnabled=false
        }
        iv_back.setOnClickListener {
            onBackPressed()
        }

    }

    override fun initData() {
        requestSeaViewModel.getHistoryData()
        requestSeaViewModel.getHotData()
    }

    override fun startObserve() {
        requestSeaViewModel.run {
            hotDataState.observe(this@SearchActivity, Observer {
                it.isSuccess?.let {
                    hotAdapter.setList(it)
                }
                it.isError?.let {

                }
            })

            historyData.observe(this@SearchActivity, Observer {
                historyAdapter.data=it;
                historyAdapter.notifyDataSetChanged()
                CaCheUtil.setSearchHistoryData(it.toJson())
            })
        }
    }

}