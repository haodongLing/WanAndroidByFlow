package io.github.haodongling.kotlinmvvmdemo.ui.search

import androidx.lifecycle.MutableLiveData
import com.kunminx.architecture.ui.callback.UnPeekLiveData
import io.github.haodongling.kotlinmvvmdemo.model.bean.SearchBean
import io.github.haodongling.kotlinmvvmdemo.model.repository.SearchRepository
import io.github.haodongling.kotlinmvvmdemo.util.CaCheUtil
import io.github.haodongling.lib.common.core.BaseViewModel
import io.github.haodongling.lib.common.model.bean.ArticleList
import io.github.haodongling.lib.common.util.FFLog
import kotlinx.coroutines.flow.collect

/**
 * Author: tangyuan
 * Time : 2021/11/10
 * Description:
 */
class SearchViewModel : BaseViewModel() {
    // 搜索热词
    var hotDataState = MutableLiveData<UiState<ArrayList<SearchBean>>>()

    // 搜索结果
    var searchResultState = MutableLiveData<UiState<ArticleList>>()

    // 搜索历史
    var historyData = MutableLiveData<ArrayList<String>>()
    private val searchRepository: SearchRepository by lazy { SearchRepository() }

    val searchKey=UnPeekLiveData<String>();

    fun getHotData() {
        launchOnUI {
            searchRepository.getHotData().collect {
                hotDataState.value = it
            }
        }
    }

    fun getSearchDataByKey(pageNo: Int, searchKey: String) {
        launchOnUI {
            searchRepository.getSearchDataByKey(pageNo, searchKey).collect {
                FFLog.i()
                searchResultState.value = it
            }
        }
    }

    fun getHistoryData() {
        launch({
            CaCheUtil.getSearchHistoryData()
        }, {
            historyData.value = it
        }, {
            //获取本地历史数据出异常了
        })
        if (historyData.value==null){
            historyData.value=ArrayList()
        }
    }


}