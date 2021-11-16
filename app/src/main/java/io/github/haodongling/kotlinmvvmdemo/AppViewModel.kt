package io.github.haodongling.kotlinmvvmdemo

import com.kunminx.architecture.ui.callback.UnPeekLiveData
import io.github.haodongling.kotlinmvvmdemo.util.CaCheUtil
import io.github.haodongling.lib.common.core.BaseViewModel
import io.github.haodongling.lib.common.model.bean.User

/**
 * Author: tangyuan
 * Time : 2021/11/16
 * Description:
 */
class AppViewModel : BaseViewModel() {
    var userInfo=UnPeekLiveData.Builder<User>().setAllowNullValue(true).create()
    init {
        userInfo.value=CaCheUtil.getUser()
    }

}