package io.github.haodongling.lib.webview

import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.AttributeSet
import com.tencent.smtt.sdk.WebView
import io.github.haodongling.lib.webview.utils.X5LogUtils
import java.lang.ref.WeakReference

/**
 * Author: tangyuan
 * Time : 2021/9/10
 * Description:
 */
class BaseWebView : WebView {
    companion object {
        const val EXEC_SCRIPT = 1
        const val LOAD_URL = 2
        const val LOAD_URL_WITH_HEADERS = 3
    }

    constructor (context: Context) : this(context, null) {

    }

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0) {

    }

    constructor(context: Context, attributeSet: AttributeSet?, i: Int) : super(context, attributeSet, i) {

        init()
    }

    private lateinit var mainThreadHandler: MyHandler

    private fun init() {
        mainThreadHandler = MyHandler(context)
    }

    class MyHandler(context: Context) : Handler(Looper.getMainLooper()) {
        val mContextReference: WeakReference<Context> = WeakReference(context)
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            val context = mContextReference.get();
            context?.let {
                when (msg.what) {
                    EXEC_SCRIPT -> {

                    }
                    LOAD_URL -> {

                    }
                    LOAD_URL_WITH_HEADERS -> {

                    }
                }
            }
        }

    }

    fun evaluateJavascriptUrl(script: String) {

        //BaseWebView.super.evaluateJavascript(script, null);
        super@BaseWebView.evaluateJavascript(
            script
        ) { s -> X5LogUtils.i("---evaluateJavascript-1--$s") }
    }

    override fun loadUrl(p0: String?) {
        val msg: Message = mainThreadHandler.obtainMessage(LOAD_URL, url)
        mainThreadHandler.sendMessage(msg)
    }

    override fun loadUrl(url: String?, additionalHttpHeaders: Map<String, String>) {
        if (url == null || url.isEmpty()) {
            return
        }
        val msg: Message = mainThreadHandler.obtainMessage(
            LOAD_URL_WITH_HEADERS,
            RequestInfo(url, additionalHttpHeaders = additionalHttpHeaders)
        )
        mainThreadHandler.sendMessage(msg)
    }

}