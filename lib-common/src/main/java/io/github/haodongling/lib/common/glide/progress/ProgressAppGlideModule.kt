package io.github.haodongling.lib.common.glide.progress

import android.content.Context
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import okhttp3.OkHttpClient
import java.io.InputStream
import java.util.concurrent.TimeUnit

/**
 * 描述：
 *
 * @author Cuizhen
 * @date 2018/9/17
 */
@GlideModule
class ProgressAppGlideModule : AppGlideModule() {
    var diskSize = 1024 * 1024 * 500
    var memorySize = Runtime.getRuntime().maxMemory().toInt() / 8
    override fun isManifestParsingEnabled(): Boolean {
        return false
    }

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        super.applyOptions(context, builder)


        // 定义缓存大小和位置
        builder.setDiskCache(InternalCacheDiskCacheFactory(context, diskSize.toLong())) // 内部存储中
        // builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, "cache", diskSize)); // sd卡中

        // 默认内存和图片池大小
        val calculator = MemorySizeCalculator.Builder(context).setMemoryCacheScreens(2f).build()
        val defaultMemoryCacheSize = calculator.memoryCacheSize // 默认内存大小
        val defaultBitmapPoolSize = calculator.bitmapPoolSize // 默认图片池大小
        builder.setMemoryCache(LruResourceCache(defaultMemoryCacheSize.toLong()))
        builder.setBitmapPool(LruBitmapPool(defaultBitmapPoolSize.toLong()))

        // 自定义内存和图片池大小
        builder.setMemoryCache(LruResourceCache(memorySize.toLong())) // 自定义内存大小
        builder.setBitmapPool(LruBitmapPool(memorySize.toLong())) // 自定义图片池大小
        builder.setDefaultRequestOptions(RequestOptions().format(DecodeFormat.PREFER_RGB_565))
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        super.registerComponents(context, glide, registry)
        val client: OkHttpClient = OkHttpClient.Builder().connectTimeout(10, TimeUnit.MINUTES).readTimeout(10, TimeUnit.MINUTES)
            .retryOnConnectionFailure(true).build()
        registry.replace(
            GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory(client)
        )
    }
}