package io.github.haodongling.kotlinmvvmdemo.web.js

import com.tencent.smtt.sdk.WebView
import io.github.haodongling.lib.common.util.FFLog
import io.github.haodongling.lib.utils.global.AppGlobals


abstract class BaseJsInterface(val jsName: String) {

    protected lateinit var webView: WebView
    private val jsString: String? by lazy {
        val jsStr = loadJsFromString()
        if (!jsStr.isNullOrEmpty()) {
            return@lazy jsStr
        }
        val assetsName = loadJsFromAssets()
        if (assetsName.isNullOrEmpty()) {
            return@lazy null
        }
        try {
            AppGlobals.getApplication().resources.assets.open(assetsName).use {
                try {
                    val buffer = ByteArray(it.available())
                    it.read(buffer)
                    String(buffer)
                } catch (e: Throwable) {
                    null
                }
            }
        } catch (e: Throwable) {
            null
        }
    }
    private val jsInitCode: String? by lazy {
        loadJsInitCode()
    }

    abstract fun loadJsFromString(): String?
    abstract fun loadJsFromAssets(): String?
    abstract fun loadJsInitCode(): String?
    open fun onlyDebug(): Boolean = false

    fun attach(webView: WebView) {
        this.webView = webView
    }

    fun inject() {
        if (FFLog.hideLog) return
        if (!jsString.isNullOrEmpty()) {
            webView.loadUrl("javascript: $jsString")
        }
        if (!jsInitCode.isNullOrEmpty()) {
            val name = this::class.java.simpleName
            webView.loadUrl("""
                javascript: var init$name = document.createElement("script");
                init$name.id= "init$name";
                init$name.text= "$jsInitCode";
                document.body.appendChild(init$name);
            """.trimIndent())
        }
    }
}