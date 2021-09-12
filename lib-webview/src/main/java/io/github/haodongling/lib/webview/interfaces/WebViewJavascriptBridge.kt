package io.github.haodongling.lib.webview.interfaces

/**
 * Author: tangyuan
 * Time : 2021/9/10
 * Description:
 */
interface WebViewJavascriptBridge {
    fun callHandler(handlerName: String?)
    fun callHandler(handlerName: String?, data: String?)
    fun callHandler(handlerName: String?, data: String?, callBack: CallBackFunction?)
    fun unregisterHandler(handlerName: String?)
    fun registerHandler(handlerName: String?, handler: BridgeHandler?)
}