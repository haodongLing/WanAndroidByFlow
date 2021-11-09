package io.github.haodongling.kotlinmvvmdemo.ui.home

import com.kunminx.architecture.ui.callback.UnPeekLiveData
import io.github.haodongling.kotlinmvvmdemo.model.bean.CollectUrlResponse
import io.github.haodongling.kotlinmvvmdemo.model.event.CollectEvent
import io.github.haodongling.kotlinmvvmdemo.model.repository.CollectRepository
import io.github.haodongling.lib.common.core.BaseViewModel
import io.github.haodongling.lib.common.extention.LiveDataBus
import io.github.haodongling.lib.common.global.BizConst
import kotlinx.coroutines.flow.collect

/**
 * Author: tangyuan
 * Time : 2021/11/9
 * Description:
 */
class CollectViewModel : BaseViewModel() {
    val collectRepository by lazy { CollectRepository() }
    val collectUrlState = UnPeekLiveData<UiState<CollectUrlResponse>>()
    val unCollectUrlState=UnPeekLiveData<UiState<Any?>>()

    fun collectArticle(articleId: Int, boolean: Boolean,position: Int) {
        launchOnUI {
            if (boolean) {
                collectRepository.collectArticle(articleId).collect {
                    LiveDataBus.get().with(BizConst.COLLECT_ARTICLE).postStickyData(CollectEvent(articleId,boolean,position))
                }
            } else {
                collectRepository.unCollectArticle(articleId).collect {
                    /*粘性事件，全局更新*/
                    LiveDataBus.get().with(BizConst.COLLECT_ARTICLE).postStickyData(CollectEvent(articleId,boolean,position))
                }
            }
        }
    }
    fun collectUrl(name: String, link: String){
        launchOnUI {
            collectRepository.collectUrl(name,link).collect{
                collectUrlState.value=it
            }
        }
    }
    fun unCollectUrl(id:Int){
        launchOnUI {
            collectRepository.unCollectUrl(id).collect {
                unCollectUrlState.value=it
            }
        }
    }


}