package io.github.haodongling.kotlinmvvmdemo.ui.search
import android.widget.SearchView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer

import com.alibaba.android.arouter.facade.annotation.Route

import io.github.haodongling.kotlinmvvmdemo.R
import io.github.haodongling.kotlinmvvmdemo.databinding.ActivitySearchBinding

import io.github.haodongling.lib.common.core.BaseVMActivity
import io.github.haodongling.lib.common.ext.hideSoftKeyboard
import io.github.haodongling.lib.common.global.BizConst
import io.github.haodongling.lib.common.util.FFLog
import kotlinx.android.synthetic.main.activity_search.*

/**
 * Author: tangyuan
 * Time : 2021/11/10
 * Description:
 */
@Route(path = BizConst.ACTIVITY_SEARCH)
class SearchActivity : BaseVMActivity<ActivitySearchBinding>() {
    var mIsResultPage = false;
    private val mFragmentManager: FragmentManager by lazy {
        supportFragmentManager
    }
    lateinit var mSearchHistoryFragment: SearchFragment
    lateinit var mSearchResultFragment: SearchResultFragment
    val requestSeaViewModel: SearchViewModel by lazy { getActivityScopeViewModel(SearchViewModel::class.java) }


    override fun getLayoutId(): Int {
        return R.layout.activity_search
    }

    override fun setVariable() {
    }

    override fun onBackPressed() {
        if (mIsResultPage) {
            hideSoftKeyboard(this)
            showHistoryFragment()
        } else {
            super.onBackPressed()
        }

    }

    override fun initView() {
        iv_back.setOnClickListener {
            onBackPressed()
        }
        val transaction = mFragmentManager.beginTransaction();
        val searchHistoryFragment = mFragmentManager.findFragmentByTag(
            SearchFragment::class.java.getName()
        )
        if (searchHistoryFragment == null) {
            mSearchHistoryFragment = SearchFragment.create()
            transaction.add(R.id.fl, mSearchHistoryFragment, SearchFragment::class.java.getName())
        } else {
            mSearchHistoryFragment = searchHistoryFragment as SearchFragment
        }
        val searchResultFragment = mFragmentManager.findFragmentByTag(
            SearchResultFragment::class.java.name
        )
        if (searchResultFragment == null) {
            mSearchResultFragment = SearchResultFragment.create()
            transaction.add(R.id.fl, mSearchResultFragment, SearchResultFragment::class.java.name)
        } else {
            mSearchResultFragment = searchResultFragment as SearchResultFragment
        }
        transaction.show(mSearchHistoryFragment)
        transaction.hide(mSearchResultFragment)
        transaction.commit()
        mIsResultPage = false
        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                p0?.let {
                    search(it)
                }
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {

                return false
            }


        })
        search_view.setOnCloseListener {
            FFLog.i("onClose")
            showHistoryFragment()
            false
        }
        search_view.isIconifiedByDefault = true

    }

    override fun initData() {

    }

    override fun startObserve() {
        requestSeaViewModel.searchKey.observe(this@SearchActivity, Observer {
           showResultFragment()
        })

    }

    fun search(key: String) {
        updateKey(key)
        showResultFragment()
        requestSeaViewModel.searchKey.value = key
    }

    fun showHistoryFragment() {
        FFLog.i()
        if (!mIsResultPage) return
        mIsResultPage = false
        val t: FragmentTransaction = mFragmentManager.beginTransaction()
        t.hide(mSearchResultFragment)
        t.show(mSearchHistoryFragment)
        t.commit()
    }

    fun showResultFragment() {
        FFLog.i()
        if (mIsResultPage) return
        mIsResultPage = true
        val t: FragmentTransaction = mFragmentManager.beginTransaction()
        t.hide(mSearchHistoryFragment)
        t.show(mSearchResultFragment)
        t.commit()
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