package io.github.haodongling.lib.webview.interfaces

import io.github.haodongling.lib.webview.utils.X5WebUtils

/**
 * Author: tangyuan
 * Time : 2021/9/10
 * Description:
 */
interface InterWebListener {
    /**
     * 隐藏进度条
     */
    fun hindProgressBar()

    /**
     * 展示异常页面
     * @param type                           异常类型
     */
    fun showErrorView(@X5WebUtils.ErrorType type: Int)

    /**
     * 进度条变化时调用，这里添加注解限定符，必须是在0到100之间
     * @param newProgress                   进度0-100
     */
    fun startProgress(newProgress: Int)

    /**
     * 获取加载网页的标题
     * @param title                         title标题
     */
    fun showTitle(title: String?)

    /**
     * 加载完成
     * @param url                           连接
     */
    fun onPageFinished(url: String?)
}