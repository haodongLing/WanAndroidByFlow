package io.github.haodongling.lib.common.glide.progress

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.request.target.ImageViewTarget
import com.bumptech.glide.request.transition.Transition

/**
 * Author: tangyuan
 * Time : 2021/11/30
 * Description:
 */
class ProgressImageViewTarget<T>(
    private val url: String, imageView: ImageView
) : ImageViewTarget<T>(imageView) {

    override fun onLoadStarted(placeholder: Drawable?) {
        super.onLoadStarted(placeholder)
        if (placeholder is ProgressPlaceholderDrawable) {
            ProgressInterceptor.addProgressListener(url
            ) { progress -> placeholder.setProgress(progress.toInt()) }
        }
    }

    override fun onLoadFailed(errorDrawable: Drawable?) {
        super.onLoadFailed(errorDrawable)
        ProgressInterceptor.removeProgressListener(url)
    }

    override fun onResourceReady(resource: T, transition: Transition<in T>?) {
        super.onResourceReady(resource, transition)
        ProgressInterceptor.removeProgressListener(url)
    }

    override fun onLoadCleared(placeholder: Drawable?) {
        super.onLoadCleared(placeholder)
        ProgressInterceptor.removeProgressListener(url)
    }

    override fun setResource(resource: T?) {
        if (resource is Bitmap) {
            view.setImageBitmap(resource)
        } else if (resource is Drawable) {
            view.setImageDrawable(resource)
        }
    }
}