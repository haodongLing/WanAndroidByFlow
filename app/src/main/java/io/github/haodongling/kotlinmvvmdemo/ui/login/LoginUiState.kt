package io.github.haodongling.kotlinmvvmdemo.ui.login

import io.github.haodongling.lib.common.core.BaseViewModel

class LoginUiState<T>(
    isLoading: Boolean = false,
    isSuccess: T? = null,
    isError: String? = null,
    val enableLoginButton: Boolean = false,
    val needLogin: Boolean = false
) : BaseViewModel.UiState<T>(isLoading, false, isSuccess, isError)
