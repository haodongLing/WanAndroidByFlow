package io.github.haodongling.lib.common.glide.progress

import android.content.Context
import android.graphics.Color
import com.bumptech.glide.annotation.GlideExtension
import com.bumptech.glide.annotation.GlideOption
import com.bumptech.glide.request.BaseRequestOptions

/**
 * Author: tangyuan
 * Time : 2021/11/30
 * Description:
 */
@GlideExtension
object ProgressExtension {

    @GlideOption
    @JvmStatic
    fun progress(options: BaseRequestOptions<*>, context: Context): BaseRequestOptions<*> {
        val progressPlaceholderDrawable =
            ProgressPlaceholderDrawable(
                context,
                options.placeholderDrawable,
                options.placeholderId
            )
        progressPlaceholderDrawable.setTint(Color.GRAY)
        return options.placeholder(progressPlaceholderDrawable)
    }
}