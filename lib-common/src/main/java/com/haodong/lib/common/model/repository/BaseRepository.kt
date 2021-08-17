package com.haodong.lib.common.model.repository

import android.widget.Toast
import com.didichuxing.doraemonkit.util.NetworkUtils
import com.haodong.lib.common.App
import com.haodong.lib.common.ext.toast
import com.haodong.lib.common.model.DTOResult
import com.haodong.lib.common.model.WanResponse
import com.haodong.lib.common.model.bean.HttpCode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

open class BaseRepository {

    suspend fun <T : Any> apiCall(call: suspend () -> WanResponse<T>): WanResponse<T> {
        return call.invoke()
    }

    suspend fun <T : Any> safeApiCall(call: suspend () -> DTOResult<T>, errorMessage: String): DTOResult<T> {
        return try {
            call()
        } catch (e: Exception) {
            // An exception was thrown when calling the API so we're converting this to an IOException
            DTOResult.Error(HttpCode(-1, errorMessage))
        }
    }

    suspend fun <T : Any> executeResponse(
        response: WanResponse<T>, successBlock: (suspend CoroutineScope.() -> Unit)? = null,
        errorBlock: (suspend CoroutineScope.() -> Unit)? = null
    ): DTOResult<T> {
        return coroutineScope {
            if (response.errorCode == -1) {
                errorBlock?.let { it() }
                DTOResult.Error(HttpCode(-1, response.errorMsg))
            } else {
                successBlock?.let { it() }
                DTOResult.Success(response.data)
            }
        }
    }

    suspend fun <T : Any> executeResponseFlow(
        response: WanResponse<T>
    ): Flow<DTOResult<T>> {
        return flow {
            if (!NetworkUtils.isConnected()) {
                withContext(Dispatchers.Main) {
                    App.CONTEXT.toast(HttpCode.HttpEnum.HTTP_ERROR_CONNECT.message, Toast.LENGTH_SHORT)
                }
                emit(
                    DTOResult.Error(
                        HttpCode(
                            HttpCode.HttpEnum.HTTP_ERROR_CONNECT.code,
                            HttpCode.HttpEnum.HTTP_ERROR_CONNECT.message
                        )
                    )
                )
            }
            response.run {
                if (errorCode == -1) {
                    emit(DTOResult.Error(HttpCode(-1, errorMsg)))
                } else {
                    emit(DTOResult.Success(data))
                }
            }

//            if (response.code == 200) {
//                emit(DTOResult.Success(response.data))
//            } else {
//                when (response.code) {
//                    201 -> {
//                        withContext(Dispatchers.Main) {
//                            val intent = Intent()
//                            intent.action = "login_action"
//                            BaseApplicationLike.getApplicationContext().sendBroadcast(intent)
//                        }
//                        emit(DTOResult.Error(HttpCode(response.code, response.message)))
//                    }
//                    212 -> {
//                        withContext(Dispatchers.Main) {
//                            val intent = Intent()
//                            intent.action = "update_action"
//                            BaseApplicationLike.getApplicationContext().sendBroadcast(intent)
//                        }
//                        emit(DTOResult.Error(HttpCode(response.code, response.message)))
//                    }
//                    else -> {
//                        withContext(Dispatchers.Main) {
//                            if (!TextUtils.isEmpty(response.message)) {
//                                ToastUtil.showShort(response.message)
//                            }
//                        }
//                        emit(DTOResult.Error(HttpCode(response.code, response.message)))
//                    }
//                }
//            }
        }.flowOn(Dispatchers.IO).catch {
            emit(
                DTOResult.Error(
                    HttpCode(
                        HttpCode.HttpEnum.HTTP_ERROR_CONNECT.code,
                        HttpCode.HttpEnum.HTTP_ERROR_CONNECT.message
                    )
                )
            )
        }


    }
}