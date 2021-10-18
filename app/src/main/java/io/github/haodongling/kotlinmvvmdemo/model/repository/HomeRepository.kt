package io.github.haodongling.kotlinmvvmdemo.model.repository

import io.github.haodongling.kotlinmvvmdemo.model.api.RetrofitClient
import io.github.haodongling.lib.common.core.BaseViewModel
import io.github.haodongling.lib.common.model.DTOResult
import io.github.haodongling.lib.common.model.bean.ArticleList
import io.github.haodongling.lib.common.model.bean.Banner
import io.github.haodongling.lib.common.model.bean.HttpCode
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
 * Time : 2021/8/19
 * Description:
 */
class HomeRepository : BaseRepository() {

    suspend fun getBanners() = flow<DTOResult<List<Banner>>> {
        RetrofitClient.wanService.getBanner().doSuccess { list ->
            emit(DTOResult.Success(list))
        }.doError { errMsg ->
            emit(DTOResult.Error(HttpCode(-1, errMsg.errMessage)))
        }
    }.flowOn(Dispatchers.IO).catch {
        emit(DTOResult.Error(HttpCode(-1, it.message)))
    }

    suspend fun getArticleList(page: Int, isRefresh: Boolean) = flow<BaseViewModel.BaseUiModel<ArticleList>> {
        RetrofitClient.wanService.getHomeArticles(page).doSuccess {
            emit(BaseViewModel.BaseUiModel(showSuccess = it, showLoading = false, isRefresh = isRefresh))
        }
    }.flowOn(Dispatchers.IO).onStart {
        emit(BaseViewModel.BaseUiModel(showLoading = true))
    }.catch {
        emit(BaseViewModel.BaseUiModel(showError = it.message, showLoading = false, showEnd = false))
    }

    suspend fun collectArticle(articleId: Int) = flow<BaseViewModel.UiState<ArticleList>> {
        RetrofitClient.wanService.collectArticle(articleId)
            .doSuccess {
                emit(BaseViewModel.UiState<ArticleList>(isSuccess = it, isLoading = false, isRefresh = false))
            }.doError { emit(BaseViewModel.UiState<ArticleList>(isError = it.errMessage)) }


    }.flowOn(Dispatchers.IO).onStart {
        emit(BaseViewModel.UiState<ArticleList>(isLoading = true))
    }.catch {
        emit(BaseViewModel.UiState<ArticleList>(isLoading = false, isError = it.message))
    }

    suspend fun unCollectArticle(articleId: Int) = flow<BaseViewModel.UiState<ArticleList>> {
        RetrofitClient.wanService.cancelCollectArticle(articleId)
            .doSuccess {
                emit(BaseViewModel.UiState<ArticleList>(isSuccess = it, isLoading = false, isRefresh = false))
            }.doError { emit(BaseViewModel.UiState<ArticleList>(isError = it.errMessage)) }
    }.flowOn(Dispatchers.IO).onStart {
        emit(BaseViewModel.UiState<ArticleList>(isLoading = true))
    }.catch {
        emit(BaseViewModel.UiState<ArticleList>(isLoading = false, isError = it.message))
    }


}