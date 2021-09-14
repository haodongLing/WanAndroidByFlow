package io.github.haodongling.lib.webview.interfaces

/**
 * Author: tangyuan
 * Time : 2021/9/10
 * Description:
 */
interface BridgeHandler {
    fun handler(data: String, function: CallBackFunction?=null)
}