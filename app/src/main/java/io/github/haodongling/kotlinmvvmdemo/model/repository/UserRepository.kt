package io.github.haodongling.kotlinmvvmdemo.model.repository

import io.github.haodongling.kotlinmvvmdemo.model.api.RetrofitClient
import io.github.haodongling.lib.common.core.BaseViewModel
import io.github.haodongling.lib.common.model.bean.UserDetail
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
 * Time : 2021/9/15
 * Description:
 */
class UserRepository : BaseRepository() {
    suspend fun getUserDetail() = flow<BaseViewModel.BaseUiModel<UserDetail>> {
        RetrofitClient.userService.getUserDetail().doSuccess {
            emit(BaseViewModel.BaseUiModel(showLoading = false, showSuccess = it))
        }.doError { err ->
            emit(BaseViewModel.BaseUiModel(showLoading = false, showError = err.errMessage))
        }
    }.onStart {}.flowOn(Dispatchers.IO).catch {
        emit(BaseViewModel.BaseUiModel(showError = it.message, showLoading = false, showEnd = false))
    }
}