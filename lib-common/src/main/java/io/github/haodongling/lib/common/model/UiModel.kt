package io.github.haodongling.lib.common.model

/**
 * Author: tangyuan
 * Time : 2021/10/12
 * Description:
 */
class UiModel<T>( var showLoading: Boolean = false,
                      var showError: String? = null,
                      var showSuccess: T? = null,
                      var showEnd: Boolean = false, // 加载更多
                      var isRefresh: Boolean = false // 刷新
)