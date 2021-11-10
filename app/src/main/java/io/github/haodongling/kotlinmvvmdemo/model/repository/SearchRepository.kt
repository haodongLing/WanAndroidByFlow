package io.github.haodongling.kotlinmvvmdemo.model.repository

import io.github.haodongling.kotlinmvvmdemo.model.api.RetrofitClient
import io.github.haodongling.kotlinmvvmdemo.model.bean.SearchBean
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
 * Time : 2021/11/10
 * Description:
 */
class SearchRepository : BaseRepository() {

    /**
     * 收藏网址
     */
    suspend fun getHotData() = flow<BaseViewModel.UiState<ArrayList<SearchBean>>> {
        RetrofitClient.wanService.getSearchData().doSuccess {
            emit(BaseViewModel.UiState<ArrayList<SearchBean>>(isSuccess = it, isLoading = false, isRefresh = false))
        }.doError { emit(BaseViewModel.UiState<ArrayList<SearchBean>>(isError = it.errMessage)) }
    }.flowOn(Dispatchers.IO).onStart {
        emit(BaseViewModel.UiState<ArrayList<SearchBean>>(isLoading = true))
    }.catch {
        emit(BaseViewModel.UiState<ArrayList<SearchBean>>(isLoading = false, isError = it.message))
    }

    suspend fun getSearchDataByKey(pageNo: Int, searchKey: String) = flow<BaseViewModel.UiState<ArticleList>> {
        RetrofitClient.wanService.getSearchDataByKey(pageNo, searchKey).doSuccess {
            emit(BaseViewModel.UiState(isSuccess = it, isLoading = false))
        }.doError {
            emit(BaseViewModel.UiState(isError = it.errMessage))
        }

    }.flowOn(Dispatchers.IO).onStart {
        emit(BaseViewModel.UiState(isLoading = true))
    }.catch {
        emit(BaseViewModel.UiState(isLoading = false, isError = it.message))
    }

}