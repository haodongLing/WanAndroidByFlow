package com.haodong.kotlinmvvmdemo.model.repository

import com.didichuxing.doraemonkit.extension.err
import com.haodong.kotlinmvvmdemo.model.api.RetrofitClient
import com.haodong.kotlinmvvmdemo.model.api.WanService
import com.haodong.lib.common.core.BaseViewModel
import com.haodong.lib.common.model.DTOResult
import com.haodong.lib.common.model.bean.ArticleList
import com.haodong.lib.common.model.bean.Banner
import com.haodong.lib.common.model.bean.HttpCode
import com.haodong.lib.common.model.doError
import com.haodong.lib.common.model.doSuccess
import com.haodong.lib.common.model.repository.BaseRepository
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
    suspend fun getArticleList(page:Int,isRefresh:Boolean)= flow<BaseViewModel.BaseUiModel<ArticleList>> {
        RetrofitClient.wanService.getHomeArticles(page).doSuccess{
            emit(BaseViewModel.BaseUiModel(showSuccess = it,showLoading = false,isRefresh = isRefresh))
        }
    }.flowOn(Dispatchers.IO).onStart {
        emit(BaseViewModel.BaseUiModel(showLoading = true))
    }.catch {
        emit(BaseViewModel.BaseUiModel(showError = it.message,showLoading = false,showEnd = false))
    }
}