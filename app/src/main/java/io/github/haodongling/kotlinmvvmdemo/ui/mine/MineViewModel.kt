package io.github.haodongling.kotlinmvvmdemo.ui.mine

import com.kunminx.architecture.ui.callback.UnPeekLiveData
import io.github.haodongling.kotlinmvvmdemo.model.repository.UserRepository
import io.github.haodongling.lib.common.core.BaseViewModel
import io.github.haodongling.lib.common.model.bean.UserDetail
import kotlinx.coroutines.flow.collect

/**
 * Author: tangyuan
 * Time : 2021/9/26
 * Description:
 */
class MineViewModel : BaseViewModel() {
    val mineRepository=UserRepository();
    val uiState=UnPeekLiveData<BaseViewModel.BaseUiModel<UserDetail>>()

    fun getUserDetail(){
        launchOnUI {
            mineRepository.getUserDetail().collect{
                uiState.value=it
            }
        }
    }

}