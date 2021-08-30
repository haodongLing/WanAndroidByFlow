package com.haodong.kotlinmvvmdemo.model.repository

import androidx.lifecycle.MutableLiveData
import com.haodong.lib.common.core.BaseViewModel
import com.haodong.lib.common.model.DTOResult
import com.haodong.lib.common.model.bean.ArticleList
import com.haodong.lib.common.model.bean.Banner
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext

/**
 * Author: tangyuan
 * Time : 2021/8/19
 * Description:
 */
class HomeViewModel : BaseViewModel() {
    val repository = HomeRepository()
    val articleState = MutableLiveData<BaseUiModel<ArticleList>>()
    val bannerState = MutableLiveData<DTOResult<List<Banner>>>()

    fun getArticleList(page: Int, isRefresh: Boolean) {
        launchOnUI {
            repository.getArticleList(page, isRefresh).collect {
                articleState.postValue(it)
            }
        }
    }

    fun getbanner() {
        launchOnUI {
            repository.getBanners().collect {
                bannerState.postValue(it)
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