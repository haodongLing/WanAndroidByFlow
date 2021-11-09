package io.github.haodongling.kotlinmvvmdemo.model.repository

import io.github.haodongling.kotlinmvvmdemo.model.api.RetrofitClient
import io.github.haodongling.lib.common.core.BaseViewModel
import io.github.haodongling.lib.common.model.bean.ArticleList
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
class WendaRepository :BaseRepository() {
    suspend fun getWenDaList(page: Int, isRefresh: Boolean) = flow<BaseViewModel.BaseUiModel<ArticleList>> {
        RetrofitClient.wanService.getWendaArticles(page).doSuccess {
            emit(BaseViewModel.BaseUiModel(showSuccess = it, showLoading = false, isRefresh = isRefresh))
        }
    }.flowOn(Dispatchers.IO).onStart {
        emit(BaseViewModel.BaseUiModel(showLoading = true))
    }.catch {
        emit(BaseViewModel.BaseUiModel(showError = it.message, showLoading = false, showEnd = false))
    }

}