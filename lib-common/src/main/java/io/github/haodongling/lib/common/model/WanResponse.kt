package io.github.haodongling.lib.common.model

import io.github.haodongling.lib.common.model.bean.HttpCode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope

data class WanResponse<out T>(val errorCode: Int, val errorMsg: String, val data: T)

suspend fun <T : Any> WanResponse<T>.executeResponse(successBlock: (suspend CoroutineScope.() -> Unit)? = null,
                                                     errorBlock: (suspend CoroutineScope.() -> Unit)? = null): DTOResult<T> {
    return coroutineScope {
        if (errorCode == -1) {
            errorBlock?.let { it() }
            DTOResult.Error(HttpCode(-1,errorMsg))
        } else {
            successBlock?.let { it() }
            DTOResult.Success(data)
        }
    }
}

suspend fun <T : Any> WanResponse<T>.doSuccess(successBlock: (suspend CoroutineScope.(T) -> Unit)? = null): WanResponse<T> {
    return coroutineScope {
        if (errorCode != -1) successBlock?.invoke(this, this@doSuccess.data)
        this@doSuccess
    }

}

suspend fun <T : Any> WanResponse<T>.doError(errorBlock: (suspend CoroutineScope.(HttpCode) -> Unit)? = null): WanResponse<T> {
    return coroutineScope {
        if (errorCode == -1) errorBlock?.invoke(this, HttpCode(this@doError.errorCode,this@doError.errorMsg))
        this@doError
    }
}

