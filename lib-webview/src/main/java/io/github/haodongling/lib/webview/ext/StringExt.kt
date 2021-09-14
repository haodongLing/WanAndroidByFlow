package io.github.haodongling.lib.webview.ext

/**
 * Author: tangyuan
 * Time : 2021/9/10
 * Description:
 */
class StringExt {
    fun String.isHtml(str: String): Boolean {
        if (str.isNullOrEmpty()) {
            return false
        }
        if (str.toLowerCase().contains("html") || str.toLowerCase().contains("htm")) {
            return true
        }
        return false
    }
}