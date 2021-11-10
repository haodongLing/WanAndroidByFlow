package io.github.haodongling.kotlinmvvmdemo.model.repository

import io.github.haodongling.kotlinmvvmdemo.model.api.RetrofitClient
import io.github.haodongling.kotlinmvvmdemo.model.bean.CollectUrlBean
import io.github.haodongling.lib.common.core.BaseViewModel
import io.github.haodongling.lib.common.model.bean.ArticleList
import io.github.haodongling.lib.common.model.doError
import io.github.haodongling.lib.common.model.doSuccess
import io.github.haodongling.lib.common.model.repository.BaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart

/**
 * Author: tangyuan
 * Time : 2021/11/9
 * Description:
 */
class CollectRepository : BaseRepository() {


    suspend fun collectArticle(articleId: Int) = flow<BaseViewModel.UiState<ArticleList>> {
        RetrofitClient.wanService.collectArticle(articleId).doSuccess {
            emit(BaseViewModel.UiState<ArticleList>(isSuccess = it, isLoading = false, isRefresh = false))
        }.doError { emit(BaseViewModel.UiState<ArticleList>(isError = it.errMessage)) }


    }.flowOn(Dispatchers.IO).onStart {
        emit(BaseViewModel.UiState<ArticleList>(isLoading = true))
    }.catch {
        emit(BaseViewModel.UiState<ArticleList>(isLoading = false, isError = it.message))
    }

    suspend fun unCollectArticle(articleId: Int) = flow<BaseViewModel.UiState<ArticleList>> {
        RetrofitClient.wanService.cancelCollectArticle(articleId).doSuccess {
            emit(BaseViewModel.UiState<ArticleList>(isSuccess = it, isLoading = false, isRefresh = false))
        }.doError { emit(BaseViewModel.UiState<ArticleList>(isError = it.errMessage)) }
    }.flowOn(Dispatchers.IO).onStart {
        emit(BaseViewModel.UiState<ArticleList>(isLoading = true))
    }.catch {
        emit(BaseViewModel.UiState<ArticleList>(isLoading = false, isError = it.message))
    }

    /**
     * 收藏网址
     */
    suspend fun collectUrl(name: String, link: String) = flow<BaseViewModel.UiState<CollectUrlBean>> {
        RetrofitClient.wanService.collectUrl(name,link).doSuccess {
            emit(BaseViewModel.UiState<CollectUrlBean>(isSuccess = it, isLoading = false, isRefresh = false))
        }.doError { emit(BaseViewModel.UiState<CollectUrlBean>(isError = it.errMessage)) }
    }.flowOn(Dispatchers.IO).onStart {
        emit(BaseViewModel.UiState<CollectUrlBean>(isLoading = true))
    }.catch {
        emit(BaseViewModel.UiState<CollectUrlBean>(isLoading = false, isError = it.message))
    }
    /**
     * 取消收藏网址
     */
    suspend fun unCollectUrl(id:Int) = flow<BaseViewModel.UiState<Any?>> {
        RetrofitClient.wanService.deleteTool(id).doSuccess {
            emit(BaseViewModel.UiState<Any?>(isSuccess = it, isLoading = false, isRefresh = false))
        }.doError { emit(BaseViewModel.UiState<Any?>(isError = it.errMessage)) }
    }.flowOn(Dispatchers.IO).onStart {
        emit(BaseViewModel.UiState<Any?>(isLoading = true))
    }.catch {
        emit(BaseViewModel.UiState<Any?>(isLoading = false, isError = it.message))
    }
}

