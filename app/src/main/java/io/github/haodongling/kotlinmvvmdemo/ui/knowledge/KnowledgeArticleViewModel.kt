package io.github.haodongling.kotlinmvvmdemo.ui.knowledge

import com.kunminx.architecture.ui.callback.UnPeekLiveData
import io.github.haodongling.kotlinmvvmdemo.model.repository.KnowledgeArticleRepository
import io.github.haodongling.lib.common.core.BaseViewModel
import io.github.haodongling.lib.common.model.bean.ArticleList
import kotlinx.coroutines.flow.collect

/**
 * Author: tangyuan
 * Time : 2021/9/23
 * Description:
 */
class KnowledgeArticleViewModel : BaseViewModel() {
    val repository = KnowledgeArticleRepository()
    val articleListData = UnPeekLiveData<BaseUiModel<ArticleList>>()
    open fun getSystemTypeDetail(isRefresh: Boolean, page: Int, cid: Int) {
        launchOnUI {
            repository.getSystemTypeDetail(isRefresh, page, cid).collect { it ->
                articleListData.postValue(it)
            }
        }
    }

}