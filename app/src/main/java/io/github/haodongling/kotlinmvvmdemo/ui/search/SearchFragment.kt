package io.github.haodongling.kotlinmvvmdemo.ui.search

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import io.github.haodongling.kotlinmvvmdemo.R
import io.github.haodongling.kotlinmvvmdemo.databinding.FragmentSearchBinding
import io.github.haodongling.kotlinmvvmdemo.util.CaCheUtil
import io.github.haodongling.lib.common.core.BaseVMFragment
import io.github.haodongling.lib.common.ext.toJson
import io.github.haodongling.lib.common.util.FFLog
import kotlinx.android.synthetic.main.fragment_search.*


/**
 * Author: tangyuan
 * Time : 2021/11/10
 * Description:
 */
class SearchFragment : BaseVMFragment<FragmentSearchBinding>(R.layout.fragment_search) {
    val historyAdapter: SearchHistoryAdapter by lazy { SearchHistoryAdapter(arrayListOf()) }
    val hotAdapter: SearchHotAdapter by lazy { SearchHotAdapter(arrayListOf()) }
    val requestSeaViewModel: SearchViewModel by lazy { getActivityScopeViewModel(SearchViewModel::class.java) }

    companion object {
        @JvmStatic
        fun create(): SearchFragment {
            return SearchFragment()
        }
    }

    override fun setVariable() {

    }


    override fun initView() {
        //初始化搜搜历史Recyclerview
//        search_historyRv.init(LinearLayoutManager(context), historyAdapter, false)
        search_historyRv.run {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = historyAdapter
            isNestedScrollingEnabled = false
        }
        //初始化热门Recyclerview
        val layoutManager = FlexboxLayoutManager(mContext)
        //方向 主轴为水平方向，起点在左端
        layoutManager.flexDirection = FlexDirection.ROW
        //左对齐
        layoutManager.justifyContent = JustifyContent.FLEX_START
//        search_hotRv.init(layoutManager, hotAdapter, false)
        search_hotRv.run {
            this.layoutManager = layoutManager
            setHasFixedSize(true)
            adapter = hotAdapter
            isNestedScrollingEnabled = false
        }
        historyAdapter.run {
            setOnItemClickListener { adapter, view, position ->
                val queryStr = historyAdapter.data[position]
                FFLog.i("queryStr-->${queryStr}")
                updateKey(queryStr)
                requestSeaViewModel.searchKey.value = queryStr
            }
            addChildClickViewIds(R.id.item_history_del)
            setOnItemChildClickListener { adapter, view, position ->
                when (view.id) {
                    R.id.item_history_del -> {
                        requestSeaViewModel.historyData.value?.let {
                            it.removeAt(position)
                            requestSeaViewModel.historyData.value = it
                        }
                    }
                }
            }
        }

        hotAdapter.run {
            setOnItemClickListener { adapter, view, position ->
                val queryStr = hotAdapter.data[position].name
                updateKey(queryStr)
                requestSeaViewModel.searchKey.value = queryStr
            }
        }


    }

    override fun initData() {
        requestSeaViewModel.getHistoryData()
        requestSeaViewModel.getHotData()
    }

    override fun startObserve() {
        requestSeaViewModel.run {
            hotDataState.observe(viewLifecycleOwner, Observer {
                it.isSuccess?.let {
                    hotAdapter.setList(it)
                }
                it.isError?.let {

                }
            })

            historyData.observe(viewLifecycleOwner, Observer {
                historyAdapter.data = it;
                historyAdapter.notifyDataSetChanged()
                CaCheUtil.setSearchHistoryData(it.toJson())
            })
        }
    }

    fun updateKey(keyStr: String) {
        requestSeaViewModel.historyData.value?.let {
            if (it.contains(keyStr)) {
                //当搜索历史中包含该数据时 删除
                it.remove(keyStr)
            } else if (it.size >= 10) {
                //如果集合的size 有10个以上了，删除最后一个
                it.removeAt(it.size - 1)
            }
            //添加新数据到第一条
            it.add(0, keyStr)
            requestSeaViewModel.historyData.value = it
        }
    }
}