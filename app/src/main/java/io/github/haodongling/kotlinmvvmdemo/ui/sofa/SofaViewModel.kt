package io.github.haodongling.kotlinmvvmdemo.ui.sofa

import io.github.haodongling.kotlinmvvmdemo.model.repository.HomeRepository
import io.github.haodongling.lib.common.core.BaseViewModel
import io.github.haodongling.lib.common.model.bean.ArticleList
import com.kunminx.architecture.ui.callback.UnPeekLiveData
import kotlinx.coroutines.flow.collect

/**
 * Author: tangyuan
 * Time : 2021/9/1
 * Description:
 */
class SofaViewModel :BaseViewModel() {
    val repository=HomeRepository()
    val articleState=UnPeekLiveData<BaseUiModel<ArticleList>>()

    fun getArticleList(page: Int, isRefresh: Boolean) {
        launchOnUI {
            repository.getArticleList(page, isRefresh).collect {
                articleState.postValue(it)
            }
        }
    }

    fun collectArticle(articleId:Int,boolean: Boolean){
        launchOnUI {
            if (boolean){
                repository.collectArticle(articleId).collect {  }
            }else{
                repository.unCollectArticle(articleId)
            }
        }
    }



}