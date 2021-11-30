package io.github.haodongling.lib.utils.file

import io.github.haodongling.lib.utils.global.AppGlobals
import java.io.File

object CacheUtils {
    /**
     * 获取系统默认缓存文件夹
     * 优先返回SD卡中的缓存文件夹
     */
    val cacheDir: String
        get() {
            var cacheFile: File? = null
            if (FileUtils.isSDCardAlive) {
                cacheFile = AppGlobals.getApplication().applicationContext.externalCacheDir
            }
            if (cacheFile == null) {
                cacheFile = AppGlobals.getApplication().applicationContext.cacheDir
            }
            return cacheFile!!.absolutePath
        }
    val filesDir: String
        get() {
            val cacheFile = AppGlobals.getApplication().applicationContext.filesDir
            return cacheFile.absolutePath
        }

    /**
     * 获取系统默认缓存文件夹内的缓存大小
     */
    val totalCacheSize: String
        get() {
            var cacheSize = FileUtils.getSize(AppGlobals.getApplication().applicationContext.cacheDir)
            if (FileUtils.isSDCardAlive) {
                cacheSize += FileUtils.getSize(AppGlobals.getApplication().externalCacheDir!!)
            }
            return FileUtils.formatSize(cacheSize.toDouble())
        }

    /**
     * 清除系统默认缓存文件夹内的缓存
     */
    fun clearAllCache() {
        FileUtils.delete(AppGlobals.getApplication().applicationContext.cacheDir)
        if (FileUtils.isSDCardAlive) {
            FileUtils.delete(AppGlobals.getApplication().applicationContext.externalCacheDir)
        }
    }
}