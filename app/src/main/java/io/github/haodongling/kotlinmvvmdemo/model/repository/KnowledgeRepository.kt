package io.github.haodongling.kotlinmvvmdemo.model.repository

import io.github.haodongling.kotlinmvvmdemo.model.api.RetrofitClient
import io.github.haodongling.lib.common.core.BaseViewModel
import io.github.haodongling.lib.common.model.bean.Navigation
import io.github.haodongling.lib.common.model.bean.SystemChild
import io.github.haodongling.lib.common.model.bean.SystemParent
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
 * Time : 2021/9/3
 * Description: 体系
 */
class KnowledgeRepository : BaseRepository() {
    suspend fun getSystemType(isRefresh: Boolean) = flow<BaseViewModel.BaseUiModel<List<SystemChild>>> {
        RetrofitClient.wanService.getSystemType()
            .doSuccess {
                emit(
                    BaseViewModel.BaseUiModel(showSuccess = it, showLoading = false, isRefresh = isRefresh)
                )
            }
            .doError {
                emit(BaseViewModel.BaseUiModel(showLoading = false, showError = it.errMessage))
            }
    }.flowOn(Dispatchers.IO).onStart {
        emit(BaseViewModel.BaseUiModel(showLoading = true))
    }.catch {
        emit(BaseViewModel.BaseUiModel(showError = it.message, showLoading = false, isRefresh = isRefresh))
    }

    suspend fun getNavigation() = flow<BaseViewModel.BaseUiModel<List<Navigation>>> {
        RetrofitClient.wanService.getNavigation().doSuccess {
            emit(BaseViewModel.BaseUiModel(showSuccess = it, showLoading = false))
        }
            .doError { emit(BaseViewModel.BaseUiModel(showLoading = false, showError = it.errMessage)) }
    }.flowOn(Dispatchers.IO).onStart {
        emit(BaseViewModel.BaseUiModel(showLoading = true))
    }.catch {
        emit(BaseViewModel.BaseUiModel(showError = it.message, showLoading = false))
    }
}