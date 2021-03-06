package io.github.haodongling.kotlinmvvmdemo.web.cache

import android.util.LruCache
import com.jakewharton.disklrucache.DiskLruCache
import io.github.haodongling.lib.utils.coder.MD5Coder
import io.github.haodongling.lib.utils.file.CacheUtils
import java.io.File
import java.io.IOException

/**
 * Author: tangyuan
 * Time : 2021/9/28
 * Description:
 */
object ResCacheManager {
    private const val memoryMaxSize = 100
    private const val diskMaxSize = 10 * 1024 * 1024L

    private val memoryLruCache: LruCache<String, String> by lazy {
        LruCache(memoryMaxSize)
    }
    private var diskLruCache: DiskLruCache? = null
    private fun openDiskLruCache() {
        diskLruCache?.let {
            if (it.isClosed) {
                diskLruCache = null
            }
        }
        if (diskLruCache == null) {
            val file = File(CacheUtils.filesDir, "web/res")
            if (!file.exists()) {
                file.mkdirs()
            }
            try {
                diskLruCache = DiskLruCache.open(file, 1, 1, diskMaxSize)
            } catch (e: IOException) {
            }
        }
    }

    fun get(url: String): String? {
        memoryLruCache.get(url)?.let {
            if (it.isNotEmpty()) {
                return it
            } else {
                memoryLruCache.remove(url)
            }
        }
        synchronized(this) {
            openDiskLruCache()
            val diskLruCache = diskLruCache ?: return@synchronized
            val key = MD5Coder.encode(url)
            try {
                diskLruCache.get(key)?.let {
                    val res = it.getString(0)
                    if (res.isNotEmpty()) {
                        memoryLruCache.put(url, res)
                        return res
                    }
                }
            } catch (e: IOException) {
            }
        }
        return null
    }

    fun save(url: String, res: String) {
        memoryLruCache.put(url, res)
        synchronized(this) {
            openDiskLruCache()
            val diskLruCache = diskLruCache ?: return@synchronized
            val key = MD5Coder.encode(url)
            try {
                val editor: DiskLruCache.Editor = diskLruCache.edit(key)
                editor[0] = res
                editor.commit()
                diskLruCache.flush()
            } catch (e: IOException) {
            }
        }
    }
}