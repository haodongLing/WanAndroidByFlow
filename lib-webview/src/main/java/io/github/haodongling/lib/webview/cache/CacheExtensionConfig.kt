package io.github.haodongling.lib.webview.cache

import android.text.TextUtils
import java.util.*
import kotlin.collections.HashSet

/**
 * Author: tangyuan
 * Time : 2021/9/14
 * Description:
 */
class CacheExtensionConfig {
    private val statics:HashSet<String> = STATIC
    private val noCache:HashSet<String> = NO_CACHE
    companion object {
        val STATIC: HashSet<String> = hashSetOf(
            "html", "htm", "js", "ico", "css", "png", "jpg", "jpeg", "gif", "bmp",
            "ttf", "woff", "woff2", "otf", "eot", "svg", "xml", "swf", "txt", "text", "conf", "webp",
        )
        val NO_CACHE = hashSetOf(
            "mp4", "mp3", "ogg", "avi", "wmv", "flv", "rmvb", "3gp"
        )

        private fun add(set: HashSet<String>, extension: String?) {
            if (!extension.isNullOrEmpty()) {
                set.add(extension.replace(".", "").toLowerCase().trim())
            }
        }
        private fun remove(set: HashSet<String>, extension: String?) {
            if (!extension.isNullOrEmpty()) {
                set.remove(extension.replace(".", "").toLowerCase().trim { it <= ' ' })
            }
        }
        @JvmStatic
        fun addGlobalExtension(extension: String?) {
            add(STATIC, extension)
        }
        @JvmStatic
        fun removeGlobalExtension(extension: String?) {
            CacheExtensionConfig.remove(STATIC, extension)
        }


    }

    /**
     * 是否是音视频内容，需要过滤
     * @param extension                             extension
     * @return
     */
    fun isMedia(extension: String): Boolean {
        if (TextUtils.isEmpty(extension)) {
            return false
        }
        return if (NO_CACHE.contains(extension)) {
            true
        } else noCache.contains(extension.toLowerCase().trim { it <= ' ' })
    }

    /**
     * 是否可以缓存
     * @param extension                             extension
     * @return
     */
    fun canCache(extension: String): Boolean {
        var extension = extension
        if (TextUtils.isEmpty(extension)) {
            return false
        }
        extension = extension.toLowerCase().trim { it <= ' ' }
        return if (STATIC.contains(extension)) {
            true
        } else statics.contains(extension)
    }


    fun addExtension(extension: String?): CacheExtensionConfig? {
        add(statics, extension)
        return this
    }

    fun removeExtension(extension: String?): CacheExtensionConfig? {
        remove(statics, extension)
        return this
    }


    fun isHtml(extension: String): Boolean {
        if (TextUtils.isEmpty(extension)) {
            return false
        }
        return extension.toLowerCase(Locale.ROOT).contains("html") || extension.toLowerCase().contains("htm")
    }

    fun clearAll() {
        clearDiskExtension()
    }

    fun clearDiskExtension() {
        statics.clear()
    }
}