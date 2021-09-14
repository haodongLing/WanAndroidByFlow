package io.github.haodongling.lib.webview.brige

import io.github.haodongling.lib.webview.interfaces.BridgeHandler
import io.github.haodongling.lib.webview.interfaces.CallBackFunction

/**
 * Author: tangyuan
 * Time : 2021/9/14
 * Description:
 */
class DefaultHandler : BridgeHandler {
    override fun handler(data: String, function: CallBackFunction?) {
        function?.let { function.onCallBack("defaultHandler response data") }
    }
}