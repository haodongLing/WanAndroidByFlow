package io.github.haodongling.lib.webview.cache

import com.tencent.smtt.export.external.interfaces.WebResourceRequest
import com.tencent.smtt.export.external.interfaces.WebResourceResponse
import com.tencent.smtt.sdk.WebView
import java.io.File
import java.io.InputStream

/**
 * Author: tangyuan
 * Time : 2021/9/10
 * Description:
 */
interface WebViewRequestClient {
   fun  interceptRequest(request: WebResourceRequest):WebResourceResponse

   fun interceptRequest(url:String):WebResourceResponse

   fun getCachePath():File

   fun clearCache()

   fun enableForce(force:Boolean)

   fun getCacheFile(url: String):InputStream?

   fun initAssetData()

   fun loadUrl(webView:WebView,url:String)

   fun loadUrl(url: String?, userAgent: String?)
   fun loadUrl(url: String?, additionalHttpHeaders: Map<String?, String?>?, userAgent: String?)
   fun loadUrl(webView: WebView?, url: String?, additionalHttpHeaders: Map<String?, String?>?)
}