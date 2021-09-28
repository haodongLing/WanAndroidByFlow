package io.github.haodongling.kotlinmvvmdemo.web.cache

import com.jakewharton.disklrucache.DiskLruCache
import io.github.haodongling.kotlinmvvmdemo.web.interceptor.WebHttpClient
import io.github.haodongling.kotlinmvvmdemo.web.interceptor.WebHttpClient.stringRespBody
import io.github.haodongling.lib.utils.coder.MD5Coder
import io.github.haodongling.lib.utils.file.CacheUtils
import kotlinx.coroutines.*
import java.io.File
import java.io.IOException

/**
 * Author: tangyuan
 * Time : 2021/9/28
 * Description:
 */
object HtmlCacheManager :CoroutineScope by GlobalScope{
    private const val diskMaxSize = 10 * 1024 * 1024L
    private var diskLruCache: DiskLruCache? = null
    private val submitJobs = hashMapOf<String, Job>()
    private val mObject=Any()
    private fun openDiskLruCache() {
        diskLruCache?.let {
            if (it.isClosed) {
                diskLruCache = null
            }
        }
        if (diskLruCache == null) {
            val file = File(CacheUtils.getFilesDir(), "web/html")
            if (!file.exists()) {
                file.mkdirs()
            }
            try {
                diskLruCache = DiskLruCache.open(file, 1, 1, diskMaxSize)
            } catch (e: IOException) {
            }
        }
    }
    @Suppress("UNREACHABLE_CODE")
    fun submit(url: String) {
        // 暂时屏蔽自动缓存列表文章html
        return
        synchronized(mObject) {
            launch(Dispatchers.IO) {
                if (!hasSaved(url)) {
                    val html = WebHttpClient.request(url, "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Mobile Safari/537.36", null, "GET").stringRespBody()
                    if (html != null) {
                        save(url, html)
                    }
                }
            }.apply {
                submitJobs[url] = this
                this.invokeOnCompletion {
                    submitJobs.remove(url)
                }
            }
        }
    }

    fun get(url: String): String? {
        synchronized(mObject) {
            submitJobs[url]?.cancel()
            openDiskLruCache()
            val diskLruCache = diskLruCache ?: return@synchronized
            val key = MD5Coder.encode(url)
            try {
                diskLruCache.get(key)?.let {
                    val html = it.getString(0)
                    if (html.isNotEmpty()) {
                        return html
                    }
                }
            } catch (e: IOException) {
            }
        }
        return null
    }
    fun save(url: String, html: String) {
        synchronized(mObject) {
            openDiskLruCache()
            val diskLruCache = diskLruCache ?: return@synchronized
            val key = MD5Coder.encode(url)
            try {
                val editor: DiskLruCache.Editor = diskLruCache.edit(key)
                editor[0] = html
                editor.commit()
                diskLruCache.flush()
            } catch (e: IOException) {
            }
        }
    }
    private fun hasSaved(url: String): Boolean {
        openDiskLruCache()
        val key = MD5Coder.encode(url)
        try {
            diskLruCache?.get(key)?.let {
                return true
            }
        } catch (e: IOException) {
        }
        return false
    }


}