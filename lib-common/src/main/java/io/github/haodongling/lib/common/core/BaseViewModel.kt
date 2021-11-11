package io.github.haodongling.lib.common.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

/**
 * Author: tangyuan
 * Time : 2021/7/20
 * Description:
 */
open class BaseViewModel : ViewModel() {


    fun launchOnUI(block: suspend CoroutineScope.() -> Unit) {

        viewModelScope.launch { block() }

    }
    suspend fun <T> launchOnIO(block: suspend CoroutineScope.() -> T) {
        withContext(Dispatchers.IO) {
            block
        }
    }
    open class UiState<T>(
        val isLoading: Boolean = false,
        val isRefresh: Boolean = false,
        val isSuccess: T? = null,
        val isError: String?= null
    )


    open class BaseUiModel<T>(
        var showLoading: Boolean = false,
        var showError: String? = null,
        var showSuccess: T? = null,
        var showEnd: Boolean = false, // 加载更多
        var isRefresh: Boolean = false // 刷新

    )
    fun <T> BaseViewModel.launch(
        block: () -> T,
        success: (T) -> Unit,
        error: (Throwable) -> Unit = {}
    ) {
        viewModelScope.launch {
            kotlin.runCatching {
                withContext(Dispatchers.IO) {
                    block()
                }
            }.onSuccess {
                success(it)
            }.onFailure {
                error(it)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

}