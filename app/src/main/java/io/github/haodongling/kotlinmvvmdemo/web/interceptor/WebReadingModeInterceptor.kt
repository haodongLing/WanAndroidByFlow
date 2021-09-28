package io.github.haodongling.kotlinmvvmdemo.web.interceptor

import android.net.Uri
import com.alibaba.fastjson.JSON
import com.tencent.smtt.export.external.interfaces.WebResourceResponse
import io.github.haodongling.kotlinmvvmdemo.web.cache.HtmlCacheManager
import io.github.haodongling.kotlinmvvmdemo.web.css.CssStyleManager
import io.github.haodongling.lib.common.model.bean.RegexBean
import io.github.haodongling.lib.common.model.bean.WebArticleUrlRegexBean
import io.github.haodongling.lib.common.util.AppConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.ByteArrayInputStream
import java.util.regex.Pattern

object WebReadingModeInterceptor : BaseWebUrlInterceptor() {
    lateinit var urlRegexList:List<WebArticleUrlRegexBean>

    fun setup() {
        GlobalScope.launch (Dispatchers.IO){
            val content: String = AppConfig.parseFile("article_regex.json")
           val regex:RegexBean=  JSON.parseObject(content, RegexBean::class.java)
            urlRegexList=regex.data
        }
    }

    override fun intercept(uri: Uri,
                           userAgent: String?,
                           reqHeaders: Map<String, String>?,
                           reqMethod: String?): WebResourceResponse? {
        val resp = super.intercept(uri, userAgent, reqHeaders, reqMethod)
        if (resp != null) return resp
        if (reqMethod != "GET") return null
        if (urlRegexList.isEmpty()) return null
        val host = uri.host ?: return null
        val urlRegexBean = urlRegexList
                .firstOrNull { host.contains(it.host) }
                ?: return null
        val url = uri.toString()
        val pattern = Pattern.compile(urlRegexBean.regex)
        val matcher = pattern.matcher(url)
        if (!matcher.find()) {
            return null
        }
        var html = HtmlCacheManager.get(url)
        if (html == null) {
            html = WebHttpClient.request(url, userAgent, reqHeaders, reqMethod).resp()?.also {
                HtmlCacheManager.save(url, it)
            }
        }
        html ?: return null
        if (html.isNotEmpty()) {
            html = CssStyleManager.appendCssOnFirstStyle(html, urlRegexBean.name)
        }
        return WebResourceResponse("text/html", "utf-8", ByteArrayInputStream(html.toByteArray()))
    }

}