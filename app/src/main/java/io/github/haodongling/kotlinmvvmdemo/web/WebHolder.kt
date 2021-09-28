package io.github.haodongling.kotlinmvvmdemo.web

import android.net.Uri
import com.tencent.smtt.export.external.interfaces.WebResourceResponse

/**
 * Author: tangyuan
 * Time : 2021/9/28
 * Description:
 */
class WebHolder {
    private var mOnPageScrollEndListener:OnPageScrollEndListener?=null
    private val mOnPageTitleCallback: OnPageTitleCallback? = null
    private val mOnPageLoadCallback: OnPageLoadCallback? = null
    private val mOnPageProgressCallback: OnPageProgressCallback? = null
    private val mOnHistoryUpdateCallback: OnHistoryUpdateCallback? = null
    private val mOverrideUrlInterceptor: OverrideUrlInterceptor? = null
    private val mInterceptUrlInterceptor: InterceptUrlInterceptor? = null


}

interface OnPageScrollEndListener {
    fun onPageScrollEnd()
}

interface OnPageTitleCallback{
  fun  onReceivedTitle(title:String)
}

interface OnPageLoadCallback{
    fun onPageStarted()
    fun onPageFinished()
}

interface OnPageProgressCallback{
    fun showProgress()
    fun onProgressChanged(progress:Int)
    fun onHideProgress()
}
interface OnHistoryUpdateCallback {
    fun onHistoryUpdate(isReload: Boolean)
}

interface OverrideUrlInterceptor {
    fun onOverrideUrl(url: String?): Boolean
}

interface InterceptUrlInterceptor {
    fun onInterceptUrl(
        reqUri: Uri, reqHeaders: Map<String?, String?>?, reqMethod: String?
    ): WebResourceResponse?
}