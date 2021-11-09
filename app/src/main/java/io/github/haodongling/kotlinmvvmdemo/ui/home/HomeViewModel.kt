package io.github.haodongling.kotlinmvvmdemo.ui.home

import androidx.lifecycle.MutableLiveData
import io.github.haodongling.lib.common.core.BaseViewModel
import io.github.haodongling.lib.common.model.DTOResult
import io.github.haodongling.lib.common.model.bean.ArticleList
import io.github.haodongling.lib.common.model.bean.Banner
import com.kunminx.architecture.ui.callback.UnPeekLiveData
import io.github.haodongling.kotlinmvvmdemo.model.repository.HomeRepository
import kotlinx.coroutines.flow.collect

/**
 * Author: tangyuan
 * Time : 2021/8/19
 * Description:
 */
class HomeViewModel : BaseViewModel() {
    val repository = HomeRepository()
    val articleState = UnPeekLiveData<BaseUiModel<ArticleList>>()
    val bannerState = UnPeekLiveData<DTOResult<List<Banner>>>()
    val collectState= MutableLiveData<UiState<ArticleList>>()

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
                bannerState.value=it
            }
        }
    }

}