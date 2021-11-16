package io.github.haodongling.kotlinmvvmdemo.model.repository

import com.google.gson.Gson
import io.github.haodongling.kotlinmvvmdemo.model.api.RetrofitClient
import io.github.haodongling.lib.common.model.bean.User
import io.github.haodongling.kotlinmvvmdemo.ui.login.LoginUiState
import io.github.haodongling.lib.common.App
import io.github.haodongling.lib.common.model.doError
import io.github.haodongling.lib.common.model.doSuccess
import io.github.haodongling.lib.common.model.repository.BaseRepository
import io.github.haodongling.lib.common.util.Pref
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

/**
 * Author: tangyuan
 * Time : 2021/8/17
 * Description:
 */
class LoginRepository() : BaseRepository() {
    private var isLogin by Pref(Pref.IS_LOGIN, false)
    private var userJson by Pref(Pref.USER_GSON, "")
    suspend fun register(userName: String, password: String) = flow<LoginUiState<User>> {
        if (userName.isNullOrBlank() || password.isNullOrBlank()) {
            emit(LoginUiState(enableLoginButton = false))
            return@flow
        }
        RetrofitClient.userService.register(userName, password, password).doSuccess {
            emit(LoginUiState(needLogin = true))
        }.doError { err ->
            emit(LoginUiState(isError = err.errMessage, enableLoginButton = true))
        }
    }.onStart {
        emit(LoginUiState(needLogin = true))
    }.flowOn(Dispatchers.IO).catch { emit(LoginUiState(isError = it.message, enableLoginButton = true)) }


    suspend fun loginFlow(userName: String, passWord: String) = flow {

        // 输入不能为空
        if (userName.isBlank() || passWord.isBlank()) {
            emit(LoginUiState(enableLoginButton = false))
            return@flow
        }

        RetrofitClient.userService.login(userName, passWord).doSuccess { user ->
            isLogin = true
            userJson = Gson().toJson(user)
            emit(LoginUiState(isSuccess = user, enableLoginButton = true))
        }.doError { errorMsg ->
            emit(LoginUiState<User>(isError = errorMsg.errMessage, enableLoginButton = true))
        }
    }.onStart {
        emit(LoginUiState(isLoading = true))
    }.flowOn(Dispatchers.IO)
        .catch { emit(LoginUiState(isError = it.message, enableLoginButton = true)) }

}