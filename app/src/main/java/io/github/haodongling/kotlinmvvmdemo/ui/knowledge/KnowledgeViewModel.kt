package io.github.haodongling.kotlinmvvmdemo.ui.knowledge

import com.kunminx.architecture.ui.callback.UnPeekLiveData
import io.github.haodongling.kotlinmvvmdemo.model.repository.KnowledgeRepository
import io.github.haodongling.lib.common.core.BaseViewModel
import io.github.haodongling.lib.common.model.bean.Navigation
import io.github.haodongling.lib.common.model.bean.SystemChild
import io.github.haodongling.lib.common.model.bean.SystemParent
import kotlinx.coroutines.flow.collect

/**
 * Author: tangyuan
 * Time : 2021/9/3
 * Description:
 */
class KnowledgeViewModel :BaseViewModel() {
    val repository=KnowledgeRepository()
    val systemData=UnPeekLiveData<BaseUiModel<List<SystemChild>>>()
    val navigationData=UnPeekLiveData<BaseUiModel<List<Navigation>>>()

    open fun getNavigation(){
        launchOnUI {
            repository.getNavigation().collect {
                navigationData.postValue(it)
            }
        }
    }
    open fun getSystem(){
        launchOnUI {
            repository.getSystemType(isRefresh = true).collect {
                systemData.postValue(it)
            }
        }
    }




}