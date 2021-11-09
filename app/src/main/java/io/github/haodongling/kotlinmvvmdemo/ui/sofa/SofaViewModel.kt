package io.github.haodongling.kotlinmvvmdemo.ui.sofa

import io.github.haodongling.kotlinmvvmdemo.model.repository.HomeRepository
import io.github.haodongling.lib.common.core.BaseViewModel
import io.github.haodongling.lib.common.model.bean.ArticleList
import com.kunminx.architecture.ui.callback.UnPeekLiveData
import io.github.haodongling.kotlinmvvmdemo.model.repository.WendaRepository
import kotlinx.coroutines.flow.collect

/**
 * Author: tangyuan
 * Time : 2021/9/1
 * Description:
 */
class SofaViewModel :BaseViewModel() {
    val repository=WendaRepository()
    val articleState=UnPeekLiveData<BaseUiModel<ArticleList>>()

    fun getWendaList(page: Int, isRefresh: Boolean) {
        launchOnUI {
            repository.getWenDaList(page, isRefresh).collect {
                articleState.postValue(it)
            }
        }
    }

}