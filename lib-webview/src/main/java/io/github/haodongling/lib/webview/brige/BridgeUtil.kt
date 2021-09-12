package io.github.haodongling.lib.webview.brige

import io.github.haodongling.lib.webview.utils.X5LogUtils

/**
 * Author: tangyuan
 * Time : 2021/9/10
 * Description:
 */
object BridgeUtil {
    const val YY_OVERRIDE_SCHEMA = "yy://"

    /**
     * 格式为   yy://return/{function}/returncontent
     */
    const val YY_RETURN_DATA = YY_OVERRIDE_SCHEMA + "return/"
    const val YY_FETCH_QUEUE = YY_RETURN_DATA + "_fetchQueue/"
    const val EMPTY_STR = ""
    const val UNDERLINE_STR = "_"
    const val SPLIT_MARK = "/"
    const val CALLBACK_ID_FORMAT = "JAVA_CB_%s"
    const val JS_HANDLE_MESSAGE_FROM_JAVA = "javascript:WebViewJavascriptBridge._handleMessageFromNative('%s');"
    const val JS_FETCH_QUEUE_FROM_JAVA = "javascript:WebViewJavascriptBridge._fetchQueue();"
    const val JAVASCRIPT_STR = "javascript:"

    /**
     * 例子 javascript:WebViewJavascriptBridge._fetchQueue(); --> _fetchQueue
     * @param jsUrl                url
     * @return                    返回字符串，注意获取的时候判断空
     */
    @JvmStatic
    fun parseFunctionName(jsUrl: String): String {
        return jsUrl.replace("javascript:WebViewJavascriptBridge.", "").replace("\\(.*\\);".toRegex(), "")
    }
    @JvmStatic
    fun getDataFromReturnUrl(url: String): String? {
        if (url.startsWith(BridgeUtil.YY_FETCH_QUEUE)) {
            // return = [{"responseId":"JAVA_CB_2_3957","responseData":"Javascript Says Right back aka!"}]
            return url.replace(BridgeUtil.YY_FETCH_QUEUE, BridgeUtil.EMPTY_STR)
        }

        // temp = _fetchQueue/[{"responseId":"JAVA_CB_2_3957","responseData":"Javascript Says Right back aka!"}]

        // temp = _fetchQueue/[{"responseId":"JAVA_CB_2_3957","responseData":"Javascript Says Right back aka!"}]
        val temp: String = url.replace(BridgeUtil.YY_RETURN_DATA, BridgeUtil.EMPTY_STR)
        val functionAndData: Array<String> = temp.split(BridgeUtil.SPLIT_MARK).toTypedArray()

        if (functionAndData.size >= 2) {
            val sb = StringBuilder()
            for (i in 1 until functionAndData.size) {
                sb.append(functionAndData[i])
            }
            // return = [{"responseId":"JAVA_CB_2_3957","responseData":"Javascript Says Right back aka!"}]
            return sb.toString()
        }
        return null
    }
    @JvmStatic
    fun getFunctionFromReturnUrl(url: String): String? {
        val temp = url.replace(BridgeUtil.YY_RETURN_DATA, BridgeUtil.EMPTY_STR)
        X5LogUtils.i("------BridgeUtil--------getFunctionFromReturnUrl--$temp")
        val functionAndData = temp.split(BridgeUtil.SPLIT_MARK).toTypedArray()
        return if (functionAndData.size >= 1) {
            // functionAndData[0] = _fetchQueue
            functionAndData[0]
        } else null
    }
}