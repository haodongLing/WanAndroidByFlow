package io.github.haodongling.kotlinmvvmdemo.web.js

import android.os.Handler
import android.os.Looper
import com.tencent.smtt.sdk.WebView

/**
 * Author: tangyuan
 * Time : 2021/9/28
 * Description:
 */
class JsInjector( private val webView:WebView):Handler(Looper.getMainLooper()) {
    private val JS_NAME= JsInjector::class.java.simpleName
    private val jsInterface= mutableListOf<BaseJsInterface>()

}